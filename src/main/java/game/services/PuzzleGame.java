package game.services;

public interface PuzzleGame {

    public abstract String startNewGame(Integer playerId);
    public abstract String playGame(Integer playerId, String directionToMoveTile);
    public abstract String deleteGame(Integer playerId);

}
