package com.ticketmonster.movie_beast.helpers.config;

import com.ticketmonster.movie_beast.repositories.UserRepository;
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

import javax.sql.DataSource;

@Configurable
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;

//    @Autowired
//    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT email, password, enabled FROM users WHERE email = ?")
                .authoritiesByUsernameQuery("SELECT email, role FROM users WHERE email = ?")
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                //.formLogin() //FIXME: <- temporarily disabled to allow CORS Authentication from Angular  ----
                //.loginProcessingUrl("/login").successHandler(authenticationSuccessHandler).and() //FIXME: <-|
                .authorizeRequests()
                .antMatchers("/",
                        "/register",
                        "/login").permitAll()
                .antMatchers("/theatres", "/theatres/**", "/seatReservation", "/seatReservation/**",
                        "/movies", "/movies/**",
                        "/cities", "/cities/**",
                        "/logout").fullyAuthenticated()
                .antMatchers("/users", "/users/**").access("hasAuthority('ADMIN')").and()
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
        return new MvcConfig() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") //FIXME: <- change to http://localhost:4200/
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                        .allowedHeaders("Content-Type", "Date", "Total-Count", "loginInfo", "application/json", "Authorization")
                        .exposedHeaders("Content-Type", "Date", "Total-Count", "loginInfo", "application/json", "Authorization")
                        .maxAge(3600);
            }
        };
    }
}
