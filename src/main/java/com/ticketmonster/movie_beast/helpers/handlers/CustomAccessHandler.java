package com.ticketmonster.movie_beast.helpers.handlers;

import com.ticketmonster.movie_beast.models.User;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessHandler {

    public boolean userIsAuthorizedToViewSpecifiedContent(Integer requestedId, User user) {
        if (userIsAdmin(user)) {
            return true;
        } else {
            return user.getRole().equals("USER") && user.getUserId().equals(requestedId);
        }
    }

    public boolean userIsAdmin(User user){
        return user.getRole().equals("ADMIN");
    }
}
