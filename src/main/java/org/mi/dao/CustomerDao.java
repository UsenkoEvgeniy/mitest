package org.mi.dao;

import org.mi.model.Customer;
import org.mi.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CustomerDao {

    public Collection<Customer> getCustomers(int offset) {
        String sqlString = """
                SELECT *
                    FROM customer
                    LEFT JOIN table_1 USING ("customer_id")
                    LEFT JOIN table_2 USING ("customer_id")
                    LEFT JOIN table_3 USING ("customer_id")
                    LEFT JOIN table_many USING ("customerId")
                    WHERE "customerId" IN (
                        SELECT "customerId" FROM customer
                        ORDER BY 1
                        OFFSET ? LIMIT 5000
                        );
                """;
        try (Connection con = DataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlString)) {
            ps.setInt(1, offset);
            try (ResultSet rs = ps.executeQuery()) {
                Map<String, Customer> customerMap = new LinkedHashMap<>();
                while (rs.next()) {
                    String customerId = rs.getString("customerId");
                    Customer customer = customerMap.get(customerId);
                    if (customer == null) {
                        String col_1 = rs.getString("col_1");
                        String col_2 = rs.getString("col_2");
                        String t1_col_1 = rs.getString("t1_col_1");
                        String t2_col_1 = rs.getString("t2_col_1");
                        String t3_col_1 = rs.getString("t3_col_1");
                        customer = new Customer(customerId, col_1, col_2, t1_col_1, t2_col_1, t3_col_1);
                    }
                    Set<Integer> groups = customer.getGroups();
                    Integer groupId = rs.getInt("groupId");
                    if (groupId != 0) {
                        groups.add(groupId);
                    }
                }
                return customerMap.values();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
