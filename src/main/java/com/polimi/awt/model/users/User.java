package com.polimi.awt.model.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.polimi.awt.model.Role;
import com.polimi.awt.model.RoleName;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = "id")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @JsonProperty(value = "email")
    private String emailAddress;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String emailAddress, Role role) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.roles.add(role);
    }

    public User(String username, String password, String emailAddress) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole (Role role) {
        roles.add(role);
    }

    public boolean rolesContainsRoleName (RoleName roleName) {
        for (Role currentRole : this.roles) {
            if (currentRole.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public void updateInformation(String username, String emailAddress, String password) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
