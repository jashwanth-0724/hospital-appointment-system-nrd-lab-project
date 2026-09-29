package com.hospital.model;
public class Doctor extends User {
    private int doctorId; private int departmentId; private String departmentName; private String firstName; private String lastName; private String phone; private String specialization; private int experienceYears;
    public Doctor() {}
    public int getDoctorId() { return doctorId; } public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public int getDepartmentId() { return departmentId; } public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
    public String getDepartmentName() { return departmentName; } public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public String getFirstName() { return firstName; } public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; } public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; } public void setPhone(String phone) { this.phone = phone; }
    public String getSpecialization() { return specialization; } public void setSpecialization(String specialization) { this.specialization = specialization; }
    public int getExperienceYears() { return experienceYears; } public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
}
