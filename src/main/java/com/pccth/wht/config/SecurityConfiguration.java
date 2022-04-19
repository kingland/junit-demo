package com.pccth.wht.config;

import com.pccth.wht.async.AsyncTaskExecutorExceptionHandling;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy( SessionCreationPolicy.STATELESS )
                .and()
                //.addFilterAfter(tokenFilter(new AndRequestMatcher( new AntPathRequestMatcher( "/" ) )), UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(customFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                //.antMatchers("/sso").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web
            .ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/**/*.{js,css}")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/h2-console/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
