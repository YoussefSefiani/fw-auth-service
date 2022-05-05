package fw.authservice.security;

import fw.authservice.jwt.JwtConfig;
import fw.authservice.jwt.JwtTokenVerifier;
import fw.authservice.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import fw.authservice.repository.UserRepository;
import fw.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final UserService userService;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, SecretKey secretKey, JwtConfig jwtConfig, UserRepository userRepository, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf()
                .disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey, userService)) // authentificationManager comes from extended class
                    .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/user").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .httpBasic();



    }
}

