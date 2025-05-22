package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.Customer;
import com.astafievvadim.mm_backend.payload.CustomerSignUpRequest;
import com.astafievvadim.mm_backend.payload.VerifyEmailRequest;
import com.astafievvadim.mm_backend.security.CustomUserDetailsService;
import com.astafievvadim.mm_backend.security.CustomUserDetails;
import com.astafievvadim.mm_backend.service.EmailService;
import jakarta.mail.MessagingException;
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
            CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

            String code = String.format("%06d", random.nextInt(1_000_000));
            verificationCodes.put(email, code);

            System.out.println("Sending verification code to " + email + ": " + code);

            emailService.sendVerificationCode(email, code);

            return ResponseEntity.ok(Map.of("message", "Verification code sent to email"));
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

            System.out.println("Sending verification code to " + request.getEmail() + ": " + code);
            emailService.sendVerificationCode(request.getEmail(), code);

            return ResponseEntity.ok(Map.of("message", "Customer registered successfully. Verification code sent to email."));
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to send verification email"));
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
