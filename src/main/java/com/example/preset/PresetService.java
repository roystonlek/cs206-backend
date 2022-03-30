package com.example.preset;

import java.util.List;
import com.example.user.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class PresetService {

    @Autowired
    private PresetRepository presetRepository;

    @Autowired
    private UserRepository users;

    // Get all Presets
    public List<Preset> getAllPresets(Long id) {
        return presetRepository.findByUserId(id);
    }

    // Add a preset
    public Preset createPreset(Long id , Preset preset) {
        return users.findById(id).map(user->{
            preset.setUser(user);
            return presetRepository.save(preset);
        }).orElse(null);
    }

    // Delete a preset under a user id
    public void deletePreset(Long user_id, Long preset_id) {
        users.findById(user_id).map(user->{
            presetRepository.deleteById(preset_id);
            return null;
        }).orElse(null);
    }

    // Delete all presets under a user id
    public void deleteAllPresets(Long user_id) {
        users.findById(user_id).map(user->{
            presetRepository.deleteByUserId(user_id);
            return null;
        }).orElse(null);
    }

}


