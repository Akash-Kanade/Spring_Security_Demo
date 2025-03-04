package com.security.config;

import com.security.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    CustomUserDetailService userDetailService;


    SecurityConfiguration(CustomUserDetailService userDetailService)
    {
        this.userDetailService = userDetailService;
    }
    // This will be configure the security filter chain and along with it will configure Authentication filter
    @Bean
    public SecurityFilterChain createSecurityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("user/register", "user/login").permitAll()
                                .anyRequest().authenticated()
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



   // @Bean    // This method is commented because we are using custom UserDetailsService to authenticate from the database
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

// Instead of providing a UserDetailService directly like a above method
    // we are creating bean of authentication provider which will further give us UserDeatilSerive and UserDetails implmentation.
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(getEncoder());
        return provider;
    }

    // Creating the bean of BCryptPasswordEncoder so we can encrypt password whenever we need by injecting its bean.
    @Bean
    public BCryptPasswordEncoder getEncoder()
    {
        return new BCryptPasswordEncoder(14);
    }



}

