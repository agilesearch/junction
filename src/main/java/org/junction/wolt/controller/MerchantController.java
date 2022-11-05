package org.junction.wolt.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junction.wolt.client.MerchantClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/merchant")
public class MerchantController {


    @RestClient
    MerchantClient client;

    final String EXAMPLE = """
            {
              "pickup": {
                "location": {
                    "formatted_address": "Arkadiankatu 3-6"
                },
                "comment": "The box is in front of the door",
                "contact_details": {
                  "name": "John Doe",
                  "phone_number": "+358123456789",
                  "send_tracking_link_sms": false
                }
              },
              "dropoff": {
                "location": {
                  "formatted_address": "Otakaari 24, 02150 Espoo"
                },
                "contact_details": {
                  "name": "John Doe's wife",
                  "phone_number": "+358123456789",
                  "send_tracking_link_sms": false
                },
                "comment": "Leave at the door, please"
              },
              "customer_support": {
                "email": "string",
                "phone_number": "string",
                "url": "string"
              },
              "merchant_order_reference_id": null,
              "is_no_contact": true,
              "contents": [
                {
                  "count": 1,
                  "description": "plastic bag",
                  "identifier": "12345",
                  "tags": []
                }
              ],
              "tips": [],
              "min_preparation_time_minutes": 10,
              "scheduled_dropoff_time": null
            }
                        
            """;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<JsonNode> hello() {
        return client.order("6364e0088018ce361efafca3", EXAMPLE);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{merchantId}/delivery-order")
    public Uni<JsonNode> order(@PathParam("merchantId") String merchantId, String body) {
        return client.order(merchantId, body);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{merchantId}/delivery-fee")
    public Uni<JsonNode> fee(@PathParam("merchantId") String merchantId, String body) {
        return client.fee(merchantId, body);
    }
}
