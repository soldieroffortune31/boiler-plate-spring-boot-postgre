package com.example.springpostgres.entity;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "checklist_m")
@EntityListeners({AuditingEntityListener.class})
public class CheckList {

    // @GeneratedValue(strategy = GenerationType.IDENTITY)  //untuk postgre juga bisa menggunakan ini
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "checklist_seq")
    @SequenceGenerator(name = "checklist_seq", sequenceName = "checklist_m_checklist_id_seq", allocationSize = 1)
    @Column(name = "checklist_id")
    private Integer checklistId;

    @Column(name = "checklist_nama")
    private String checkListNama;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pengguna_id", referencedColumnName = "pengguna_id")
    private Pengguna pengguna;

    @OneToMany(mappedBy = "checkList")
    private List<CheckListItem> checkListItems;
}
