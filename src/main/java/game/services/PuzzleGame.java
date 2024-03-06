package game.services;

public interface PuzzleGame {

    String startNewGame(Integer playerId);
    String playGame(Integer playerId, String directionToMoveTile);
    String deleteGame(Integer playerId);

}
