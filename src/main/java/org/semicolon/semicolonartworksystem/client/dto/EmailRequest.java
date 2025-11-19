package org.semicolon.semicolonartworksystem.client.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequest {
    @NotBlank
    private String to;
    private String subject;
    @NotBlank
    private String body;
}
