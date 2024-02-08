package br.reserfy.integrationtests.dto.user;


import br.reserfy.domain.entity.Permission;

import java.io.Serializable;
import java.util.List;

public class UserUpdateDTO implements Serializable {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private List<Permission> permissions;

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(String id, String firstname, String lastname, String email, String password, List<Permission> permissions) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
