package com.hospital.dao;
import com.hospital.model.Doctor; import com.hospital.util.DBConnection; import java.sql.*; import java.util.ArrayList; import java.util.List;
public class DoctorDAO {
    public Doctor getDoctorByUserId(int userId) {
        String query = "SELECT d.*, u.email, u.role, dep.name as department_name FROM doctors d JOIN users u ON d.user_id = u.id JOIN departments dep ON d.department_id = dep.id WHERE u.id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Doctor d = new Doctor();
                d.setId(rs.getInt("user_id")); d.setEmail(rs.getString("email")); d.setRole(rs.getString("role"));
                d.setDoctorId(rs.getInt("id")); d.setDepartmentId(rs.getInt("department_id")); d.setDepartmentName(rs.getString("department_name"));
                d.setFirstName(rs.getString("first_name")); d.setLastName(rs.getString("last_name")); d.setPhone(rs.getString("phone"));
                d.setSpecialization(rs.getString("specialization")); d.setExperienceYears(rs.getInt("experience_years"));
                return d;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT d.*, u.email, u.role, dep.name as department_name FROM doctors d JOIN users u ON d.user_id = u.id JOIN departments dep ON d.department_id = dep.id";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Doctor d = new Doctor();
                d.setId(rs.getInt("user_id")); d.setEmail(rs.getString("email")); d.setRole(rs.getString("role"));
                d.setDoctorId(rs.getInt("id")); d.setDepartmentId(rs.getInt("department_id")); d.setDepartmentName(rs.getString("department_name"));
                d.setFirstName(rs.getString("first_name")); d.setLastName(rs.getString("last_name")); d.setPhone(rs.getString("phone"));
                d.setSpecialization(rs.getString("specialization")); d.setExperienceYears(rs.getInt("experience_years"));
                doctors.add(d);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return doctors;
    }
}
