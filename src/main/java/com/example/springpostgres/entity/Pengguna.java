package com.example.springpostgres.entity;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "pengguna_m")
@EntityListeners({AuditingEntityListener.class})
public class Pengguna {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pengguna_seq")
    @SequenceGenerator(name = "pengguna_seq", sequenceName = "pengguna_m_pengguna_id_seq", allocationSize = 1)
    @Column(name = "pengguna_id")
    private Integer penggunaId;

    private String email;

    private String username;

    private String password;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "pengguna")
    private List<CheckList> checkLists;

}
