package hamza.patient.net.speaktest.auth.controller;

import hamza.patient.net.speaktest.auth.dto.ChangePasswordRequest;
import hamza.patient.net.speaktest.auth.dto.UpdateProfileRequest;
import hamza.patient.net.speaktest.auth.dto.UserProfileDto;
import hamza.patient.net.speaktest.auth.service.UserService;
import hamza.patient.net.speaktest.common.constants.AppConstants;
import hamza.patient.net.speaktest.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(AppConstants.API_V1 + "/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User Management", description = "User profile and account management endpoints")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "Get current user profile", description = "Retrieve the profile of the authenticated user")
    public ResponseEntity<ApiResponse<UserProfileDto>> getCurrentUserProfile() {
        UserProfileDto profile = userService.getCurrentUserProfile();
        return ResponseEntity.ok(ApiResponse.success(profile));
    }

    @GetMapping("/{userId}/profile")
    @Operation(summary = "Get user profile by ID", description = "Retrieve user profile by user ID")
    public ResponseEntity<ApiResponse<UserProfileDto>> getUserProfile(@PathVariable UUID userId) {
        UserProfileDto profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(ApiResponse.success(profile));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update user profile", description = "Update the authenticated user's profile")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {
        UUID userId = userService.getCurrentUser().getUserId();
        UserProfileDto profile = userService.updateProfile(userId, request.getPreferredLanguage());
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", profile));
    }

    @PutMapping("/password")
    @Operation(summary = "Change password", description = "Change the authenticated user's password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {
        UUID userId = userService.getCurrentUser().getUserId();
        userService.changePassword(userId, request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
    }
}
