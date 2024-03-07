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
import static org.mockito.Mockito.when;

@WebMvcTest(PuzzleGameController.class)
class PuzzleGameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PuzzleGameService puzzleGameService;

    private Integer playerId;
    private Integer[][] startingGameTiles;
    private String startingBoardString;

    @BeforeEach
    public void  setUp(){
        playerId = 1;
        startingGameTiles = new Integer[][] {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        startingBoardString =
                "&nbsp;**************** <br>" +
                        "&nbsp;*&nbsp;&nbsp;1&nbsp;*&nbsp;&nbsp;2&nbsp;*&nbsp;&nbsp;3&nbsp;*&nbsp;&nbsp;4&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;5&nbsp;*&nbsp;&nbsp;6&nbsp;*&nbsp;&nbsp;7&nbsp;*&nbsp;&nbsp;8&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;9&nbsp;*&nbsp;10&nbsp;*&nbsp;11&nbsp;*&nbsp;12&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;13&nbsp;*&nbsp;14&nbsp;*&nbsp;15&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*<br>"+
                        "&nbsp;**************** <br>";
    }

    @Test
    void test_endpoint_startGame() throws Exception {
        when(puzzleGameService.startNewGame(playerId)).thenReturn(startingBoardString);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/puzzle15/{id}/new",playerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(startingBoardString));
        verify(puzzleGameService).startNewGame(playerId);
    }

    @Test
    void test_endpoint_getGameString() throws Exception {
        when(puzzleGameService.getGameString(playerId)).thenReturn(startingBoardString);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/puzzle15/{id}",playerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(startingBoardString));
        verify(puzzleGameService).getGameString(playerId);
    }

    @Test
    void test_endpoint_playGame() throws Exception {
        String moveTile = "left";
        String expectedResponse =
                "&nbsp;**************** <br>" +
                        "&nbsp;*&nbsp;&nbsp;1&nbsp;*&nbsp;&nbsp;2&nbsp;*&nbsp;&nbsp;3&nbsp;*&nbsp;&nbsp;4&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;5&nbsp;*&nbsp;&nbsp;6&nbsp;*&nbsp;&nbsp;7&nbsp;*&nbsp;&nbsp;8&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;&nbsp;9&nbsp;*&nbsp;10&nbsp;*&nbsp;11&nbsp;*&nbsp;12&nbsp;*<br>"+
                        "&nbsp;**************** <br>&nbsp;*&nbsp;13&nbsp;*&nbsp;14&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*&nbsp;15&nbsp;*<br>"+
                        "&nbsp;**************** <br>";
        when(puzzleGameService.playGame(playerId, moveTile)).thenReturn(expectedResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/puzzle15/{playerID}/{moveTile}", playerId, moveTile))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        verify(puzzleGameService).playGame(playerId, moveTile);
    }

    @Test
    void test_endpoint_deletedGame() throws Exception {
        String expectedResponse = "Game Ended";
        when(puzzleGameService.deleteGame(playerId)).thenReturn(expectedResponse);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/game/puzzle15/{playerID}", playerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        verify(puzzleGameService).deleteGame(playerId);
    }
}