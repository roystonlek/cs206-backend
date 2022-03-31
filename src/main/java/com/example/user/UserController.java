package com.example.user;

import java.util.List;

import javax.validation.Valid;

import com.example.preset.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private UserRepository users;
    private BCryptPasswordEncoder encoder;
    @Autowired 
    private PresetRepository presets;

    public UserController(UserRepository users, BCryptPasswordEncoder encoder){
        this.users = users;
        this.encoder = encoder;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/users")
    public List<User> getUsers() {
        return users.findAll();
    }

    /**
    * Using BCrypt encoder to encrypt the password for storage 
    * @param user
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user){
        // your code here
        User ret = null;
        User sameuser = users.findByUsername(user.getUsername()).orElse(null);
        if (sameuser == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            ret = users.save(user);
        } else {
            ret = null;
        }
        presets.save(new Preset( "Suggested - Weight Loss", user,"Calories-Low", "Sugar-Low","Fat-Low"));
        presets.save( new Preset("Suggested - Bulking ", user, "Calories-High", "Protein-High","Fat-Low"));
        presets.save( new Preset("Suggested - Lean", user, "Protein-High", "Sugar-Low","Fat-Low"));
        return ret;
        // user.setPassword(encoder.encode(user.getPassword()));
        // return users.save(user);
    }

    /**
     * Login Method for axios to call for frontend login
     * @param user
     * @return User or UserNotFoundException 404
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/users/search")
    public User loginUser(@Valid @RequestBody User user) {
        return users.findByUsername(user.getUsername()).map(user2 -> {
            if (encoder.matches(user.getPassword(), user2.getPassword())) {
                return user2;
            }
            return null;
        }).orElseThrow(() -> new UserNotFoundException(user.getId()));
    }


   
}