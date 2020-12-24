package com.ticketmonster.moviebeast.util.security;

import com.ticketmonster.moviebeast.model.User;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getFullName(),
                user.getPassword(),
                user.getEmail(),
                user.getAuthorities(),
                user.isEnabled(),
                user.getLastPasswordResetDate()
        );
    }
}
