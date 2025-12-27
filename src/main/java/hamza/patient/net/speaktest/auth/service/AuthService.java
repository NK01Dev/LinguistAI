package hamza.patient.net.speaktest.auth.service;

import hamza.patient.net.speaktest.auth.domain.User;
import hamza.patient.net.speaktest.auth.dto.LoginRequest;
import hamza.patient.net.speaktest.auth.dto.LoginResponse;
import hamza.patient.net.speaktest.auth.dto.RegisterRequest;
import hamza.patient.net.speaktest.auth.dto.UserProfileDto;
import hamza.patient.net.speaktest.auth.repository.UserRepository;
import hamza.patient.net.speaktest.common.enums.Language;
import hamza.patient.net.speaktest.common.enums.UserRole;
import hamza.patient.net.speaktest.config.JwtService;
import hamza.patient.net.speaktest.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        log.info("Registering new user: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("User with this email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("User with this username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .preferredLanguage(
                        request.getPreferredLanguage() != null ? request.getPreferredLanguage() : Language.EN)
                .build();

        user = userRepository.save(user);
        log.info("User registered successfully: {}", user.getUserId());

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return LoginResponse.of(
                accessToken,
                refreshToken,
                jwtService.getJwtExpiration(),
                mapToUserProfileDto(user));
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        log.info("User login attempt: {}", request.getUsernameOrEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()));

        User user = userRepository.findByUsername(request.getUsernameOrEmail())
                .or(() -> userRepository.findByEmail(request.getUsernameOrEmail()))
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        log.info("User logged in successfully: {}", user.getUserId());

        return LoginResponse.of(
                accessToken,
                refreshToken,
                jwtService.getJwtExpiration(),
                mapToUserProfileDto(user));
    }

    @Transactional(readOnly = true)
    public LoginResponse refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new BadRequestException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        return LoginResponse.of(
                newAccessToken,
                newRefreshToken,
                jwtService.getJwtExpiration(),
                mapToUserProfileDto(user));
    }

    private UserProfileDto mapToUserProfileDto(User user) {
        return UserProfileDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .preferredLanguage(user.getPreferredLanguage())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
