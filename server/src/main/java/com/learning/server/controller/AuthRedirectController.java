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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller xử lý OAuth2 redirect sau khi login thành công
 */
@RestController
@RequestMapping("/api/auth")
public class AuthRedirectController {

    @Autowired
    private UserService userService;

    @GetMapping("/success")
    public ResponseEntity<ApiResponse<UserDTO>> loginSuccess(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("ERROR", "Authentication thất bại", null));
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
                return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Đăng nhập OAuth2 thành công", userDTO));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", "Loại authentication không được hỗ trợ", null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("ERROR", "Lỗi server: " + e.getMessage(), null));
        }
    }

    @GetMapping("/failure")
    public ResponseEntity<ApiResponse<String>> loginFailure() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>("ERROR", "OAuth2 login thất bại", null));
    }

    @GetMapping("/test-session")
    public ResponseEntity<ApiResponse<String>> testSession(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Session còn hoạt động", "AUTHENTICATED"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>("ERROR", "Session không tồn tại hoặc đã hết hạn", "UNAUTHENTICATED"));
    }
}
