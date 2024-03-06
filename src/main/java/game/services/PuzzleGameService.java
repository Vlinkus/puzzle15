package game.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class PuzzleGameService extends PuzzleGame {
    private final GameBoard gameBoard;
    private final Map<Integer, Integer[][]> games = new HashMap<>();

    @Autowired
    public PuzzleGameService(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    @Override
    public String startNewGame(Integer playerId) {
        String newGame = "Please enter player  ID";
        if (playerId != null) {
            Integer[][] gameTiles = gameBoard.getNewBoard();
            games.put(playerId, gameTiles);
            newGame = gameBoard.getBoardString(gameTiles);
        }
        return newGame;
    }

    @Override
    public String playGame(Integer playerId, String directionToMoveTile) {
            updateTiles(playerId, directionToMoveTile);
            return checkIfPuzzleSolved(games.get(playerId)) ?
                    "Congratulation You WON" :
                    gameBoard.getBoardString(games.get(playerId));
    }

    @Override
    public String deleteGame(Integer playerId) {
        games.remove(playerId);
        return  "Game Ended";
    }

    protected Integer[][] moveTile(String directionToMoveTile, Integer[][] gameTiles) {
        Integer[][] updatedGameTiles = null;
        if(gameTiles != null) {
            int[] emptyTilePosition = findEmptyTilePosition(gameTiles);
            int[] switchingTilePosition = getSwitchingTilePosition(directionToMoveTile, emptyTilePosition);
            updatedGameTiles = gameBoard.updateBoard(gameTiles, emptyTilePosition, switchingTilePosition);
        }
        return updatedGameTiles;
    }

    protected void updateTiles(int playerId, String directionToMoveTile){
        Integer[][] updatedTiles = moveTile(directionToMoveTile, games.get(playerId));
        if(updatedTiles != null)
            games.put(playerId, updatedTiles);
    }

    protected int[] getSwitchingTilePosition(String directionToMoveTile, int[] emptyTilePosition) {
        int[] switchingTilePosition = new int[2];
        if (emptyTilePosition != null) {
            switchingTilePosition = Arrays.copyOf(emptyTilePosition, emptyTilePosition.length);
            switch (directionToMoveTile.toLowerCase()) {
                case "up":
                    if (emptyTilePosition[0] > 0) {
                        switchingTilePosition[0] = emptyTilePosition[0] - 1;
                    }
                    break;
                case "down":
                    if (emptyTilePosition[0] < 3) {
                        switchingTilePosition[0] = emptyTilePosition[0] + 1;
                    }
                    break;
                case "left":
                    if (emptyTilePosition[1] > 0) {
                        switchingTilePosition[1] = emptyTilePosition[1] - 1;
                    }
                    break;
                case "right":
                    if (emptyTilePosition[1] < 3) {
                        switchingTilePosition[1] = emptyTilePosition[1] + 1;
                    }
                    break;
            }
        }
        return switchingTilePosition;
    }

    protected int[] findEmptyTilePosition(Integer[][] gameBoard) {
        if(gameBoard != null) {
            for (int row = 0; row < gameBoard.length; row++) {
                for (int tile = 0; tile < gameBoard[row].length; tile++) {
                    if (gameBoard[row][tile] == 0) {
                        return new int[]{row, tile};
                    }
                }
            }
        }
        return null;
    }

    protected boolean checkIfPuzzleSolved(Integer[][] gameTiles) {
        Integer[][] solvedPuzzle = new Integer[][] {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12},{13, 14, 15, 0}};
        return Arrays.deepEquals(gameTiles, solvedPuzzle);
    }

    public String getLastGame(Integer playerId) {
        Integer[][] lastGame = games.get(playerId);
        return lastGame != null ? gameBoard.getBoardString(lastGame) : "Start New Game";
    }
}
