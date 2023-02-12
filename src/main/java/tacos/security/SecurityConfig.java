package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tacos.data.UserRepository;
import tacos.models.User;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                .authorizeHttpRequests()
                    .requestMatchers("/design", "/orders").hasRole("USER")
                    .requestMatchers("/", "/**","/h2-console/**", "/h2-console/").permitAll()

                .and()
                    .formLogin()
                        .loginPage("/login")
                    .defaultSuccessUrl("/design", true)
                    .loginProcessingUrl("/login")
                    .failureUrl("/")
                    .usernameParameter("username")
                    .passwordParameter("password")

                .and()
                    .oauth2Login()
                        .loginPage("/login")

                .and()
                    .logout()
                        .logoutSuccessUrl("/")

                .and()
                .csrf().disable();
         http.headers().frameOptions().disable();
        return http
                    .build();
    }
}
