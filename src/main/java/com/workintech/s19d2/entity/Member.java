package com.workintech.s19d2.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member", schema = "bank")
public class Member implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_role", schema = "bank",
                joinColumns = @JoinColumn(name = "member_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();



/*
    UserDetails Interface'indeki getPassword @Data daki getter ile zaten getleniyor
    password isimli fieldımız olduğu için(UserDetails içindeki getPassword() ile denkleşiyor,
    şuan gerek yok ama istersem @getPassword() methodunu override edebilirim.
    ( Role'deki GrantedAuthority ile aynı mantık !!
    *
    @Override
    public String getPassword() {
        return password;
    }
*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    // Userı hangi field ile ile login etmek istiyorsak o değer dönmeli.
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
