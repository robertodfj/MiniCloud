package com.minicloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minicloud.model.ROLE;

public interface  ROLErepository  extends JpaRepository<ROLE, Long> {
    
    ROLE findByName(String name);

    ROLE findById(long id);

}
