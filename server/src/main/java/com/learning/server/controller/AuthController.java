package com.learning.server.controller;

import com.learning.server.dto.UserDTO;
import com.learning.server.response.ApiResponse;
import com.learning.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("ERROR", "Chưa đăng nhập", null));
            }

            // Xử lý OAuth2 Authentication
            if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
                OAuth2User oauth2User = oauth2Token.getPrincipal();

                // Lấy thông tin từ OAuth2 user attributes
                String email = oauth2User.getAttribute("email");
                String name = oauth2User.getAttribute("name");

                if (email == null || name == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ApiResponse<>("ERROR", "Không thể lấy thông tin email hoặc tên từ OAuth2", null));
                }

                UserDTO userDTO = userService.findOrCreateUser(email, name);
                return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Lấy thông tin user thành công", userDTO));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", "Loại authentication không được hỗ trợ", null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("ERROR", "Lỗi server: " + e.getMessage(), null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Invalidate session
            if (request.getSession(false) != null) {
                request.getSession().invalidate();
            }

            // Clear cookies
            response.addHeader("Set-Cookie", "JSESSIONID=; Path=/; HttpOnly; Max-Age=0");

            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Đăng xuất thành công", "LOGGED_OUT"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("ERROR", "Lỗi khi đăng xuất: " + e.getMessage(), null));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<String>> getAuthStatus(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Đã đăng nhập", "AUTHENTICATED"));
        }
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Chưa đăng nhập", "UNAUTHENTICATED"));
    }
}
