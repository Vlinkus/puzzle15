package game.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameBoardTest {

    private GameBoard gameBoard;

    @BeforeEach
    public void  setUp(){
        gameBoard = new GameBoard();
    }

    @Test
    void test_getNewBoard() {
        String actualResult = gameBoard.getNewBoard();
        Assertions.assertEquals("Game Board", actualResult);
    }

    @Test
    void test_updateBoard() {
        String game = "Player 1 Game Board";
        String direction = "up";
       String actualUpdateResult = gameBoard.updateBoard(game, direction);
        Assertions.assertEquals(game+" Moved Tile "+direction, actualUpdateResult);
    }

}