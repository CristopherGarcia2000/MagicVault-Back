package com.magicvault.documents;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class Users implements UserDetails {

    @Id
    private ObjectId id;
    private String type_rol;
    private String username;
    private String pass;
    private String email;

    // Implementing UserDetails interface methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Returns the authorities granted to the user. Currently not implemented.
    }

    @Override
    public String getPassword() {
        return pass; // Returns the password of the user.
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Returns whether the user's account is not expired.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Returns whether the user is not locked.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Returns whether the user's credentials (password) are not expired.
    }

    @Override
    public boolean isEnabled() {
        return true; // Returns whether the user is enabled.
    }

}