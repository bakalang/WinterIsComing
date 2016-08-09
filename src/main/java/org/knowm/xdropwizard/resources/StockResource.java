package org.knowm.xdropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.jersey.caching.CacheControl;
import org.knowm.xdropwizard.api.RandomNumber;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Sky on 2016/8/9.
 */

@Path("st")
@Produces(MediaType.APPLICATION_JSON)
public class StockResource {


    @GET
    @Timed
    @CacheControl(noCache = true)
    @Produces(MediaType.APPLICATION_JSON)
    public ClassifierPerformance getGroupPerformance() {

        return new ClassifierPerformance();
    }

    public class ClassifierPerformance {

    }
}
