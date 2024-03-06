package game.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PuzzleGameServiceTest {

    @Mock
    GameBoard gameBoard;

    @InjectMocks
    private PuzzleGameService puzzleGameService;

    private Integer playerId;

    @BeforeEach
    public void setUp(){
        playerId = 1;
    }

    @Test
    void startNewGame() {
        String expectedResult = "Player 1 Game Board";
        when(gameBoard.getNewBoard()).thenReturn("Game Board");
        String actualResult = puzzleGameService.startNewGame(playerId);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void test_playGame_Win() {
        String direction = "won";
        String game = "Game 1";
        puzzleGameService.addGame(playerId, game);
        when(gameBoard.updateBoard(game, direction)).thenReturn(game+" Moved Tile "+direction);
        String actualResult = puzzleGameService.playGame(playerId, direction);
        assertEquals("Congratulation You WON", actualResult);
    }

    @Test
    void test_playGame_NotWin() {
        String direction = "left";
        String game = "Game 1";
        puzzleGameService.addGame(playerId, game);
        when(gameBoard.updateBoard(game, direction)).thenReturn(game+" Moved Tile "+direction);
        String actualResult = puzzleGameService.playGame(playerId, direction);
        assertEquals("Game 1 Moved Tile left", actualResult);

    }

    @Test
    void test_deleteGame() {
        puzzleGameService.addGame(playerId, "Game1");
        String gamePreDelete = puzzleGameService.getGame(playerId);
        String deleteResult = puzzleGameService.deleteGame(playerId);
        String gamePostDelete = puzzleGameService.getGame(playerId);
        assertEquals("Game deleted", deleteResult);
        assertNotNull(gamePreDelete);
        assertNull(gamePostDelete);
    }

    @Test
    void test_getGame(){
        puzzleGameService.addGame(playerId, "Game 1");
        String game = puzzleGameService.getGame(playerId);
        assertNotNull(game);
        assertEquals("Game 1", game);
    }

    @Test
    void moveTile() {
        String direction = "left";
        String expectedGameResult = "Game 1 Moved Tile "+direction;
        String game = "Game 1";
        puzzleGameService.addGame(playerId, game);
        when(gameBoard.updateBoard(game, direction)).thenReturn(game+" Moved Tile "+direction);
        String gameBeforeUpdate = puzzleGameService.getGame(playerId);
        String actualUpdateResult =  puzzleGameService.moveTile(playerId, direction);
        String gameAfterUpdate = puzzleGameService.getGame(playerId);
        assertEquals(expectedGameResult, actualUpdateResult);
        assertNotEquals(gameBeforeUpdate, gameAfterUpdate);
    }

    @Test
    void checkIfPuzzleSolved() {
        String winGame = "Player 1 game Successfully Completed won";
        String gameNotFinished = "Player 1 game Successfully Completed";
        boolean actualGameResult = puzzleGameService.checkIfPuzzleSolved(winGame);
        boolean notFinishedGameResult = puzzleGameService.checkIfPuzzleSolved(gameNotFinished);
        assertTrue(actualGameResult);
        assertFalse(notFinishedGameResult);

    }
}