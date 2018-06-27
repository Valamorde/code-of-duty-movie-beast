package com.ticketmonster.movie_beast.helpers.config;

import com.ticketmonster.movie_beast.models.User;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessHandler {

    public boolean isUserAuthorizedToViewSpecifiedId(Integer requestedId, User user) {
        if (user.getRole().equals("ADMIN")) {
            return true;
        } else if (user.getRole().equals("USER") && user.getUserId().equals(requestedId)) {
            return true;
        } else {
            return false;
        }
    }
}
