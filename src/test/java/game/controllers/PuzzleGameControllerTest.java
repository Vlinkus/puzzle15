package game.controllers;

import game.services.PuzzleGameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@WebMvcTest(PuzzleGameController.class)
class PuzzleGameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PuzzleGameService puzzleGameService;

    private Integer playerId;

    @BeforeEach
    public void  setUp(){
        playerId = 1;

    }

    @Test
    void test_endpoint_startGame() throws Exception {
        String expectedResponse = "Player 1 Game Board";
        Mockito.when(puzzleGameService.startNewGame(playerId)).thenReturn(expectedResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/puzzle15/{id}/new",playerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        verify(puzzleGameService).startNewGame(playerId);
    }

    @Test
    void test_endpoint_getGame() throws Exception {
        String expectedResponse = "Player 1 Game Board";
        puzzleGameService.addGame(playerId, expectedResponse);
        Mockito.when(puzzleGameService.getGame(playerId)).thenReturn(expectedResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/puzzle15/{id}",playerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        verify(puzzleGameService).getGame(playerId);
    }

    @Test
    void test_endpoint_playGame() throws Exception {
        String moveTile = "left";
        String expectedResponse = "Player 1 Game Board Moved Tile left";
        Mockito.when(puzzleGameService.playGame(playerId, moveTile)).thenReturn(expectedResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game/puzzle15/{playerID}?moveTile={moveTile}", playerId, moveTile))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        verify(puzzleGameService).playGame(playerId, moveTile);
    }

    @Test
    void test_endpoint_playGame_win() throws Exception {
        String moveTile = "won";
        String expectedResponse = "Congratulation You WON";
        Mockito.when(puzzleGameService.playGame(playerId, moveTile)).thenReturn(expectedResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game/puzzle15/{playerID}?moveTile={moveTile}", playerId, moveTile))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        verify(puzzleGameService).playGame(playerId, moveTile);
    }

    @Test
    void test_endpoint_deletedGame() throws Exception {
        String expectedResponse = "Game deleted";
        puzzleGameService.addGame(playerId, "Player 1 Game Board");
        Mockito.when(puzzleGameService.deleteGame(playerId)).thenReturn(expectedResponse);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/game/puzzle15/{id}",playerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        verify(puzzleGameService).deleteGame(playerId);
    }
}