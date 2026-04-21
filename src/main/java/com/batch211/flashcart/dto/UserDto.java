package com.batch211.flashcart.dto;

import com.batch211.flashcart.entities.User;

public record UserDto(Long id, String fname, String lname, String email, String role) {
    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getFname(), user.getLname(), user.getEmail(), user.getRole());
    }
}
