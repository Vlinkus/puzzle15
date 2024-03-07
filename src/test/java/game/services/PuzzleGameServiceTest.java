package game.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PuzzleGameServiceTest {

    @Mock
    GameBoard gameBoard;

    @InjectMocks
    private PuzzleGameService puzzleGameService;

    private Integer[][] startingGameTiles;
    private Integer playerId;
    private Map<Integer, Integer[][]> games;

    @BeforeEach
    public void setUp(){
        games = new HashMap<>();
        playerId = 1;
        startingGameTiles = new Integer[][] {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        games.put(playerId, startingGameTiles);
    }

    @Test
    public void test_returnsTrueIfPuzzleGameServiceNotNull(){
        Assertions.assertNotNull(puzzleGameService);
    }

    @Test
    void test_startNewGame_ifPlayerID_isNull() {
        String expected = "Please enter player  ID";
        String actual = puzzleGameService.startNewGame(null);
        Assertions.assertEquals(expected, actual);
        verify(gameBoard, never()).getNewBoard();
    }

    @Test
    void test_startNewGame() {
        String expectedBoardString =
                "&nbsp;**************** <br>" +
                        "&nbsp;*&nbsp;&nbsp;1&nbsp;*&nbsp;&nbsp;2&nbsp;*&nbsp;&nbsp;3&nbsp;*&nbsp;&nbsp;4&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;5&nbsp;*&nbsp;&nbsp;6&nbsp;*&nbsp;&nbsp;7&nbsp;*&nbsp;&nbsp;8&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;9&nbsp;*&nbsp;10&nbsp;*&nbsp;11&nbsp;*&nbsp;12&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;13&nbsp;*&nbsp;14&nbsp;*&nbsp;15&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*<br>"+
                        "&nbsp;**************** <br>";
        when(gameBoard.getNewBoard()).thenReturn(startingGameTiles);
        when(gameBoard.getBoardString(startingGameTiles)).thenReturn(expectedBoardString);
        String actualBoardString = puzzleGameService.startNewGame(playerId);

        Assertions.assertEquals(expectedBoardString, actualBoardString);
        verify(gameBoard).getNewBoard();
        verify(gameBoard).getBoardString(startingGameTiles);
    }


    @Test
    void test_endGame() {
        Integer nonExistingId = 2;
        String actualBoard = puzzleGameService.deleteGame(nonExistingId);
        Assertions.assertEquals("Game Ended", actualBoard);
    }

    @Test
    void test_playGame_toMoveTile() {
        int[] emptyTilePosition = new int[] {3,2};
        int[] switchingTilePosition = new int[] {2,2};
        Integer[][] gameTiles = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 14, 0, 15}};
        Integer[][] updatedGameTiles = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 0, 12},{13, 14, 11, 15}};
        String expectedBoardString =
                "&nbsp;**************** <br>" +
                        "&nbsp;*&nbsp;&nbsp;1&nbsp;*&nbsp;&nbsp;2&nbsp;*&nbsp;&nbsp;3&nbsp;*&nbsp;&nbsp;4&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;5&nbsp;*&nbsp;&nbsp;6&nbsp;*&nbsp;&nbsp;7&nbsp;*&nbsp;&nbsp;8&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;9&nbsp;*&nbsp;10&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;*&nbsp;12&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;13&nbsp;*&nbsp;14&nbsp;*&nbsp;11&nbsp;*&nbsp;15&nbsp;*<br>"+
                        "&nbsp;**************** <br>";

        puzzleGameService.addGame(playerId, gameTiles);
        when(gameBoard.updateBoard(gameTiles, emptyTilePosition,switchingTilePosition)).thenReturn(updatedGameTiles);
        when(gameBoard.getBoardString(updatedGameTiles)).thenReturn(expectedBoardString);
        String actualString  = puzzleGameService.playGame(playerId, "up");
        Assertions.assertEquals(expectedBoardString, actualString);
    }

    @Test
    void test_playGame_win() {
        int[] emptyTilePosition = new int[] {3,2};
        int[] switchingTilePosition = new int[] {3,3};
        Integer[][] gameTiles = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 14, 0, 15}};
        Integer[][] updatedGameTiles = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 14, 15, 0}};
        String expectedString = "Congratulation You WON";

        puzzleGameService.addGame(playerId, gameTiles);
        when(gameBoard.updateBoard(gameTiles, emptyTilePosition,switchingTilePosition)).thenReturn(updatedGameTiles);
        String actualString  = puzzleGameService.playGame(playerId, "right");
        verify(gameBoard).updateBoard(gameTiles, emptyTilePosition,switchingTilePosition);
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    void test_moveTile(){
        String direction = "left";
        puzzleGameService.addGame(playerId, startingGameTiles);
        int[] emptyTilePosition = new int[] {3,3};
        int[] switchingTilePosition = new int[] {3,2};
        Integer[][]expectedGameTiles = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 14, 0, 15}};
        when(gameBoard.updateBoard(startingGameTiles, emptyTilePosition, switchingTilePosition)).thenReturn(expectedGameTiles);
        Integer[][] actualGameTile = puzzleGameService.moveTile(playerId, direction);
        assertTrue(Arrays.deepEquals(expectedGameTiles, actualGameTile));
    }

    @Test
    void test_moveTile_IfGameIsNull(){
        String direction = "up";
        Integer[][] actualGameTile = puzzleGameService.moveTile(playerId, direction);
        assertNull(actualGameTile);
    }

    @Test
    void test_getSwitchingTilePosition_up() {
        int[] emptyTilePosition = new int[]{2,2};
        int[] expectedTilePosition = new int[]{1,2};
        int[] actualTilePosition = puzzleGameService.getSwitchingTilePosition("up", emptyTilePosition);
        assertArrayEquals(expectedTilePosition, actualTilePosition);
    }
    @Test
    void test_getSwitchingTilePosition_down() {
        int[] emptyTilePosition = new int[]{2,2};
        int[] expectedTilePosition = new int[]{3,2};
        int[] actualTilePosition = puzzleGameService.getSwitchingTilePosition("down", emptyTilePosition);
        assertArrayEquals(expectedTilePosition, actualTilePosition);
    }
    @Test
    void test_getSwitchingTilePosition_left() {
        int[] emptyTilePosition = new int[]{2,2};
        int[] expectedTilePosition = new int[]{2,1};
        int[] actualTilePosition = puzzleGameService.getSwitchingTilePosition("left", emptyTilePosition);
        assertArrayEquals(expectedTilePosition, actualTilePosition);
    }
    @Test
    void test_getSwitchingTilePosition_right() {
        int[] emptyTilePosition = new int[]{2,2};
        int[] expectedTilePosition = new int[]{2,3};
        int[] actualTilePosition = puzzleGameService.getSwitchingTilePosition("right", emptyTilePosition);
        assertArrayEquals(expectedTilePosition, actualTilePosition);
    }

    @Test
    void test_findEmptyTilePosition() {
        int[] expectedTilePosition = new int[]{3,3};
        int[] actualTilePosition = puzzleGameService.findEmptyTilePosition(startingGameTiles);
        assertArrayEquals(expectedTilePosition, actualTilePosition);
    }

    @Test
    void test_findEmptyTilePosition_ifBoardTileIsNull() {
        int[] actualTilePosition = puzzleGameService.findEmptyTilePosition(null);
        assertNull(actualTilePosition);
    }

    @Test
    void test_checkIfPuzzleSolved() {
        assertTrue(puzzleGameService.checkIfPuzzleSolved(startingGameTiles));
    }

    @Test
    void test_checkIfPuzzleSolved_ifPuzzleNotSolved() {
        Integer[][] notSolvedTiles = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 14, 0, 15}};
        assertFalse(puzzleGameService.checkIfPuzzleSolved(notSolvedTiles));
    }

    @Test
    void test_getGame(){
        puzzleGameService.addGame(playerId, startingGameTiles);
        Integer[][] actualGameBoard = puzzleGameService.getGame(playerId);
        assertNotNull(actualGameBoard);
        assertTrue(Arrays.deepEquals(startingGameTiles, actualGameBoard));
    }

    @Test
    void test_getGame_String(){
        String expectedBoardString =
                "&nbsp;**************** <br>" +
                        "&nbsp;*&nbsp;&nbsp;1&nbsp;*&nbsp;&nbsp;2&nbsp;*&nbsp;&nbsp;3&nbsp;*&nbsp;&nbsp;4&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;5&nbsp;*&nbsp;&nbsp;6&nbsp;*&nbsp;&nbsp;7&nbsp;*&nbsp;&nbsp;8&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;9&nbsp;*&nbsp;10&nbsp;*&nbsp;11&nbsp;*&nbsp;12&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;13&nbsp;*&nbsp;14&nbsp;*&nbsp;15&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*<br>"+
                        "&nbsp;**************** <br>";
        puzzleGameService.addGame(playerId, startingGameTiles);
        when(gameBoard.getBoardString(startingGameTiles)).thenReturn(expectedBoardString);
        String actualGameBoard = puzzleGameService.getGameString(playerId);
        assertNotEquals("Start New Game", actualGameBoard);
        assertEquals(expectedBoardString, actualGameBoard);
    }

    @Test
    void test_getGame_String_WhenGameDoesNotExist(){
        String actualGameBoard = puzzleGameService.getGameString(playerId);
        assertEquals("Start New Game", actualGameBoard);
    }

}