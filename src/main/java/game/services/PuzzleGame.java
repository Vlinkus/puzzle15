package game.services;

public abstract class PuzzleGame {

    public abstract String startNewGame(Integer playerId);
    public abstract String playGame(Integer playerId, String directionToMoveTile);
    public abstract String deleteGame(Integer playerId);

}
