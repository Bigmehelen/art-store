package org.semicolon.semicolonartworksystem.controllers;

import jakarta.validation.Valid;
import org.semicolon.semicolonartworksystem.client.EmailSendingService;
import org.semicolon.semicolonartworksystem.client.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/email")
public class SendTest {

    @Autowired
    private EmailSendingService emailSendingService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
        return ResponseEntity.ok(emailSendingService.sendEmail(emailRequest).block());
    }
}
