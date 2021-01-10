package com.it.hybrid.weatherapi.repositories;

import com.it.hybrid.weatherapi.models.BaseEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Base repository used for entities that inherits BaseEntity.
 */
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
    
}
