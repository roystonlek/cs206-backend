package com.example.preset;

import java.util.List;
import com.example.user.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin
@RestController
public class PresetController {
    
    @Autowired
    private PresetService presetService;

    // Get all presets under a user id
    @GetMapping("/users/{id}/presets")
    public List<Preset> getAllPresets(@PathVariable Long id){
        List<Preset> ret = presetService.getAllPresets(id);
        return ret;
    }

    // Create a preset under a user id
    @PostMapping("/users/{id}/presets")
    public Preset createPreset(@PathVariable Long id , @RequestBody Preset preset) {
        Preset ret = presetService.createPreset(id, preset);
        if(ret == null){
            throw new UserNotFoundException(id);
        }
        return ret;
    }

    
    // Delete a preset under a user id
    @DeleteMapping("/users/{user_id}/presets/{preset_id}")
    public void deletePreset(@PathVariable Long user_id, @PathVariable Long preset_id) {
        presetService.deletePreset(user_id, preset_id);
    }

    // Delete all presets under a user id
    @DeleteMapping("/users/{user_id}/presets") 
    public void deleteAllPresets(@PathVariable Long user_id) {
        presetService.deleteAllPresets(user_id);
    }
}
