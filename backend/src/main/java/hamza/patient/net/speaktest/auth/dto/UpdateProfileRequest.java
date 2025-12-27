package hamza.patient.net.speaktest.auth.dto;

import hamza.patient.net.speaktest.common.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {

    private Language preferredLanguage;
}
