## Puzzle 15 game TASK implementation

### Task Requirements
Task needed only back end with REST end-points. 
- Application state in memory only, no database allowed.
- Multiple game instances can be played at the same time.
- No need to create logic for moving tiles.
- No front-end needed.
- Application covered by tests.

Repository contains two branches **MAIN** and **PUZZLE**. Puzzle branch can be played in the browser (no front-end created) endpoints return only a string.

### Endpoints For Puzzle Branch:
After you download or clone the Puzzle branch and start it.
- **{playerID} is any positive number**
- **{DirectionToMoveTile} direction up, down, left, right**
- **Start new Game**
  - `http://localhost:8080/api/v1/game/puzzle15/{playerID}/new`
- **Get Current Game Status**
  - `http://localhost:8080/api/v1/game/puzzle15/{playerID}`
- **Move Empty Tile to Direction**
  - `http://localhost:8080/api/v1/game/puzzle15/{playerID}/{DirectionToMoveTile}`
    - `http://localhost:8080/api/v1/game/puzzle15/{playerID}/up`
    - `http://localhost:8080/api/v1/game/puzzle15/{playerID}/down`
    - `http://localhost:8080/api/v1/game/puzzle15/{playerID}/left`
    - `http://localhost:8080/api/v1/game/puzzle15/{playerID}/right`
- **After you start application**
    - http://localhost:8080/api/v1/game/puzzle15/1/new
    - http://localhost:8080/api/v1/game/puzzle15/1/up
    - http://localhost:8080/api/v1/game/puzzle15/1/down
    - http://localhost:8080/api/v1/game/puzzle15/1/left
    - http://localhost:8080/api/v1/game/puzzle15/1/right


Example of what you should expect to see in browser after you start new game. Numbers will be ramdomized.


```html
********************  
*  1 *  2 *  3 * 4 *  
********************  
*  5 *  6 *  7 * 8 *  
********************  
* 9 * 10 * 11 * 12 *  
********************  
* 13 * 14 * 15 *   *  
********************  


