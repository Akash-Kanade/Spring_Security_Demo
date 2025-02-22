package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // This will be configure the security filter chain and along with it will configure Authentication filter
    @Bean
    public SecurityFilterChain createSecurityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request.anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults()) // For login form
                .httpBasic(Customizer.withDefaults()); // For verifying credentials as authorizeHttpRequest only will check
                                                       // if the request is authorised or not and if not then it will give
                                                       // 401 as unauthorised but won't provide a way to authorise user

        return security.build();
    }

    // This will configure the UserDetail and AuthenticationManger and Authentication filter is configured by
    // spring boot for the InMemoryUserDetailsManger so at this point there is no configuration needed for
    // Authentication manager and Authentication provider.
    // InMemoryUserDetailsService uses DaoAuthenticationProvider as a by default.
    @Bean
    public UserDetailsService userDetailsServiceConfigure()
    {
        UserDetails akash = User
                .withUsername("Akash")
                .password("{noop}Akash")// Password encoder should be included in order to work authentication.
                .roles("ADMIN")
                .build();

        UserDetails sanket = User.withUsername("Sanket")
                .password("{noop}Sanket")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(akash,sanket);
    }
}

