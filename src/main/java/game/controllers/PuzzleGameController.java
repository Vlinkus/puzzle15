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
    public ResponseEntity<String> startGame(@PathVariable Integer playerId){
        String newGame = gameService.startNewGame(playerId);
        return ResponseEntity.ok().body(newGame);
    }

    @GetMapping("/{playerID}/{moveTile}")
    public ResponseEntity<String> playGame(@PathVariable Integer playerID, @PathVariable String moveTile){
        String gameUpdated = gameService.playGame(playerID, moveTile);
        return ResponseEntity.ok().body(gameUpdated);
    }
    @DeleteMapping("/{playerId}")
    public ResponseEntity<String> deletedGame(@PathVariable Integer playerId){
        String lastGame = gameService.deleteGame(playerId);
        return ResponseEntity.ok().body(lastGame);
    }
}
