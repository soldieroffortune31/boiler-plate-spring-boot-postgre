package com.example.springpostgres.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springpostgres.entity.CheckList;
import com.example.springpostgres.entity.CheckListItem;

@Repository
public interface CheckListItemRepository extends JpaRepository<CheckListItem, Integer> {

    List<CheckListItem> findAllByCheckList(CheckList checkList);

    Optional<CheckListItem> findFirstByCheckListAndCheckListItemId(CheckList checkList, Integer checkListItemId);

    @Query("SELECT ci from CheckListItem ci JOIN FETCH ci.checkList c JOIN FETCH c.pengguna p WHERE ci.id = :checkListItemId AND c.id= :checkListId AND p.id = :penggunaId")
    Optional<CheckListItem> findCheckListItem(
        @Param("checkListItemId") Integer checkListItemId,
        @Param("checkListId") Integer checkListId,
        @Param("penggunaId") Integer penggunaId
    );

}
