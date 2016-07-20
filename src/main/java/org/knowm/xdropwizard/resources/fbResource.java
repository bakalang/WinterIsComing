package org.knowm.xdropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.knowm.xdropwizard.business.GraphData;
import org.knowm.xdropwizard.business.GraphDataDAO;
import org.knowm.xdropwizard.constance.commonConstance;
import org.knowm.xdropwizard.utils.ResponseFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

/**
 * Created by Sky on 2016/7/19.
 */
@Path("graphapi")
@Produces(MediaType.APPLICATION_JSON)
public class FBResource implements commonConstance {

    private final String accessToken = "EAACEdEose0cBABH6FdCyw789dZAnemLloBHnwnnXVspDfypHo7D2kASnKy8ZCneDbje4nYnIYmFVBrfRpT5TqRnZCm8E8pMzWEk8K5jHi4LuZC6sTq9N7jkFElZCIl0wgZAZAZAqQzcS6EKmHBdqHOP6bTNXVWEmSUeNCIuNcK7MPwZDZD";
    private Random RANDOM = new Random();

    @GET
    @Timed
    public Response sayHello(@QueryParam("fbid") Optional<String> fbid, @QueryParam("fields") Optional<String> fields, @QueryParam("limit") Optional<String> limit, @QueryParam("token") Optional<String> token) {

        DateTime now = new org.joda.time.DateTime();
        String reqFbid = fbid.orNull();
        String reqFields = fields.orNull();
        String reqLimit = limit.orNull();
        String reqToken = token.or(accessToken);

        if(reqFbid == null ){
            return ResponseFactory.ok("param error");
        }
        if(reqLimit != null && reqFields == null){
            return ResponseFactory.ok("param error");
        }

        StringBuilder url = new StringBuilder();
        url.append("https://graph.facebook.com/v2.7/").append(reqFbid);

        if(reqFields != null){
            url.append("?fields=").append(reqFields);
        }
        if(reqLimit != null){
            url.append(".limit("+reqLimit+")");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("URL => "+url+"\n");
        JSONObject jo = sendRequest(url+"&access_token="+reqToken);

        JSONArray dataArray =  jo.getJSONObject(reqFields).getJSONArray("data");
        sb.append("RESPONSE BODY => "+dataArray.toString()+"\n");
        GraphData gd = new GraphData();
        gd.setqDate(YYYYMMDD.print(now));
        gd.setData(dataArray.toString());
        gd.setParam(reqFields);
        GraphDataDAO.insertGraphData(gd);

        return ResponseFactory.ok(sb.toString());
    }


    @GET
    @Path("lottery")
    public Response Lottery(@QueryParam("id") Optional<String> id, @QueryParam("token") Optional<String> token) {
        String reqId = id.orNull();
        String reqToken = token.or(accessToken);
        if(reqId == null ){
            return ResponseFactory.ok("param error");
        }

        StringBuilder url = new StringBuilder();
        url.append("https://graph.facebook.com/v2.7/").append(reqId).append("/?fields=likes");
        JSONObject jo = sendRequest(url+"&access_token="+reqToken);

        JSONArray dataArray =  jo.getJSONObject("likes").getJSONArray("data");

        int likeSize = dataArray.length();
        int randomCardPlace = RANDOM.nextInt(likeSize);
        return ResponseFactory.ok("winner "+dataArray.get(randomCardPlace).toString());
    }

    @GET
    @Path("all")
    public List<GraphData> getAll() {
        return GraphDataDAO.selectAll();
    }

    private JSONObject sendRequest(String url) {
        GetMethod fileGet = new GetMethod(url);
        HttpClient client = new HttpClient();
        BufferedReader streamReader = null;
        JSONObject returnObject = null;
        try
        {
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            int status = client.executeMethod(fileGet);
            if (status == 200){
                streamReader = new BufferedReader(new InputStreamReader(fileGet.getResponseBodyAsStream()));

                StringBuilder responseStrBuilder = new StringBuilder();
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
                returnObject = new JSONObject(responseStrBuilder.toString());
            }

        }catch ( Throwable e ){
            e.printStackTrace();
        }
        return returnObject;
    }
}
