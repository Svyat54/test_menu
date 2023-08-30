package org.klozevitz.test_menu.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/styles/*");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("").hasRole("MANAGER")
//                        .requestMatchers("/event/save", "/new_event").hasAnyRole("ADMIN", "USER")
//                        .requestMatchers("/profile", "/profile/update", "/logout", "/profile/activity/*",
//                                "/event/participate", "/event/roastOut", "event/filter").authenticated()
                        .requestMatchers("/register/chiefs", "/menu/register").hasRole("COMPANY")
                        .requestMatchers("/register/managerSubs").hasRole("MANAGER")
                        .requestMatchers("/register/chefSubs").hasRole("CHEF")
                        .requestMatchers("/register/bartenderSubs").hasRole("HEAD_BARTENDER")
                                .requestMatchers("/admin_user/**").hasRole("ADMIN")
                        .requestMatchers("/logout").authenticated()
                        .requestMatchers("/register/company").anonymous()
                        .requestMatchers("/auth/**", "/service/**", "/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .failureUrl("/login?error=true")
                        .permitAll()
                        .defaultSuccessUrl("/")
                );
        return http.build();
    }
}
