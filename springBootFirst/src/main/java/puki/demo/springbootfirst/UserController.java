package puki.demo.springbootfirst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private Map<Integer, User> userDb = new HashMap<>();

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Creating user with id: {}", user.getId());

        userDb.putIfAbsent(user.getId(), user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        logger.info("Updating user with id: {}", user.getId());

        if(!userDb.containsKey(user.getId())) {
            logger.error("User not found with id: {}", user.getId());
            throw new UserNotFoundException("User not found with id: " + user.getId());
        }

        userDb.put(user.getId(), user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        logger.info("Deleting user with id: {}", id);

        if(!userDb.containsKey(id)) {
            logger.error("User not found with id: {}", id);
            throw new UserNotFoundException("User not found with id: " + id);
        }

        userDb.remove(id);
        return ResponseEntity.ok("User Deleted");
    }

    @GetMapping
    public List<User> getUsers() {
        logger.info("Fetching all users");
        return new ArrayList<>(userDb.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        logger.info("Fetching user with id: {}", id);

        if(!userDb.containsKey(id)) {
            logger.error("User not found with id: {}", id);
            throw new UserNotFoundException("User not found with id: " + id);
        }

        return ResponseEntity.ok(userDb.get(id));
    }

    @GetMapping("{userId}/orders/{orderId}")
    public ResponseEntity<User> getUserOrder(
            @PathVariable("userId") int id,
            @PathVariable int orderId) {

        logger.info("Fetching order {} for user {}", orderId, id);

        if(!userDb.containsKey(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }

        return ResponseEntity.ok(userDb.get(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam(required = false) String name) {
        logger.info("Searching users with name: {}", name);

        List<User> users = userDb.values().stream()
                .filter(u -> name == null || u.getName().equalsIgnoreCase(name))
                .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<String> getUserOrders(
            @PathVariable int id,
            @RequestParam(required = false) String status,
            @RequestHeader("Authorization") String token
    ) {
        logger.info("Fetching orders for user {}, status: {}", id, status);

        if (!userDb.containsKey(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }

        return ResponseEntity.ok("Orders fetched for user " + id);
    }
}