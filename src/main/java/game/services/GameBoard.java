package game.services;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameBoard {

    public GameBoard() {
    }

    public String getNewBoard() {
        return "Game Board";
    }

    public String updateBoard(String game, String direction) {
        return game+" Moved Tile "+direction;
    }
}
