package com.example.preset;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;


@Repository
public interface PresetRepository extends JpaRepository<Preset,Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from preset where user_id = ?1", nativeQuery = true)
    void deleteByUserId(Long user_id);

    List<Preset> findByUserId(Long id);
    
}
