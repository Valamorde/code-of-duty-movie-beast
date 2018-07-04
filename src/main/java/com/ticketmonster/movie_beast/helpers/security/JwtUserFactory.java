package com.ticketmonster.movie_beast.helpers.security;

import com.ticketmonster.movie_beast.models.User;

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
