package de.iws.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.iws.jdbc.ResourceManager;
import de.iws.utils.db.OrderUtil;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.codegen.maven.example.Tables;
import org.jooq.codegen.maven.example.tables.pojos.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class OrderHandler {

    public static int createNewOrder(Order order) throws SQLException {
        int id = 0;
        try (Connection connection = ResourceManager.getConnection()) {
            DSLContext dslContext = ResourceManager.getDSLContext(connection);
            Record record = dslContext
                    .insertInto(Tables.ORDER,
                            Tables.ORDER.CUSTOMER_ID,
                            Tables.ORDER.ITEMS,
                            Tables.ORDER.PRICE,
                            Tables.ORDER.BILL
                           )
                    .values(order.getCustomerId(),
                            order.getItems(),
                            order.getPrice(),
                            UUID.randomUUID().toString().replace("-", ""))
                    .returning(Tables.ORDER.ORDER_ID).fetchOne();
            id = record.getValue(Tables.ORDER.ORDER_ID);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return id;
    }

    public static Order getOrder(int id) throws JsonProcessingException, SQLException {
       return OrderUtil.fetchOrderForOrderId(id);
    }
}