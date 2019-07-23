package com.jason.exam.web.config;

import com.jason.exam.model.Account;
import com.jason.exam.security.SecurityAccount;
import com.jason.exam.service.IAccountService;
import com.jason.exam.dao.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired(required=true)
    IAccountService accountService; 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().
        antMatchers("/lib/**","/h-ui/**","/h-ui.admin/**").permitAll()
        .anyRequest().authenticated().
        and().formLogin().loginPage("/login").permitAll().successHandler(loginSuccessHandler())
        .and().headers().frameOptions().disable().
        and().logout().permitAll().logoutSuccessHandler(logoutSuccessHandler()).invalidateHttpSession(true).
        deleteCookies("JSESSIONID").
        and().sessionManagement().maximumSessions(10).expiredUrl("/login");
		http.csrf().disable();
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { //密码加密
        return new BCryptPasswordEncoder(4);
    }


    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() { //登出处理
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                try {
                	SecurityAccount user = (SecurityAccount) authentication.getPrincipal();
                    System.out.println("USER : " + user.getUsername() + " LOGOUT SUCCESS !  ");
                } catch (Exception e) {
                	System.out.println("LOGOUT EXCEPTION , e : " + e.getMessage());
                }
                httpServletResponse.sendRedirect("/login");
            }
        };
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() { //登入处理
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                Account userDetails = (Account) authentication.getPrincipal();
                System.out.println("USER : " + userDetails.getAccountname() + " LOGIN SUCCESS !  ");
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }


    @Bean
    public UserDetailsService userDetailsService() {    //用户登录实现
        return new UserDetailsService() {
            @Autowired(required=true)
            private AccountRepository accountRepository;

            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                Account account = accountRepository.findByAccountname(s);
                if (account == null) throw new UsernameNotFoundException("Username " + s + " not found");
                return new SecurityAccount(accountService,account);
            }
        };
    }

}
