package com.example.demo.repository;

import com.example.demo.config.JpaConfig;
import com.example.demo.entity.BoardEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
public class JpaRepositoryTest {

    private final BoardRepository boardRepository;

    public JpaRepositoryTest(
            @Autowired BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void given_whenSelecting_thenWorksFine(){
        // given
        int previousCount = Long.valueOf(boardRepository.count()).intValue();

        // when
        List<BoardEntity> boards = boardRepository.findAll();

        // then
        assertThat(boards).isNotNull().hasSize(previousCount);

    }
    @DisplayName("insert 테스트")
    @Test
    void given_whenInserting_thenWorksFine(){
        // given
        long previousCount = boardRepository.count();
        BoardEntity board = BoardEntity.builder()
                .title("title")
                .content("content")
                .build();

        // when
        BoardEntity savedBoard = boardRepository.save(board);

        // then
        assertThat(boardRepository.count()).isEqualTo(previousCount+1);
    }

    @DisplayName("update 테스트")
    @Test
    void given_whenUpdating_thenWorksFine(){
        // given
        BoardEntity board = boardRepository.findById(1L).orElseThrow();
        String updatedTitle = "update title";
        board.update(updatedTitle);

        // when
        BoardEntity savedBoardEntity = boardRepository.saveAndFlush(board);

        // then
        assertThat(savedBoardEntity).hasFieldOrPropertyWithValue("title", updatedTitle);
    }

    @DisplayName("delete 테스트")
    @Test
    void given_whendeleting_thenWorksFine(){
        // given
        BoardEntity board = boardRepository.findById(1L).orElseThrow();

        // when
        boardRepository.delete(board);
        Optional<BoardEntity> board1 = boardRepository.findById(1L);

        // then
        assertThat(board1.isPresent()).isEqualTo(false);
    }

}
