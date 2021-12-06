package com.example.clip.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    @NonNull
    private String user;
    @NotBlank
    @NonNull
    private String password;
}
