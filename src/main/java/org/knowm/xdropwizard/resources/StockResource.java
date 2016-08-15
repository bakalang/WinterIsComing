package org.knowm.xdropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import io.dropwizard.jersey.caching.CacheControl;
import org.json.JSONObject;
import org.knowm.xdropwizard.business.SecurityTrade;
import org.knowm.xdropwizard.business.SecurityTradeDAO;
import org.knowm.xdropwizard.utils.ResponseFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<SecurityTrade> getStock(@QueryParam("stockId") Optional<String> stockId) {

        String reqStockId = stockId.orNull();

        List<SecurityTrade> stList = SecurityTradeDAO.selectSecurityTradeByStockIdAndSecurityId(reqStockId, "9800");
        for(SecurityTrade st : stList){
            st.setTradeDateMinSec(st.getTradeDate().getTime());
        }

        return stList;
    }


    @Path("top5")
    @GET
    @Timed
    @CacheControl(noCache = true)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<SecurityTrade>> getTop5(@QueryParam("securityId") Optional<String> securityId) {

        Map<String, List<SecurityTrade>> rtnMap = new HashMap<String, List<SecurityTrade>>();
        String reqSecurityId = securityId.orNull();

        List<Object[]> aa = SecurityTradeDAO.selectTopSecurityTrade(reqSecurityId, "10");
        for(Object[] o : aa){
            String stockId = (String) o[2];
            List<SecurityTrade> stList = SecurityTradeDAO.selectSecurityTradeByStockIdAndSecurityId(stockId, reqSecurityId);
            for(SecurityTrade st : stList){
                st.setTradeDateMinSec(st.getTradeDate().getTime());
            }
            rtnMap.put(stockId, stList);
        }
        return rtnMap;
    }
}
