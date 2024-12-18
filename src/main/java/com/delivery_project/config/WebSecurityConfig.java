package com.delivery_project.config;

import com.delivery_project.enums.UserRoleEnum;
import com.delivery_project.jwt.JwtUtil;
import com.delivery_project.security.JwtAuthenticationFilter;
import com.delivery_project.security.JwtAuthorizationFilter;
import com.delivery_project.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/api/user/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/{username}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/user/{username}").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/user/{username}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/ai").permitAll()

                        //주문 생성
                        .requestMatchers(HttpMethod.POST,"/api/order/")
                        .hasAnyAuthority(UserRoleEnum.CUSTOMER.getAuthority())

                        //주문 목록 조회
                        .requestMatchers(HttpMethod.GET,"/api/order/")
                        .hasAnyAuthority(
                                UserRoleEnum.OWNER.getAuthority(),
                                UserRoleEnum.MANAGER.getAuthority(),
                                UserRoleEnum.MASTER.getAuthority()
                        )

                        //단일 항목 주문 조회
                        .requestMatchers(HttpMethod.GET,"/api/order/{orderId}")
                        .hasAnyAuthority(
                                UserRoleEnum.CUSTOMER.getAuthority(),
                                UserRoleEnum.OWNER.getAuthority(),
                                UserRoleEnum.MANAGER.getAuthority(),
                                UserRoleEnum.MASTER.getAuthority()
                        )

                        //주문 취소
                        .requestMatchers(HttpMethod.PATCH,"/api/order/{orderId}")
                        .hasAnyAuthority(
                                UserRoleEnum.CUSTOMER.getAuthority(),
                                UserRoleEnum.OWNER.getAuthority(),
                                UserRoleEnum.MANAGER.getAuthority(),
                                UserRoleEnum.MASTER.getAuthority()
                        )

                        //리뷰 생성
                        .requestMatchers(HttpMethod.POST,"/api/review")
                        .hasAnyAuthority(
                                UserRoleEnum.CUSTOMER.getAuthority(),
                                UserRoleEnum.OWNER.getAuthority(),
                                UserRoleEnum.MANAGER.getAuthority(),
                                UserRoleEnum.MASTER.getAuthority()
                        )

                        //리뷰 목록 조회
                        .requestMatchers(HttpMethod.GET,"/api/review")
                        .hasAnyAuthority(
                                UserRoleEnum.ANONYMOUS.getAuthority(),
                                UserRoleEnum.CUSTOMER.getAuthority(),
                                UserRoleEnum.OWNER.getAuthority(),
                                UserRoleEnum.MANAGER.getAuthority(),
                                UserRoleEnum.MASTER.getAuthority()
                        )

                        //단일 리뷰 조회
                        .requestMatchers(HttpMethod.GET,"/api/review/{reviewId}")
                        .hasAnyAuthority(
                                UserRoleEnum.ANONYMOUS.getAuthority(),
                                UserRoleEnum.CUSTOMER.getAuthority(),
                                UserRoleEnum.OWNER.getAuthority(),
                                UserRoleEnum.MANAGER.getAuthority(),
                                UserRoleEnum.MASTER.getAuthority()
                        )

                        //리뷰 삭제
                        .requestMatchers(HttpMethod.PATCH,"/api/review/{reviewId}")
                        .hasAnyAuthority(
                                UserRoleEnum.CUSTOMER.getAuthority(),
                                UserRoleEnum.OWNER.getAuthority(),
                                UserRoleEnum.MANAGER.getAuthority(),
                                UserRoleEnum.MASTER.getAuthority()
                        )


                        //결제 생성
                        .requestMatchers(HttpMethod.POST, "/api/payment/{orderId}")
                        .hasAnyAuthority(UserRoleEnum.CUSTOMER.getAuthority())

                        //결제 단일 항목 조회
                        .requestMatchers(HttpMethod.GET, "/api/payment/{paymentId}")
                        .hasAnyAuthority(
                                UserRoleEnum.CUSTOMER.getAuthority(),
                                UserRoleEnum.OWNER.getAuthority(),
                                UserRoleEnum.MANAGER.getAuthority(),
                                UserRoleEnum.MASTER.getAuthority()
                        )

                        //결제 목록 조회
                        .requestMatchers(HttpMethod.GET, "/api/payment")
                        .hasAnyAuthority(
                                UserRoleEnum.CUSTOMER.getAuthority(),
                                UserRoleEnum.OWNER.getAuthority(),
                                UserRoleEnum.MANAGER.getAuthority(),
                                UserRoleEnum.MASTER.getAuthority()
                        )

                        //결제 삭제
                        .requestMatchers(HttpMethod.PATCH, "/api/payment/{paymentId}")
                        .hasAnyAuthority(
                                UserRoleEnum.CUSTOMER.getAuthority(),
                                UserRoleEnum.OWNER.getAuthority(),
                                UserRoleEnum.MANAGER.getAuthority(),
                                UserRoleEnum.MASTER.getAuthority()
                        )

                       
                        .requestMatchers(HttpMethod.GET, "/api/menus/{restaurantId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/menus").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/category").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/restaurants/{restaurantId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/restaurants").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/restaurants/category/{categoryId}").permitAll()
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}