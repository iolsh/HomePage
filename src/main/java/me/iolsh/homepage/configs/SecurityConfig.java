package me.iolsh.homepage.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${homepage.char_secret}")
    private String char_secret;
    @Value("${homepage.rememberMeKey}")
    private String rememberMeKey;

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("tokenRepository")
    PersistentTokenRepository tokenRepository;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userDetailsService);
        return dao;
    }

    @Bean
    public ProviderManager providerManager() {
        List<AuthenticationProvider> list = new ArrayList<AuthenticationProvider>();
        list.add(daoAuthenticationProvider());
        return new ProviderManager(list);
    }

    @Bean
    RememberMeServices rememberMeServices() {
        RememberMeServices services = new PersistentTokenBasedRememberMeServices(rememberMeKey,
                userDetailsService, tokenRepository);
        //services.setParameter("remember-me"); //TODO check
        return services;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**")
                .antMatchers("/css/**")
                .antMatchers("/img/**")
                .antMatchers("/js/**")
                .antMatchers("/webjars/**")
                .antMatchers("/i18n/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login")
                .and().logout().logoutSuccessUrl("/")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                .rememberMe().rememberMeServices(rememberMeServices()).key(rememberMeKey)
                .and()
                .httpBasic()
                .realmName("HomePageApp")
                .and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/notes").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/notes/comment").authenticated()
                .anyRequest().permitAll();
                //.and().requiresChannel().anyRequest().requiresSecure();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new StandardPasswordEncoder(char_secret));
    }
}
