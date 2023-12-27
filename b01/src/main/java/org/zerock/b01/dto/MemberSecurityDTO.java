package org.zerock.b01.dto;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {

    private String mid,mpw, email;

    private Boolean del, social;

    public MemberSecurityDTO(String username, String password, String email, Boolean del, Boolean social,
                            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        mid = username;
        mpw = password;
        this.email = email;
        this.del = del;
        this.social = social;
    }
}
