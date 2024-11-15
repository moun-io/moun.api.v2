package io.moun.api.member.controller.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class RegisterRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}