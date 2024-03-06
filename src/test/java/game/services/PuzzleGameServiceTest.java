package game.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PuzzleGameServiceTest {

    @Mock
    GameBoard mockGameBoard;

    @InjectMocks
    private PuzzleGameService puzzleGameService;

    private Integer[][] startingGameTiles;
    private Integer playerId;
    private GameBoard gameBoard;
    private Map<Integer, Integer[][]> games;

    @BeforeEach
    public void setUp(){
        games = new HashMap<>();
        gameBoard = new GameBoard();
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
        verify(mockGameBoard, never()).getNewBoard();
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
        when(mockGameBoard.getNewBoard()).thenReturn(startingGameTiles);
        when(mockGameBoard.getBoardString(startingGameTiles)).thenReturn(expectedBoardString);
        String actualBoardString = puzzleGameService.startNewGame(playerId);

        Assertions.assertEquals(expectedBoardString, actualBoardString);
        verify(mockGameBoard).getNewBoard();
        verify(mockGameBoard).getBoardString(startingGameTiles);
    }


    @Test
    void test_endGame() {
        Integer nonExistingId = 2;
        String actualBoard = puzzleGameService.deleteGame(nonExistingId);
        Assertions.assertEquals("Game Ended", actualBoard);
    }

    @Test
    void test_playGame_toMoveTileLeft() {
        int[] startingTile = new int[]{3, 3};
        int[] targetTile = new int[]{3, 2};
        Integer[][] gameTiles = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 14, 0, 15}};
        Integer[][] updatedGameTiles = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 0, 14, 15}};
        String expectedBoardString =
                "&nbsp;**************** <br>" +
                "&nbsp;*&nbsp;&nbsp;1&nbsp;*&nbsp;&nbsp;2&nbsp;*&nbsp;&nbsp;3&nbsp;*&nbsp;&nbsp;4&nbsp;*<br>"+
                "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;5&nbsp;*&nbsp;&nbsp;6&nbsp;*&nbsp;&nbsp;7&nbsp;*&nbsp;&nbsp;8&nbsp;*<br>"+
                "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;9&nbsp;*&nbsp;10&nbsp;*&nbsp;11&nbsp;*&nbsp;12&nbsp;*<br>"+
                "&nbsp;**************** <br>&nbsp;*&nbsp;13&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;*&nbsp;14&nbsp;*&nbsp;15&nbsp;*<br>"+
                "&nbsp;**************** <br>";
        when(mockGameBoard.getNewBoard()).thenReturn(gameTiles);
        when(mockGameBoard.updateBoard(startingGameTiles, startingTile,targetTile)).thenReturn(updatedGameTiles);
        when(mockGameBoard.getBoardString(updatedGameTiles)).thenReturn(expectedBoardString);
        String actualString  = puzzleGameService.playGame(playerId, "left");
        Assertions.assertEquals(expectedBoardString, actualString);
    }

    @Test
    void test_playGame_toMoveTile() {
        int[] startingTile = new int[]{3, 3};
        int[] targetTile = new int[]{3, 2};
        Integer[][] gameTiles = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 14, 0, 15}};
        Integer[][] updatedGameTiles = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 0, 12},{13, 14, 11, 15}};
        String expectedBoardString =
                "&nbsp;**************** <br>" +
                        "&nbsp;*&nbsp;&nbsp;1&nbsp;*&nbsp;&nbsp;2&nbsp;*&nbsp;&nbsp;3&nbsp;*&nbsp;&nbsp;4&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;5&nbsp;*&nbsp;&nbsp;6&nbsp;*&nbsp;&nbsp;7&nbsp;*&nbsp;&nbsp;8&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;9&nbsp;*&nbsp;10&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;*&nbsp;12&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;13&nbsp;*&nbsp;14&nbsp;*&nbsp;11&nbsp;*&nbsp;15&nbsp;*<br>"+
                        "&nbsp;**************** <br>";
        when(mockGameBoard.getNewBoard()).thenReturn(gameTiles);
        when(mockGameBoard.updateBoard(startingGameTiles, startingTile,targetTile)).thenReturn(updatedGameTiles);
        when(mockGameBoard.getBoardString(updatedGameTiles)).thenReturn(expectedBoardString);
        String actualString  = puzzleGameService.playGame(playerId, "up");
        Assertions.assertEquals(expectedBoardString, actualString);
    }

    @Test
    void test_moveTile(){
        String direction = "left";
        int[] emptyTilePosition = new int[]{3,3};
        int[] switchingTilePosition = new int[]{3,2};
        Integer[][]expectedGameTiles = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 14, 0, 15}};
        when(gameBoard.updateBoard(any(), any(), any())).thenReturn(new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 14, 0, 15}});
        Integer[][] actualGameTile = puzzleGameService.moveTile(direction, startingGameTiles);
        assertEquals(actualGameTile, expectedGameTiles);
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
    void test_checkIfPuzzleSolved() {
        assertTrue(puzzleGameService.checkIfPuzzleSolved(startingGameTiles));
    }

    @Test
    void test_checkIfPuzzleSolved_ifPuzzleNotSolved() {
        Integer[][] notCompletedTiles = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 14, 0, 15}};
        assertFalse(puzzleGameService.checkIfPuzzleSolved(notCompletedTiles));
    }

}