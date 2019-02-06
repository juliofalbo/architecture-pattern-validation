package com.juliofalbo.poc.archunit.repositories;

import com.juliofalbo.poc.archunit.entities.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends JpaRepository<App, String> {
}
