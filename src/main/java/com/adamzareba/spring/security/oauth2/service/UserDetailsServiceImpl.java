package com.adamzareba.spring.security.oauth2.service;

import com.adamzareba.spring.security.oauth2.model.security.Role;
import com.adamzareba.spring.security.oauth2.model.security.User;
import com.adamzareba.spring.security.oauth2.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user != null) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            for(Role role : user.getRoles()) {
                Set<SimpleGrantedAuthority> innerAuthorities = role.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getName())).collect(Collectors.toSet());
                authorities.addAll(innerAuthorities);
            }
            user.setAuthorities(authorities);

            return user;
        }

        throw new UsernameNotFoundException(username);
    }
}
