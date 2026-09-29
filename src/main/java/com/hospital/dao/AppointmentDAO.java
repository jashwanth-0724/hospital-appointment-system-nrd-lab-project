package com.hospital.dao;
import com.hospital.model.Appointment; import com.hospital.util.DBConnection; import java.sql.*; import java.util.ArrayList; import java.util.List;
public class AppointmentDAO {
    public boolean bookAppointment(Appointment appt) {
        String checkQuery = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ? AND appointment_time = ? AND status != 'CANCELLED' AND status != 'REJECTED'";
        String query = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, reason) VALUES (?, ?, ?, ?, 'CONFIRMED', ?)";
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, appt.getDoctorId()); checkStmt.setDate(2, appt.getAppointmentDate()); checkStmt.setTime(3, appt.getAppointmentTime());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) return false; // double booking prevented
            }
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, appt.getPatientId()); stmt.setInt(2, appt.getDoctorId());
                stmt.setDate(3, appt.getAppointmentDate()); stmt.setTime(4, appt.getAppointmentTime()); stmt.setString(5, appt.getReason());
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        List<Appointment> list = new ArrayList<>();
        String query = "SELECT a.*, d.first_name as d_fn, d.last_name as d_ln, dep.name as dep_name FROM appointments a JOIN doctors d ON a.doctor_id = d.id JOIN departments dep ON d.department_id = dep.id WHERE a.patient_id = ? ORDER BY a.appointment_date DESC, a.appointment_time DESC";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment();
                a.setId(rs.getInt("id")); a.setPatientId(rs.getInt("patient_id")); a.setDoctorId(rs.getInt("doctor_id"));
                a.setAppointmentDate(rs.getDate("appointment_date")); a.setAppointmentTime(rs.getTime("appointment_time"));
                a.setStatus(rs.getString("status")); a.setReason(rs.getString("reason"));
                a.setDoctorName("Dr. " + rs.getString("d_fn") + " " + rs.getString("d_ln")); a.setDepartmentName(rs.getString("dep_name"));
                list.add(a);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        List<Appointment> list = new ArrayList<>();
        String query = "SELECT a.*, p.first_name as p_fn, p.last_name as p_ln FROM appointments a JOIN patients p ON a.patient_id = p.id WHERE a.doctor_id = ? ORDER BY a.appointment_date DESC, a.appointment_time DESC";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment();
                a.setId(rs.getInt("id")); a.setPatientId(rs.getInt("patient_id")); a.setDoctorId(rs.getInt("doctor_id"));
                a.setAppointmentDate(rs.getDate("appointment_date")); a.setAppointmentTime(rs.getTime("appointment_time"));
                a.setStatus(rs.getString("status")); a.setReason(rs.getString("reason"));
                a.setPatientName(rs.getString("p_fn") + " " + rs.getString("p_ln"));
                list.add(a);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    public boolean updateStatus(int appointmentId, String status) {
        String query = "UPDATE appointments SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status); stmt.setInt(2, appointmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String query = "SELECT a.*, p.first_name as p_fn, p.last_name as p_ln, d.first_name as d_fn, d.last_name as d_ln, dep.name as dep_name FROM appointments a JOIN patients p ON a.patient_id = p.id JOIN doctors d ON a.doctor_id = d.id JOIN departments dep ON d.department_id = dep.id ORDER BY a.appointment_date DESC";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Appointment a = new Appointment();
                a.setId(rs.getInt("id")); a.setPatientId(rs.getInt("patient_id")); a.setDoctorId(rs.getInt("doctor_id"));
                a.setAppointmentDate(rs.getDate("appointment_date")); a.setAppointmentTime(rs.getTime("appointment_time"));
                a.setStatus(rs.getString("status")); a.setReason(rs.getString("reason"));
                a.setPatientName(rs.getString("p_fn") + " " + rs.getString("p_ln"));
                a.setDoctorName("Dr. " + rs.getString("d_fn") + " " + rs.getString("d_ln")); a.setDepartmentName(rs.getString("dep_name"));
                list.add(a);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    public int getTotalAppointments() {
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM appointments")) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}
