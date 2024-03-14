##Puzzle 15 game TASK implementation
### Task Requirements
Task needed only back end with REST end-points. 
- Application state in memory only, no dataBase allowed.
- Multiple game instances can be played at same time.
- No need to create logic for Tiles moving
- No Front-end needed
- Application covered by tests

  <p>
  Repository contains two branches **MAIN** and **PUZZLE**. Puzzle branch can be played in browser (no Front-end created) endPoints return only String.
</p>
### Endpoints For Puzzle Branch
- {playerID} is any positive number
- {DirectionToMoveTile} direction up, down, left, right
- Start new Game
  - http://localhost:8080/api/v1/game/puzzle15/{playerID}/new 
- Get Current Game Status
  - http://localhost:8080/api/v1/game/puzzle15/{playerID}
- Move Empty Tile to Direction
  - http://localhost:8080/api/v1/game/puzzle15/{playerID}/{DirectionToMoveTile}
    - http://localhost:8080/api/v1/game/puzzle15/{playerID}/up
    - http://localhost:8080/api/v1/game/puzzle15/{playerID}/down
    - http://localhost:8080/api/v1/game/puzzle15/{playerID}/left
    - http://localhost:8080/api/v1/game/puzzle15/{playerID}/right
