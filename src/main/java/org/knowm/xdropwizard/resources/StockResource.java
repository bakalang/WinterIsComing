package org.knowm.xdropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.jersey.caching.CacheControl;
import org.json.JSONObject;
import org.knowm.xdropwizard.business.SecurityTradeDAO;
import org.knowm.xdropwizard.utils.ResponseFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Sky on 2016/8/9.
 */

@Path("nvd3data")
public class StockResource {


    @Path("st")
    @GET
    @Timed
    @CacheControl(noCache = true)
    public Response getGroupPerformance() {

        SecurityTradeDAO.selectSecurityTradeByStockIdAndSecurityId("1605", "9800");

        JSONObject returnObject = new JSONObject();
        returnObject.append("key", "value");
        return ResponseFactory.jsonOK(returnObject);
    }

//    public class ClassifierPerformance {


//        SecurityTradeDAO.selectSecurityTradeByStockIdAndSecurityId("1605", "9800");

//        private final int dataLength = 100;
//        private final int[] xAxisData;
//        public ClassifierPerformance() {
//            xAxisData = new int[dataLength];
//            for (int i = 0; i < dataLength; i++) {
//                xAxisData[i] = i;
//            }
//        }
//        public int[] getxAxisData() {
//            return xAxisData;
//        }

//        public Map<String, Object> getQuantity() {
//            Map<String, Object> aa = new HashMap<String, Object>();
//            aa.put("key", "Quantity");
//            aa.put("bar", true);
//            aa.put("values", );
//            return aa;
//        }
//
//        public Map<String, Object> getPrice() {
//            Map<String, Object> aa = new HashMap<String, Object>();
//            aa.put("key", "Price");
//            aa.put("values", Arrays.asList(10,22,3,14,5,16,17,4,24));
//            return aa;
//        }
//    }
}



//"key" : "Quantity" ,
//        "bar": true,
//        "values" : [ [ 1136005200000 , 1271000.0] ,