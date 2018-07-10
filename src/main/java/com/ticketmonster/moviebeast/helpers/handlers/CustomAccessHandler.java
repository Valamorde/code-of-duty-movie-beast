package com.ticketmonster.moviebeast.helpers.handlers;

import com.ticketmonster.moviebeast.models.Role;
import com.ticketmonster.moviebeast.models.User;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessHandler {

    public boolean userIsAuthorizedToViewSpecifiedContent(User targetUser, User user) {
        if (userIsAdmin(user)) {
            return true;
        } else {
            return user.getRole().equals(Role.ROLE_USER.name()) && user.getUserId().equals(targetUser.getUserId());
        }
    }

    public boolean userIsAdmin(User user){
        return user.getRole().equals(Role.ROLE_ADMIN.name());
    }
}
