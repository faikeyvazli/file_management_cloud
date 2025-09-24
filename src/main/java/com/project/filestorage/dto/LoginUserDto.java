package com.project.filestorage.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginUserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}