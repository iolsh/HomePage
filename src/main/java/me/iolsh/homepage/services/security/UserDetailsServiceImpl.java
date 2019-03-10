package me.iolsh.homepage.services.security;

import me.iolsh.homepage.model.User;
import me.iolsh.homepage.repositories.RoleRepository;
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
    private RoleRepository roleRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO ensure if roles are loaded for user
        Optional<User> userOptional = userRepository.findByUserName(username);
        if(userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User %s not found!", username));
        }
        User user = userOptional.get();
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(i -> new SimpleGrantedAuthority("ROLE_" + i.getRole()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }
}
