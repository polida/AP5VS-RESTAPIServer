package utb.fai.RESTAPIServer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@RequestMapping(value = "", produces = { "application/json" })
public class UserController {


    @Autowired
    private UserRepository userRepository;

    // Get all users
    @GetMapping("/users")
    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a single user by ID
    @GetMapping("/getUser")
    public ResponseEntity<MyUser> getUserById(@RequestParam Long id) {
        Optional<MyUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new user
    @PostMapping("/createUser")
    public ResponseEntity<MyUser> createUser(@RequestBody MyUser newUser) {
         if(userRepository.existsById(newUser.getId())  || !newUser.isUserDataValid() || !(newUser.getId() <= 0)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
         MyUser savedUser = userRepository.save(newUser);
         return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }

    // Update an existing user
    @PutMapping("/editUser")
    public ResponseEntity<MyUser> updateUser(@RequestParam Long id, @RequestBody MyUser updatedUser) {
        Optional<MyUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (!updatedUser.isUserDataValid()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            MyUser existingUser = user.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            MyUser savedUser = userRepository.save(existingUser);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a user by ID
    @DeleteMapping(value = "/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestParam Long id) {
        Optional<MyUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete all users
    @DeleteMapping(value = "/deleteAll")
    public ResponseEntity<Void> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}