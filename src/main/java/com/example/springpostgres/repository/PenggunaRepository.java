package com.example.springpostgres.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springpostgres.entity.Pengguna;

@Repository
public interface PenggunaRepository extends JpaRepository<Pengguna, Integer> {

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<Pengguna> findFirstByUsername(String username);

}
