package com.ticketmonster.moviebeast.util.handlers;

import com.ticketmonster.moviebeast.model.Role;
import com.ticketmonster.moviebeast.model.User;
import org.springframework.stereotype.Component;

/**
 * This handler is responsible for access. Triggers a check to see if the user is an admin or not, and if not, it returns the user details.
 *
 * @author nancyatnic
 */
@Component
public class CustomAccessHandler {

    public boolean userIsAdmin(User user) {
        return user.getRole().equals(Role.ADMIN.name());
    }
}
