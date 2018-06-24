package com.ticketmonster.movie_beast.helpers.config;

import com.ticketmonster.movie_beast.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@Configurable
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    DataSource dataSource;

    @Autowired
    private HttpLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT email, password, enabled FROM user WHERE email = ?")
                .authoritiesByUsernameQuery("SELECT email, role FROM user WHERE email = ?")
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/",
                        "/register",
                        "/login","/users", "/users/**").permitAll()
                .antMatchers("/theatres", "/theatres/**",
                        "/movies", "/movies/**",
                        "/cities", "/cities/**",
                        "/logout").fullyAuthenticated()
                .and()
                //.antMatchers("/users", "/users/**").access("hasAuthority('ADMIN')").and()
                .logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .logoutSuccessHandler(logoutSuccessHandler).logoutSuccessUrl("/login").invalidateHttpSession(true).and()
                .httpBasic().and()
                .sessionManagement().invalidSessionUrl("/login").maximumSessions(1).expiredUrl("/login").and()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER) // <- TODO: figure out optimal SessionCreationPolicy
                .and().csrf().disable(); // <- TODO: enable csrf when done.
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                        .allowedHeaders("Content-Type", "Date", "Total-Count", "loginInfo")
                        .exposedHeaders("Content-Type", "Date", "Total-Count", "loginInfo")
                        .maxAge(3600);
            }
        };
    }
}
