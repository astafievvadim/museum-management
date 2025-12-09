package com.astafievvadim.mm_backend.payload;

import com.astafievvadim.mm_backend.model.RoleEnum;
import java.util.Date;

public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String email;
    private RoleEnum role;

    public EmployeeDTO() {}

    public EmployeeDTO(Long id, String firstName, String lastName, Date birthdate, String email, RoleEnum role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.role = role;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Date getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public RoleEnum getRole() {
        return role;
    }
    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
