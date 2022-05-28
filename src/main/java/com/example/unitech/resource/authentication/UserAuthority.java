package com.example.unitech.resource.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthority implements GrantedAuthority {

    private final long serialVersionUID = 2348357432L;

    private String authority;

}
