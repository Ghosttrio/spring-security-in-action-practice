package com.ghosttrio.security.config;

import com.ghosttrio.security.filter.RequestValidationFilter;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin();
        http.httpBasic();

        http.authorizeRequests()
                .anyRequest()
                .hasAuthority("WRITE");

        http.authorizeRequests()
                .anyRequest()
                .access("hasAuthority('WRITE')");

        http.authorizeRequests()
                .mvcMatchers("/hello").hasRole("ADMIN")
                .mvcMatchers("/test").hasRole("MANAGER")
                .anyRequest().permitAll();

        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/a")
                .authenticated()
                .mvcMatchers(HttpMethod.POST, "/a")
                .permitAll()
                .anyRequest().denyAll();

        http.addFilterBefore(
                new RequestValidationFilter(),
                BasicAuthenticationFilter.class
        ).authorizeRequests()
                .anyRequest().permitAll();

        http.csrf(c -> c.ignoringAntMatchers("/hello"));

//        http.csrf(c -> {
//            c.csrfTokenRepository(customTokenRepository());
//            c.ignoringAntMatchers("/hello");
//        });

        http.cors(c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("test.com", "test2.com"));
                config.setAllowedMethods(List.of("GET", "POST"));
                return config;
            };
            c.configurationSource(source);
        });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetails build = User.withUsername("bill")
                .password("12345")
                .authorities("read", "write")
                .accountExpired(false)
                .disabled(true)
                .build();


//        var userDetailsService = new InMemoryUserDetailsManager();
//        var user = User.withUsername("john")
//                .password("12345")
//                .authorities("read")
//                .build();
//        userDetailsService.createUser(user);
//
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        var userDetailsService = new InMemoryUserDetailsManager();
//        var user = User.withUsername("john")
//                .password("12345")
//                .authorities("read")
//                .build();
//        userDetailsService.createUser(user);
//        return userDetailsService;
//    }
//

    @Bean
    public InitializingBean initializingBean() {
        return () -> SecurityContextHolder.setStrategyName(
                SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
}
