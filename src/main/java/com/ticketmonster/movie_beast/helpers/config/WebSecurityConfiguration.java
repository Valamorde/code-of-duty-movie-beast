package com.ticketmonster.movie_beast.helpers.config;

import com.ticketmonster.movie_beast.helpers.handlers.CustomLogoutSuccessHandler;
import com.ticketmonster.movie_beast.helpers.security.JwtAuthenticationEntryPoint;
import com.ticketmonster.movie_beast.helpers.security.JwtAuthorizationTokenFilter;
import com.ticketmonster.movie_beast.helpers.security.JwtTokenUtil;
import com.ticketmonster.movie_beast.helpers.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Value("${jwt.header}")
    private String tokenHeader;
    @Value("${jwt.route.authentication.path}")
    private String authenticationPath;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new DataSourceConfig() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") // http://localhost:4200/
                        .allowedMethods("*") // "GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"
                        .allowedHeaders("*") // "Content-Type", "Date", "Total-Count", "loginInfo", "application/json", "Authorization"
                        .exposedHeaders("Content-Type", "Date", "Total-Count", "loginInfo", "application/json", "Authorization")
                        .maxAge(3600);
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()//.httpBasic().and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/", "/auth","/auth/**", "/register","/register/**", "/error/**", "/loggedUser/**").permitAll()
                .antMatchers("/cities/**", "/theatres/**", "/movies/**", "/shows/**",
                        "/bookings/**", "/seatReservation/**").fullyAuthenticated()
                .antMatchers("/users", "/bookingsReport").access("hasAuthority('ROLE_ADMIN')")
                .anyRequest().authenticated();

        http.logout().permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .logoutSuccessHandler(logoutSuccessHandler).invalidateHttpSession(true);

        JwtAuthorizationTokenFilter authenticationTokenFilter = new JwtAuthorizationTokenFilter(userDetailsService(), jwtTokenUtil, tokenHeader);
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class).headers().cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // AuthenticationTokenFilter will ignore the below paths
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        authenticationPath
                )
                .and()
                .ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                );
        web
                .ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public");
    }
}
