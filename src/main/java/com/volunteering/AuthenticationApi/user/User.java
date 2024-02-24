package com.volunteering.AuthenticationApi.user;

import com.volunteering.AuthenticationApi.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data //geter and setters for all field
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
     @Id
     @GeneratedValue //The default value is(strategy = GenerationType.AUTO)
     private Integer id;
     private String firstname;
     private String lastname;
     @Column(unique = true)
     private String email;
     private String password;
     private boolean emailVerified;
     private String verificationToken;

     @Enumerated(EnumType.STRING)
     private Role role;

     @OneToMany(mappedBy = "user")
     private transient List<Token> tokens;

     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
          return List.of(new SimpleGrantedAuthority(role.name())); //Because we just want a user to have one role
     }

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
          return false;
     }

     @Override
     public boolean isCredentialsNonExpired() {
          return true;
     }

     @Override
     public boolean isEnabled() {
          return false;
     }
}
