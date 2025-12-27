package hamza.patient.net.speaktest.common.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * User roles for authorization
 */
public enum UserRole {
    ADMIN("Administrator with full access"),
    USER("Regular user with limited access");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Convert to Spring Security GrantedAuthority
     */
    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority("ROLE_" + this.name());
    }
}
