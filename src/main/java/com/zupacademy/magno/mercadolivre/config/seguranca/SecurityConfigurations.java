package com.zupacademy.magno.mercadolivre.config.seguranca;

import com.zupacademy.magno.mercadolivre.config.seguranca.autenticacao.AuthService;
import com.zupacademy.magno.mercadolivre.config.seguranca.autenticacao.AuthTokenFilter;
import com.zupacademy.magno.mercadolivre.config.seguranca.autenticacao.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    // Autenticacao
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // Configuracoes de Autorizacao
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/auth").permitAll()
                    .antMatchers(HttpMethod.POST, "/usuario").permitAll()
                    .antMatchers(HttpMethod.GET, "/produto/{id}").permitAll()
                    .antMatchers(HttpMethod.POST, "/compra/retorno").permitAll()
                    .antMatchers(HttpMethod.POST, "/notas-fiscais").permitAll()
                    .antMatchers(HttpMethod.POST, "/ranking").permitAll()
                    .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthTokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
    }

    // Recursos estaticos
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
