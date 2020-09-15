package de.iws.rest;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.iws.controller.OrderController;
import de.iws.utils.db.OrderUtil;
import org.jooq.tools.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

@Path("/")
public class Rest {
    private static ObjectWriter ow = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).writer().withDefaultPrettyPrinter();


    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response getTestService(String jsonOrder) {
        JSONObject jsonObject ;
        try {
            jsonObject = OrderController.createOrder(jsonOrder);
        } catch (IOException e) {
            return Response.status(400, e.getMessage()).build();
        } catch (SQLException e) {
           return Response.status(500, e.getSQLState()).build();
        }
        return Response.ok(jsonObject).build();
    }

    @Path("/bill/{billNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getOrderByBillNumber(@PathParam("billNumber") String billnumber) {
        String json = null;
        try {
            json = ow.writeValueAsString(OrderUtil.fetchOrderForBillNumber(billnumber));
        } catch (JsonProcessingException e) {
            Response.status(400, e.getMessage()).build();
        } catch (SQLException e) {
            Response.status(500, e.getMessage()).build();
        }
        return Response.ok(json).build();
    }

    @Path("/customer/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getOrdersByCustomer(@PathParam("customerId") int customerId) {
        String json = null;
        try {
            json = ow.writeValueAsString(OrderUtil.fetchOrderForCustomerId(customerId));
        } catch (JsonProcessingException e) {
            Response.status(400, e.getMessage()).build();
        } catch (SQLException e) {
            Response.status(500, e.getMessage()).build();
        }
        return Response.ok(json).build();
    }

}