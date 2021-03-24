package chess.domain.chessgame;

import chess.domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TurnTest {
    @Test
    @DisplayName("턴 변경하는 기능")
    void checkTurn() {
        Turn turn = new Turn();
        assertThat(turn.now()).isEqualTo(Team.WHITE);
        turn.next();
        assertThat(turn.now()).isEqualTo(Team.BLACK);
    }
}