package game.services;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class GameBoard {

    public GameBoard() {
    }

    public String getBoardString(Integer[][] gameTiles) {
        StringBuilder boardBuilder = new StringBuilder();
        String line = "&nbsp;**************** <br>";
        for (int row = 0; row< gameTiles.length; row++) {
            boardBuilder.append(line);
            for (int tile = 0; tile < gameTiles[row].length; tile++) {
                int tileNo = gameTiles[row][tile];
                if (tileNo == 0)
                    boardBuilder.append("&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;");
                else if (tileNo < 10) {
                    boardBuilder.append("&nbsp;*&nbsp;&nbsp;").append(tileNo);
                } else boardBuilder.append("&nbsp;*&nbsp;").append(tileNo);
            }
            boardBuilder.append("&nbsp;*<br>");
        }
        boardBuilder.append(line);
        return boardBuilder.toString();
    }

    public Integer[][] getNewBoard() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0));
        Collections.shuffle(numbers);
        Integer[][] newBoard = new Integer[4][4];
        int row = 0;
        int tile = 0;
        while(!numbers.isEmpty()) {
            newBoard[row][tile] = numbers.remove(0);
            if(tile == 3) {
                row++;
                tile=0;
                continue;
            }
            tile++;
        }
        return newBoard;
    }

    public Integer[][] updateBoard(Integer[][] gameTiles, int[] emptyTilePosition, int[] switchingTilePosition) {
        gameTiles[emptyTilePosition[0]][emptyTilePosition[1]] = gameTiles[switchingTilePosition[0]][switchingTilePosition[1]];
        gameTiles[switchingTilePosition[0]][switchingTilePosition[1]] = 0;
        return gameTiles;
    }
}
