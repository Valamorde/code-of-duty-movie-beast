package com.ticketmonster.moviebeast.helpers.security;

import com.ticketmonster.moviebeast.models.User;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getUserId(),
                user.getFullName(),
                user.getPassword(),
                user.getEmail(),
                user.getAuthorities(),
                user.isEnabled(),
                user.getLastPasswordResetDate()
        );
    }
}
