package org.mi.dao;

import org.mi.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionCardDao {
    public String getPositionCardNameByGroupId(String groupId) {
        String name = "NULL";
        String sqlString = """
                WITH RECURSIVE r AS (
                    SELECT g."groupId", g."parentId", pc.name, 1 AS level
                    FROM "group" g
                             LEFT JOIN "positionCard" pc USING ("groupId")
                    WHERE g."groupId" = ?
                                
                    UNION
                                
                    SELECT g."groupId", g."parentId", pc.name, level + 1 AS level
                    FROM "group" g
                             LEFT JOIN "positionCard" pc USING ("groupId")
                             JOIN r ON g."groupId" = r."parentId"
                )
                                
                SELECT name
                    FROM r
                    WHERE name IS NOT NULL
                    ORDER BY level
                    LIMIT 1;
                """;
        try (Connection con = DataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlString)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
}
