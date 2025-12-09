package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.Customer;
import com.astafievvadim.mm_backend.payload.CustomerSignUpRequest;
import com.astafievvadim.mm_backend.payload.VerifyEmailRequest;
import com.astafievvadim.mm_backend.security.CustomUserDetailsService;
import com.astafievvadim.mm_backend.security.CustomUserDetails;
import com.astafievvadim.mm_backend.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final int RESEND_COOLDOWN_MS = 15000;
    private final Map<String, Long> codeSentTimestamps = new ConcurrentHashMap<>();
    private final CustomUserDetailsService userDetailsService;
    private final EmailService emailService;
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();

    public AuthController(CustomUserDetailsService userDetailsService, EmailService emailService) {
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email) {
        try {
            long now = System.currentTimeMillis();
            Long lastSent = codeSentTimestamps.get(email);

            if (lastSent != null && (now - lastSent) < RESEND_COOLDOWN_MS) {
                long waitSeconds = (RESEND_COOLDOWN_MS - (now - lastSent)) / 1000;
                return ResponseEntity.status(429)
                        .body(Map.of("error", "Please wait " + waitSeconds + " seconds before requesting a new code"));
            }

            CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

            String code = String.format("%06d", random.nextInt(1_000_000));
            verificationCodes.put(email, code);
            codeSentTimestamps.put(email, now);

            System.out.println("Sending verification code to " + email + ": " + code);

            emailService.sendVerificationCode(email, code);

            return ResponseEntity.ok(Map.of("message", "Verification code sent to email"));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email not found"));
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to send email"));
        }
    }

    @PostMapping("/resend-code")
    public ResponseEntity<?> resendCode(@RequestParam String email) {
        try {
            long now = System.currentTimeMillis();
            Long lastSent = codeSentTimestamps.get(email);

            if (lastSent != null && (now - lastSent) < RESEND_COOLDOWN_MS) {
                long waitSeconds = (RESEND_COOLDOWN_MS - (now - lastSent)) / 1000;
                return ResponseEntity.status(429)
                        .body(Map.of("error", "Please wait " + waitSeconds + " seconds before requesting a new code"));
            }

            CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

            String code = String.format("%06d", random.nextInt(1_000_000));
            verificationCodes.put(email, code);
            codeSentTimestamps.put(email, now);

            System.out.println("Resending verification code to " + email + ": " + code);

            emailService.sendVerificationCode(email, code);

            return ResponseEntity.ok(Map.of("message", "Verification code resent to email"));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email not found"));
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to send email"));
        }
    }


    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String email, @RequestParam String code) {

        String storedCode = verificationCodes.get(email);

        if (storedCode != null && storedCode.equals(code)) {
            CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);

            verificationCodes.remove(email);

            return ResponseEntity.ok(Map.of("message", "Authentication successful"));
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid verification code"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody CustomerSignUpRequest request) {
        try {
            if (userDetailsService.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
            }

            Customer customer = new Customer();
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setEmail(request.getEmail());

            userDetailsService.saveCustomer(customer);

            String code = String.format("%06d", random.nextInt(1_000_000));
            verificationCodes.put(request.getEmail(), code);

            // Return success response BEFORE sending email
            ResponseEntity<?> response = ResponseEntity.ok(Map.of("message", "Customer registered successfully. Verification code sent to email."));

            // Send email asynchronously (new thread, executor, or async method)
            new Thread(() -> {
                try {
                    System.out.println("Sending verification code to " + request.getEmail() + ": " + code);
                    emailService.sendVerificationCode(request.getEmail(), code);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();

            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to register user"));
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody VerifyEmailRequest request) {
        String storedCode = verificationCodes.get(request.getEmail());

        if (storedCode == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "No verification code found for this email"));
        }

        if (!storedCode.equals(request.getCode())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid verification code"));
        }

        Customer customer = userDetailsService.findCustomerByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("Customer not found"));

        userDetailsService.saveCustomer(customer);

        verificationCodes.remove(request.getEmail());

        return ResponseEntity.ok(Map.of("message", "Email verified successfully"));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        return ResponseEntity.ok(Map.of(
                "user", auth.getName(),
                "authorities", auth.getAuthorities()
        ));
    }

}
