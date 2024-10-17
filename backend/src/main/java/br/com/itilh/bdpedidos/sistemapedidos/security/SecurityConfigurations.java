package br.com.itilh.bdpedidos.sistemapedidos.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.config.AuditingConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.swagger.v3.core.filter.SpecFilter;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableWebSecurity
@OpenAPIDefinition(info = @Info(title = "Sistema de Pedidos", version = "v1"))
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class SecurityConfigurations {

    @Autowired
    SpecFilter securityFilter;

    @Bean
    public <SecurityFilterChain, HttpSecurity> SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return ((Object) httpSecurity).csrf(csrf-> csrf.disable())
        .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests( authorize -> authorize                        
                        .requestMatchers(HttpMethod.POST, "/auth/login").anonymous()
                        .requestMatchers(HttpMethod.POST, "/auth/registro").anonymous()
                        .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                        )
                        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuditingConfiguration authenticationConfiguration) throws Exception{
        return ((Object) authenticationConfiguration).getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();   
    }
    

}
