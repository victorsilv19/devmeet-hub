package com.devmeethub.repository;

import com.devmeethub.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface Spring Data JPA. Magicamente fornece CRUD completo 
 * sem necessitar de boilerplate ou SQL manual.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
