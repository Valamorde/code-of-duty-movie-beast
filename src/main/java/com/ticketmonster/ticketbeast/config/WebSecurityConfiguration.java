package com.ticketmonster.ticketbeast.config;

import com.ticketmonster.ticketbeast.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepository userRepository;
    //ALSO KINDA WORKS
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.csrf().disable();
//
//        // The pages does not require login
//        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
//
//        // /userInfo page requires login as ROLE_USER or ROLE_ADMIN.
//        // If no login, it will redirect to /login page.
//        http.authorizeRequests().antMatchers("/users").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
//
//        // For ADMIN only.
//        http.authorizeRequests().antMatchers("/events").access("hasRole('ROLE_ADMIN')");
//
//        // When the user has logged in as XX.
//        // But access a page that requires role YY,
//        // AccessDeniedException will be thrown.
//        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
//
//        // Config for Login Form
//        http.authorizeRequests().and().formLogin()//
//                // Submit URL of login page.
//                .loginProcessingUrl("/login") // Submit URL
//                .defaultSuccessUrl("/users")//
//                .failureUrl("/login?error=true")//
//                .usernameParameter("username")//
//                .passwordParameter("password")
//                // Config for Logout Page
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/events");
//
//    }
    @Autowired
    DataSource dataSource;

//      THIS WORKS
//    /** This method is used for override HttpSecurity of the web Application.
//     * We can specify our authorization criteria inside this method.
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and()
//                // starts authorizing configurations
//                .authorizeRequests()
//                // ignoring the guest's urls "
//                .antMatchers("/", "/register", "/login", "/logout").permitAll()
//                // authenticate all remaining URLS
//                .anyRequest().fullyAuthenticated().and()
//                /* "/logout" will log the user out by invalidating the HTTP Session,
//                 * cleaning up any {link rememberMe()} authentication that was configured, */
//                .logout()
//                .permitAll()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
//                .and()
//                // enabling the basic authentication
//                .httpBasic().and()
//                // configuring the session on the server
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
//                // disabling the CSRF - Cross Site Request Forgery
//                .csrf().disable();
//    }

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

        http.csrf().disable(); // disable this when testing is done

        // No Login Required
        http.authorizeRequests()
                .antMatchers("/", "/login", "/register", "/logout").permitAll();
        // Requires User Level
        http.authorizeRequests().antMatchers("/events/**").access("hasAnyRole('USER', 'ADMIN')"); //TODO: figure the roles out
        // Requires Admin Level
        http.authorizeRequests().antMatchers("/users/**").access("hasRole('ADMIN')");
        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will be thrown.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");


        // Login Configuration
        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/loginurl")
                .defaultSuccessUrl("/events")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout");
    }
}
