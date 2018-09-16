package restapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public SpringSecurityConfig(UserDetailsServiceImp userDetailsServiceImp, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").hasAnyRole("ADMIN","USER")
                .and().httpBasic().realmName("ExpenceStack")
                .authenticationEntryPoint(authenticationEntryPoint)

                ;
                //.and().formLogin().loginPage("/login");
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/user");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImp).passwordEncoder(bCryptPasswordEncoder);
    }

/*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
// add our users for in memory authentication

        auth.inMemoryAuthentication().withUser("user1").password("1").roles("EMPLOYEE");
        auth.inMemoryAuthentication().withUser("manager").password("2").roles("MANAGER");
        auth.inMemoryAuthentication().withUser("admin").password("3").roles("ADMIN");

    }
*/
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsServiceImp).passwordEncoder(passwordEncoder);
    }

}
