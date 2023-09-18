package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "board")
public class BoardCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @MapsId
    @OneToOne(optional = false)
    private BoardEntity board;

    @Basic
    @Column(name = "hit", nullable = false)
    private Integer hit = 0;

    @Builder
    public BoardCount(BoardEntity board, Integer hit) {
        this.board = board;
        this.hit = hit;
    }
}
