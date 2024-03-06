package game.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class PuzzleGameService implements PuzzleGame {
    private final GameBoard gameBoard;
    private final Map<Integer, String> games = new HashMap<>();

    @Autowired
    public PuzzleGameService(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }


    public String startNewGame(Integer playerId) {
        String game = gameBoard.getNewBoard();
        games.put(playerId, game);
        return "Player "+playerId+" "+game;
    }

    public String playGame(Integer playerId, String directionToMoveTile) {
            String updatedGame = moveTile(playerId, directionToMoveTile);
            return checkIfPuzzleSolved(updatedGame) ?
                    "Congratulation You WON" :
                    updatedGame;
    }

    public String deleteGame(Integer playerId) {
        games.remove(playerId);
        return  "Game deleted";
    }

    protected String moveTile(Integer playerId, String directionToMoveTile) {
        String game = getGame(playerId);
        String updatedGame = gameBoard.updateBoard(game, directionToMoveTile);
        games.put(playerId, updatedGame);
        return updatedGame;
    }

    public String getGame(Integer playerId) {
        return games.get(playerId);
    }

    protected boolean checkIfPuzzleSolved(String game) {
       return game.contains("won");
    }

    public void addGame(Integer playerId, String game) {
        games.put(playerId,game);
    }
}
