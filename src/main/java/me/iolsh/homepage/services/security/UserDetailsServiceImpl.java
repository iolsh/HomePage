package me.iolsh.homepage.services.security;

import me.iolsh.homepage.model.User;
import me.iolsh.homepage.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserName(username);
        User user = userOptional
            .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found!", username)));
        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(i -> new SimpleGrantedAuthority("ROLE_" + i.getRole()))
            .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
            user.isEnabled(), true, true, true, authorities);
    }
}
