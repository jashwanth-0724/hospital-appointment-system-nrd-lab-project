package com.hospital.model;
import java.sql.Date;
public class Patient extends User {
    private int patientId; private String firstName; private String lastName; private String phone; private Date dateOfBirth; private String gender; private String address;
    public Patient() {}
    public int getPatientId() { return patientId; } public void setPatientId(int patientId) { this.patientId = patientId; }
    public String getFirstName() { return firstName; } public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; } public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; } public void setPhone(String phone) { this.phone = phone; }
    public Date getDateOfBirth() { return dateOfBirth; } public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; } public void setGender(String gender) { this.gender = gender; }
    public String getAddress() { return address; } public void setAddress(String address) { this.address = address; }
}
