package com.ishan.auth.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(
                User.withUsername("user1")
                        .password(passwordEncoder.encode("password1"))
                        .roles("ADMIN", "CREDIT", "DEBIT")
                        .build()
        );

        manager.createUser(
                User.withUsername("user2")
                        .password(passwordEncoder.encode("password2"))
                        .roles("CREDIT", "DEBIT")
                        .build()
        );

        manager.createUser(
                User.withUsername("user3")
                        .password(passwordEncoder.encode("password3"))
                        .roles("ADMIN")
                        .build()
        );

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
