package org.knowm.xdropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.jersey.caching.CacheControl;
import org.json.JSONObject;
import org.knowm.xdropwizard.business.SecurityTrade;
import org.knowm.xdropwizard.business.SecurityTradeDAO;
import org.knowm.xdropwizard.utils.ResponseFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Sky on 2016/8/9.
 */

@Path("nvd3data")
public class StockResource {


    @Path("st")
    @GET
    @Timed
    @CacheControl(noCache = true)
    @Produces(MediaType.APPLICATION_JSON)
    public List<SecurityTrade> getGroupPerformance() {

        List<SecurityTrade> stList = SecurityTradeDAO.selectSecurityTradeByStockIdAndSecurityId("1605", "9800");
        for(SecurityTrade st : stList){
            st.setTradeDateMinSec(st.getTradeDate().getTime());
        }

        return stList;
    }

}
