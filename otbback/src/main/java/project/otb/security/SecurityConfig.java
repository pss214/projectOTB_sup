package project.otb.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // security 비활성화
        http
                        .csrf(csrf -> csrf.disable())
                        .cors(cors -> cors.disable())
                        .authorizeHttpRequests(request -> request
                                .anyRequest().permitAll());
//        http
//                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
//                .csrf(csrf -> csrf.disable())
//
//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .accessDeniedHandler(jwtAccessDeniedHandler)
//                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                )
//
//                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//                        .requestMatchers("/api/hello", "/api/authenticate", "/api/signup").permitAll()
//                        .requestMatchers(PathRequest.toH2Console()).permitAll()
//                        .anyRequest().authenticated()
//                )
//
//                // 세션을 사용하지 않기 때문에 STATELESS로 설정
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                // enable h2-console
//                .headers(headers ->
//                        headers.frameOptions(options ->
//                                options.sameOrigin()
//                        )
//                )
//                .apply(new JwtSecurityConfig(tokenProvider));
        return http.build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource(){UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // localhost:3000 허용
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(false);
        config.applyPermitDefaultValues();

        source.registerCorsConfiguration("/**",config);
        return source;

    }

}
