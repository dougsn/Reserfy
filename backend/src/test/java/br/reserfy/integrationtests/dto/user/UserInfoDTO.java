
package br.reserfy.integrationtests.dto.user;


import br.reserfy.domain.entity.Permission;

import java.io.Serializable;
import java.util.List;

public class UserInfoDTO implements Serializable {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private List<Permission> permissions;

    public UserInfoDTO() {
    }

    public UserInfoDTO(String id, String firstname, String lastname, String email, List<Permission> permissions) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
