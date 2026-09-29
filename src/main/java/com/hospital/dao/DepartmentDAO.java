package com.hospital.dao;
import com.hospital.model.Department; import com.hospital.util.DBConnection; import java.sql.*; import java.util.ArrayList; import java.util.List;
public class DepartmentDAO {
    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String query = "SELECT * FROM departments";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) { list.add(new Department(rs.getInt("id"), rs.getString("name"), rs.getString("description"))); }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
