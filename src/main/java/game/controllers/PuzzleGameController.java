package game.controllers;

import game.services.PuzzleGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/game/puzzle15")
@RestController
public class PuzzleGameController {

    @Autowired
    PuzzleGameService gameService;

    @GetMapping("/{playerId}/new")
    public String startGame(@PathVariable Integer playerId){
        return gameService.startNewGame(playerId);
    }

    @GetMapping("/{playerId}")
    public String getGame(@PathVariable Integer playerId){
        return gameService.getGame(playerId);
    }

    @PostMapping("/{playerID}")
    public String playGame(@PathVariable Integer playerID, @RequestParam String moveTile){
        return gameService.playGame(playerID, moveTile);
    }

    @DeleteMapping("/{playerId}")
    public String deletedGame(@PathVariable Integer playerId){
        return gameService.deleteGame(playerId);
    }
}
