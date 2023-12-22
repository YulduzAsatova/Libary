package com.example.new_online_libary_project.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user`")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private UUID id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false,unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    @Enumerated(EnumType.STRING)
    private Status status = Status.IN_ACTIVE;
    @CreationTimestamp
    @Column(columnDefinition = "timeStamp default now()",updatable = false)
    private LocalDateTime createAd;
    private LocalDateTime updateAd;


   /* @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      *//* Set<Permission> permissionSet = roles.stream()
                .flatMap(role -> role
                        .getPermissions()
                        .stream())
                .collect(Collectors.toSet());
        permissionSet.addAll(permissions);
        return permissionSet
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission
                        .getName()))
                .collect(Collectors.toSet());*//*
        *//*Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
            for (Permission permission:role.getPermissions()){
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }*//*
        return Collections.emptyList();
    }*/
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+ role.name()));
    }

    @Override
    public String getUsername() {
        return phoneNumber;
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
