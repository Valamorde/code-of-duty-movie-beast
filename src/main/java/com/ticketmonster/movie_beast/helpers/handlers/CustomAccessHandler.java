package com.ticketmonster.movie_beast.helpers.handlers;

import com.ticketmonster.movie_beast.models.Role;
import com.ticketmonster.movie_beast.models.User;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessHandler {

    public boolean userIsAuthorizedToViewSpecifiedContent(User targetUser, User user) {
        if (userIsAdmin(user)) {
            return true;
        } else {
            return user.getRole().equals(Role.ROLE_USER) && user.getUserId().equals(targetUser.getUserId());
        }
    }

    public boolean userIsAdmin(User user){
        return user.getRole().equals(Role.ROLE_ADMIN);
    }
}
