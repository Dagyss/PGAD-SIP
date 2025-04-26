package unlu.sip.pga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Auth0UserDTO {
    @JsonProperty("user_id")
    private String userId;
    private String name;
    private String nickname;
    private String email;
    @JsonProperty("email_verified")
    private Boolean emailVerified;
    private String picture;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("last_login")
    private LocalDateTime lastLogin;
}