package hamza.patient.net.speaktest.auth.dto;

import hamza.patient.net.speaktest.common.enums.Language;
import hamza.patient.net.speaktest.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private UUID userId;
    private String username;
    private String email;
    private UserRole role;
    private Language preferredLanguage;
    private LocalDateTime createdAt;
}
