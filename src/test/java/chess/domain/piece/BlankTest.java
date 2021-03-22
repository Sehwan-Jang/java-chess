package chess.domain.piece;

import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BlankTest {

    @Test
    @DisplayName("해당 위치가 비어있으면 이동 불가")
    void canMove() {
        final Piece blank = Blank.getInstance();
        assertThatThrownBy(() -> blank.movable(new Position("e", "3"), new Position("e", "4"),
                new Queen(Team.WHITE)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("비어 있는 칸입니다.");
    }
}
