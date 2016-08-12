package org.knowm.xdropwizard.utils;

import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Sky on 2016/7/19.
 */
public class ResponseFactory {

    public static Response ok(String entity) {
        return Response
                .ok()
                .entity(entity)
                .type(MediaType.TEXT_PLAIN)
                .encoding("UTF-8")
                .build();
    }

    public static Response jsonOK(JSONObject entity) {
        return Response
                .ok()
                .entity(entity)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .encoding("UTF-8")
                .build();
    }
}
