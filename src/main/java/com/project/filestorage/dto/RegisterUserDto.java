package com.project.filestorage.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterUserDto {
    @NotBlank @Size(min = 3, max = 20)
    private String username;
    @NotBlank @Size(min = 6, max = 50)
    private String password;
}