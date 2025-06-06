package com.luv2code.springboot.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    // add support for JDBC ... no more hardcoded users :-)

    /**
     * Configures and provides a {@link UserDetailsManager} bean for handling user authentication and authorization
     * with JDBC. Custom SQL queries are set to retrieve user details and roles from the database.
     *
     * @param dataSource the {@link DataSource} to connect to the database containing authentication and authorization data
     * @return a configured instance of {@link JdbcUserDetailsManager} used for managing user details and roles
     */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from members where user_id=?"
        );

        // define query to retrieve the authorities/roles by username

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?"
        );

        return jdbcUserDetailsManager;
    }


    /**
     * Configures the security filter chain for HTTP security, defining access control rules
     * for various HTTP methods and endpoints. It also sets up HTTP Basic authentication
     * and disables CSRF protection.
     *
     * @param hhttp the {@link HttpSecurity} object used to configure security settings
     * @return a {@link SecurityFilterChain} object built from the configured HTTP security settings
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity hhttp) throws Exception{
        hhttp.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET,"/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("EMPLOYEE")

                        .requestMatchers(HttpMethod.POST,"/api/employees").hasRole("MANAGER")

                        .requestMatchers(HttpMethod.PUT,"/api/employees").hasRole("MANAGER")

                        .requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasRole("ADMIN")

                );

        // use HTTP Basic authentication
        hhttp.httpBasic(Customizer.withDefaults());

        // disable Cross Site Request Forgery (CSRF)
        // in general, not required for stateless REST APIs that use POST, DELETE and/ or PATCH

        hhttp.csrf(csrf->csrf.disable());

        return hhttp.build();
        }
}


/*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("EMPLOYEE","MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE","MANAGER","ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john,mary,susan);
    } */

