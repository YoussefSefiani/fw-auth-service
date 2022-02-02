package fw.authservice.security;

import fw.authservice.jwt.JwtConfig;
import fw.authservice.jwt.JwtTokenVerifier;
import fw.authservice.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import fw.authservice.service.MyUserDetailsService;
import fw.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    //private final InfluencerService influencerService;
    private final UserDetailsService userDetailsService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder, /*InfluencerService influencerService,*/ UserDetailsService userDetailsService, SecretKey secretKey, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        //this.influencerService = influencerService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey)) // authentificationManager comes from extended class
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/api/influencer").permitAll()
//                 .antMatchers(HttpMethod.GET, "/api/influencer/**").permitAll()
//                .antMatchers("/")
//                .permitAll()
//                .and()
//               .permitAll()
//                .formLogin();
//                .and()
//                .httpBasic();
                .authorizeRequests()
                //.antMatchers("/").hasRole("ADMIN")
               .antMatchers("/api/influencer").hasAuthority("USER")
                //.antMatchers("/").authenticated()
                //.antMatchers("/").permitAll();
                .anyRequest()
                .authenticated();

              //  .and().formLogin();


    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails influencer = User.builder()
                .username("influencer")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(
                influencer
        );
    }


}

