package game.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class GameBoardTest {

    private GameBoard gameBoard;
    private Integer[][] startingGameTiles;

    @BeforeEach
    public void  setUp(){
        gameBoard = new GameBoard();
        startingGameTiles = new Integer[][] {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
    }

    @Test
    void test_getBoardString() {
        String expectedBoardString =
                "&nbsp;**************** <br>" +
                "&nbsp;*&nbsp;&nbsp;1&nbsp;*&nbsp;&nbsp;2&nbsp;*&nbsp;&nbsp;3&nbsp;*&nbsp;&nbsp;4&nbsp;*<br>"+
                "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;5&nbsp;*&nbsp;&nbsp;6&nbsp;*&nbsp;&nbsp;7&nbsp;*&nbsp;&nbsp;8&nbsp;*<br>"+
                "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;9&nbsp;*&nbsp;10&nbsp;*&nbsp;11&nbsp;*&nbsp;12&nbsp;*<br>"+
                "&nbsp;**************** <br>&nbsp;*&nbsp;13&nbsp;*&nbsp;14&nbsp;*&nbsp;15&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*<br>"+
                "&nbsp;**************** <br>";
        String actualString = gameBoard.getBoardString(startingGameTiles);
        Assertions.assertEquals(expectedBoardString, actualString);
    }

    @Test
    void test_getNewBoard() {
        Integer[][] actualGameTiles = gameBoard.getNewBoard();
        Assertions.assertFalse(Arrays.deepEquals(startingGameTiles, actualGameTiles));
    }

    @Test
    void test_get_two_newBoard() {
        Integer[][] gameTiles1 = gameBoard.getNewBoard();
        Integer[][] gameTiles2 = gameBoard.getNewBoard();
        Assertions.assertFalse(Arrays.deepEquals(gameTiles1, gameTiles2));
    }

    @Test
    void test_updateBoard_horizontally() {
        Integer[][] expectedGameTilesAfterUpdate = new Integer[][] {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 0, 15}
        };
        int[] zeroTilePosition = {3,3};
        int[] updateTilePosition = {3,2};
        Integer[][] updatedGameTiles = gameBoard.updateBoard(startingGameTiles, zeroTilePosition, updateTilePosition);
        Assertions.assertTrue(Arrays.deepEquals(expectedGameTilesAfterUpdate, updatedGameTiles));
    }

    @Test
    void test_updateBoard_vertically() {
        Integer[][] expectedGameTilesAfterUpdate = new Integer[][] {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 0},
                {13, 14, 15, 12}
        };
        int[] zeroTilePosition = {3,3};
        int[] updateTilePosition = {2,3};
        Integer[][] updatedGameTiles = gameBoard.updateBoard(startingGameTiles, zeroTilePosition, updateTilePosition);
        Assertions.assertTrue(Arrays.deepEquals(expectedGameTilesAfterUpdate, updatedGameTiles));
    }

}