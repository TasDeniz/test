package de.iws.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.iws.handler.OrderHandler;
import org.jooq.codegen.maven.example.tables.pojos.Order;
import org.jooq.tools.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderController {

    public static JSONObject createOrder(String jsonOrder) throws IOException, SQLException {
        ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        Order order = mapper.readValue(jsonOrder, Order.class);
        int orderId = OrderHandler.createNewOrder(order);
        return createBill(orderId);
    }

    public static JSONObject createBill(int id) throws JsonProcessingException, SQLException {
        Order order = OrderHandler.getOrder(id);
        Date now = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy 'um' HH:mm:ss ");
        JsonElement tradeElement = JsonParser.parseString(order.getItems());
        JsonArray items = tradeElement.getAsJsonArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text","Vielen Dank Hans Meier für ihre Bestellung am "+dateFormatter.format(now)+" ! Sie haben "+ items.size() + " Artikel für "+ order.getPrice()+" gekauft:");
        jsonObject.put("billNumber", order.getBill());
        return jsonObject;
    }
}
