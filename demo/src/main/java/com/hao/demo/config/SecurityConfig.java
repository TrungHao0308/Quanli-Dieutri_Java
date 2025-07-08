
    package com.hao.demo.config;

    import com.hao.demo.service.CustomUserDetailsService;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.*;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.*;
    import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

    import java.io.IOException;
    
    import javax.swing.Spring;

    @Configuration  // lớp cấu hình
    @EnableWebSecurity  // kích hoạt Spring security
    public class SecurityConfig {

        // Spring Security lấy thông tin người dùng từ database
        private final CustomUserDetailsService customUserDetailsService;

        @Autowired
        public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
            this.customUserDetailsService = customUserDetailsService;
        }

        // Mã hóa BBrypt lưu vào db, không lưu plain text
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
            return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .csrf(csrf -> csrf.disable()) // tắt CSRF: tấn công giả mạo từ yêu cầu của web khác
                // Cho phép truy cập tự do
                .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/", "/trangchu", "/phacdodieutri", "/doingubacsi", "/cosoyte", "/auth/**", "/css/**", "/js/**", "/images/**").permitAll()
                // các request đúng vai trò mới thực hiện, nghĩa là đăng nhập mới xem
                    .requestMatchers(HttpMethod.POST, "/admin/save").hasRole("ADMIN")
                    .requestMatchers("/admin/**").hasRole("ADMIN")
    .requestMatchers("/doctor/**").hasRole("DOCTOR")
    .requestMatchers("/manager/**").hasRole("MANAGER")
    .requestMatchers("/customer/**").hasRole("CUSTOMER")

                    .anyRequest().authenticated()
                )
                .formLogin(form -> form
                    .loginPage("/auth/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(customSuccessHandler()) // chuyển hướng đúng theo vai trò
                    .failureUrl("/auth/login?error")
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/auth/login?logout")
                    .permitAll()
                );

            return http.build();
        }

        @Bean
        public AuthenticationSuccessHandler customSuccessHandler() {
            return new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    Authentication authentication) throws IOException, ServletException {
                    var authorities = authentication.getAuthorities();

                    if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
        response.sendRedirect("/admin");  // chuyển hướng
    } else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_DOCTOR"))) {
        response.sendRedirect("/doctor");
    } else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_MANAGER"))) {
        response.sendRedirect("/manager");
    } else {
        response.sendRedirect("/customer");
    }

                }
            };
        }
    }

    // khi dùng Bean: Spring tạo ra đối tượng đó 1 lần duy nhất và tái sử dụng