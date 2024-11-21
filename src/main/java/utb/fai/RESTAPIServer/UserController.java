package utb.fai.RESTAPIServer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getUser")
    public ResponseEntity<MyUser> getUserById(@RequestParam Long id) {
        Optional<MyUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<MyUser> createUser(@RequestBody MyUser newUser) {
        if (newUser.isUserDataValid()) {
            MyUser savedUser = userRepository.save(newUser);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<MyUser> updateUser(@RequestParam Long id, @RequestBody MyUser updatedUser) {
        Optional<MyUser> user = userRepository.findById(id);
        if (user.isPresent()) {
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

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestParam Long id) {
        Optional<MyUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}