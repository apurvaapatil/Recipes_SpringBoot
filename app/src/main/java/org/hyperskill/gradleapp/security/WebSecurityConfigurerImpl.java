package org.hyperskill.gradleapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    // To authenticate users, i.e, is the user logged in , etc
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(getEncoder());

//        auth.inMemoryAuthentication()
//                .withUser("user1").password(getEncoder().encode("pass1")).roles()
//                .and()
//                .withUser("user2").password(getEncoder().encode("pass2")).roles("USER")
//                .and()
//                .withUser("user3").password(getEncoder().encode("pass3")).roles("ADMIN")
//                .and()
//                .passwordEncoder(getEncoder());
    }

    // Who can access which endpoint
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .mvcMatchers("/api/register", "/h2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.formLogin()
            .usernameParameter("email") // by default the username is checked,
                                        // hence we'll change it to 'email' as we're logging the user through email & password
            .permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }

}
