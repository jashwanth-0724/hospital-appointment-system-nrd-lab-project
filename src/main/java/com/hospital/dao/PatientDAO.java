package com.hospital.dao;
import com.hospital.model.Patient; import com.hospital.util.DBConnection; import java.sql.*;
public class PatientDAO {
    public Patient getPatientByUserId(int userId) {
        String query = "SELECT p.*, u.email, u.role FROM patients p JOIN users u ON p.user_id = u.id WHERE u.id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Patient p = new Patient();
                p.setId(rs.getInt("user_id")); p.setEmail(rs.getString("email")); p.setRole(rs.getString("role"));
                p.setPatientId(rs.getInt("id")); p.setFirstName(rs.getString("first_name")); p.setLastName(rs.getString("last_name"));
                p.setPhone(rs.getString("phone")); p.setDateOfBirth(rs.getDate("date_of_birth")); p.setGender(rs.getString("gender")); p.setAddress(rs.getString("address"));
                return p;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    public boolean registerPatient(Patient patient) {
        String userQuery = "INSERT INTO users (email, password_hash, role) VALUES (?, ?, 'PATIENT')";
        String patientQuery = "INSERT INTO patients (user_id, first_name, last_name, phone, date_of_birth, gender, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement userStmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, patient.getEmail()); userStmt.setString(2, patient.getPasswordHash());
                userStmt.executeUpdate();
                ResultSet rs = userStmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    try (PreparedStatement patStmt = conn.prepareStatement(patientQuery)) {
                        patStmt.setInt(1, userId); patStmt.setString(2, patient.getFirstName()); patStmt.setString(3, patient.getLastName());
                        patStmt.setString(4, patient.getPhone()); patStmt.setDate(5, patient.getDateOfBirth()); patStmt.setString(6, patient.getGender()); patStmt.setString(7, patient.getAddress());
                        patStmt.executeUpdate();
                    }
                }
                conn.commit();
                return true;
            } catch (SQLException e) { conn.rollback(); e.printStackTrace(); }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
