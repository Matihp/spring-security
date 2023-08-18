package com.security.demo.repositories;

import com.security.demo.models.Tv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITvRepository extends JpaRepository<Tv,Long> {
}
