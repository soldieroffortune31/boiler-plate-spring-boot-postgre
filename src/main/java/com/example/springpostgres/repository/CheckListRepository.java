package com.example.springpostgres.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springpostgres.entity.CheckList;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Integer> {

    Page<CheckList> findAllByPengguna_PenggunaId(Integer penggunaId, Pageable pageable);

    @EntityGraph(attributePaths = "pengguna", type = EntityGraphType.FETCH)
    Optional<CheckList> findFirstByChecklistIdAndPengguna_PenggunaId(Integer checklistId, Integer penggunaId);

}
