package org.sid.authservice.sec;

import org.sid.authservice.sec.entities.AppUser;
import org.sid.authservice.sec.filters.JWTAuthenticationFilter;
import org.sid.authservice.sec.filters.JWTAuthorizationFilter;
import org.sid.authservice.sec.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AccountService accountService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((username)->{
            AppUser appUser=accountService.loadUserByUsername(username);
            Collection<GrantedAuthority> authorities= appUser.getAppRoles()

                    .stream().map((role)-> new SimpleGrantedAuthority(role.getRoleName()))
                    .collect(Collectors.toList());

            return new User(appUser.getUsername(),appUser.getPassword(),authorities);
        });
}
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//http.formLogin();
        http.authorizeRequests().antMatchers("/login/**","/refreshToken/**","/h2-console/**").permitAll();
//http.authorizeRequests().antMatchers(HttpMethod.GET,"/users/**").hasAnyAuthority("USER");
//http.authorizeRequests().antMatchers(HttpMethod.POST,"/users/**").hasAnyAuthority("ADMIN");
//http.authorizeRequests().antMatchers(HttpMethod.POST,"/roles/**").hasAnyAuthority("ADMIN");
//http.authorizeRequests().antMatchers(HttpMethod.POST,"/addRoleToUser/**").hasAnyAuthority("ADMIN");
        http.headers().frameOptions().disable();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception { return super.authenticationManagerBean();} }
