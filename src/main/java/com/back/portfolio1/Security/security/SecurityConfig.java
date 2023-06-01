
package com.back.portfolio1.Security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration

public class SecurityConfig {
    
    //1ºUn metodo que regule la cadena de filtros es decir la seguridad
    //con este metodo se configura la seguridad 
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        
        return httpSecurity
        
//no vamos a trabajar con formularios por eso lo deshabilito, solo configuramos una autenticacion 
        .csrf(config ->config.disable())
                
//configuro el acceso a las URL y a los endpoint                
        .authorizeHttpRequests(auth ->{
            auth.requestMatchers("/hello").permitAll();//se permite acceso a todos los que ingresen a este endpoint
            auth.anyRequest().authenticated();//cualquier otra ruta debe estar autenticado el usuario para acceder
        
        })
        
                
        .sessionManagement(session ->{
//politica de creacion de la sesion
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        })
//sirve para hacer una autenticación básica por el momento                 
        .httpBasic()
                .and()
        .build();
    
    }
    
//se crea un usuario para autenticación hasta que se cree los demás filtros
//nos ayuda a crear un usuario en memoria por ahora 
//la autenticacion basica de httpBasic se puede hacer con el usuario que estamos creando 
//para que el usuario pueda ser usado debe ser administrado por algun objeto que administre la autenticacion
//para ello tenemos el AuthenticationManager
    @Bean
    UserDetailsService userDetailsService(){
    
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("lorena")
               .password("1234")
               .roles()
               .build()); //para que construya el usuario
        
        return manager;
        
    }
    
    /*3º- PasswordEncoder por el momento no hacemos la encriptacion, solo le 
    vamos a indicar un metodo de no encriptado */
    @Bean
    PasswordEncoder passwordEncoder(){
    
        return NoOpPasswordEncoder.getInstance();
    
    }
    
    
    
    /*2º- Adicional al usuario InMemory necesitamos una autenticacion manager,
se encarga de la administracion de la autenticacion del usuario  
    se necesita un passwordEncoder porque spring security requiere que 
    encriptemos las password
    No va a dejar pasar ningun usuario que no tenga una contraseña encriptada o 
    una politica de encriptacion de contraseñas */
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception{
    
        return httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder)
                .and().build();
    
    }
    
}
