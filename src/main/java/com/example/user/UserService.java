package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.*;
import com.example.preset.*;

@Service
public class UserService {
    @Autowired
    private UserRepository users;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired 
    private PresetRepository presets;

    /**
     * Get a list of all users (ADMIN rights)
     * 
     * @return a List<User> of users
     */
    public List<User> getUsers() {
        return users.findAll();
    }

    /**
     * added extra logic to check if username already exists
     * Takes in a user and check if the username already exists if exist return null
     * else save the user with encoded password and return
     * 
     * @param user
     * @return the added user / null
     */



    public User addUser(User user) {
        User ret = null;
        User sameuser = users.findByUsername(user.getUsername()).orElse(null);
        if (sameuser == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            ret = users.save(user);
        } else {
            ret = null;
        }
        System.out.println("hi im getting here");
        presets.save(new Preset("Suggested - Weight Loss", user,"Calories-Low", "Sugar-Low","Fat-Low"));
        presets.save( new Preset("Suggested - Bulking ", user, "Calories-High", "Protein-High","Fat-Low"));
        presets.save( new Preset("Suggested - Lean", user, "Protein-High", "Sugar-Low","Fat-Low"));
        return ret;
    }

    /**
     * This is a special post method for axios to call for the user The body will
     * consist of the username and password check if username and password matches
     * an existing user then return
     * 
     * @param user
     * @return user found / null
     */
    public User getUser(User user) {
        return users.findByUsername(user.getUsername()).map(user2 -> {
            if (encoder.matches(user.getPassword(), user2.getPassword())) {
                return user2;
            }
            return null;
        }).orElse(null);
    }


    /**
     * added extra method to change password specifically
     * @param userId
     * @param newUser
     * @return updated user/null
     */
    public User updateUserPassword(Long userId, User newUser) {
        return users.findById(userId).map(user -> {
            user.setPassword(encoder.encode(newUser.getPassword()));
            return users.save(user);
        }).orElse(null);
    }
    
    /**
     * deleteUser with id
     * @param userId
     */
    public void deleteUser(Long userId) {
        users.deleteById(userId);
    }
}
