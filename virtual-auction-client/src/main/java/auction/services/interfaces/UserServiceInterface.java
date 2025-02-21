package auction.services.interfaces;

import auction.models.User;
import java.util.Optional;
import java.util.UUID;

public interface UserServiceInterface {
    boolean signIn(Optional<UUID> id, String name, String password);
    boolean insert(User newUser);
}