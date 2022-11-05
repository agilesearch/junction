package org.junction.wolt.client;

import com.fasterxml.jackson.databind.JsonNode;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@RegisterRestClient(configKey = "wolt")
@ApplicationScoped
@ClientHeaderParam(name = "Authorization", value = "{lookupAuth}")
public interface MerchantClient {

    @POST
    @Path("merchants/{merchantId}/delivery-order")
    Uni<JsonNode> order(@PathParam("merchantId") String merchantId, String body);

    @POST
    @Path("merchants/{merchantId}/delivery-fee")
    Uni<JsonNode> fee(@PathParam("merchantId") String merchantId, String body);


    default String lookupAuth() {
        final var token = ConfigProvider.getConfig().getValue("wolt.token", String.class);
        return "Bearer " + token;
    }
}
