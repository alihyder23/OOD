import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class StrictCoinGameModel implements CoinGameModel {

  private final int boardSize;
  private List<Integer> board;
  private List<Integer> players;
  private int turn;
  private Integer winner;

  /*
   * CLASS INVARIANTS:
   *
   *  - boardSize > 0
   *
   *  - players != null
   *
   *  - players.length != 0
   *
   *  - players[i] != null  (for in-bounds i)
   *
   *  - 0 <= turn < players.length
   *
   */

  /**
   * Constructs a CoinGameModel based on Strict rules
   *
   * @param board the board to play with
   * @param numPlayers the number of players playing the game (will initialize this.players)
   * @throws IllegalArgumentException {@code board} is illegal
   * @throws IllegalArgumentException {@code numPlayers} is <= 0
   *
   */
  protected StrictCoinGameModel(String board, int numPlayers) {

    /*INVARIANT: String board cannot be empty or contain illegal characters*/

    this.boardSize = board.length();
    this.board = new ArrayList();
    this.players = new ArrayList();
    this.turn = 0;
    this.winner = null;

    if(this.boardSize == 0) {
      throw new IllegalArgumentException("empty string entered to initialize board");
    }

    if(numPlayers == 0 || numPlayers < 0) {
      throw new IllegalArgumentException("no players specified");
    }

    for( int i = 0; i < numPlayers; i++) {
      this.players.add(i);
    }

    int i = 0;
    while(i < board.length()) {
      switch(board.charAt(i)) {
        case 'O':
          this.board.add(i);
        case '-':
          ++i;
          break;
        default:
          throw new IllegalArgumentException("game board does not follow template:"
                                             + " bad character discovered");
      }
    }

    if (this.board.size() == 0) {
      throw new IllegalArgumentException("no coins on board");
    }
  }


  @Override
  public int boardSize() {
    return this.boardSize;
  }

  @Override
  public int coinCount() {
    return this.board.size();
  }

  @Override
  public int getCoinPosition(int coinIndex) {
    if(coinIndex < 0 || coinIndex >= this.coinCount()) {
      throw new IllegalArgumentException("invalid coin index");
    }
    return (this.board.get(coinIndex));
  }

  @Override
  public boolean isGameOver() {
    for(int i = 0; i < this.board.size(); i++) {
      if(!(this.getCoinPosition(i) < this.coinCount())) {
        return false;
      }
    }

    return true;
  }

  /**
   * Moves coin {@code coinIndex} to position {@code newPosition} and updates whos turn it is,
   * if the move results in a game over, {@code winner} is set to the player
   * who made the winning move,
   * if the requested move is legal. Throws {@code IllegalMoveException} if
   * the requested move is illegal, which can happen in several ways:
   *
   * <ul>
   *   <li>There is no coin with the requested index.</li>
   *   <li>The new position is occupied by another coin.</li>
   *   <li>The new position is negative</li>
   *   <li>The new position is to the right of the current position</li>
   *   <li>The new position is to the left of another coin</li>
   * </ul>
   *
   * @param coinIndex   which coin to move (numbered from the left)
   * @param newPosition where to move it to
   * @throws IllegalMoveException the move is illegal
   */
  @Override
  public void move(int coinIndex, int newPosition) {
    if(coinIndex < 0 || coinIndex >= this.coinCount()) {
      throw new IllegalArgumentException("invalid coin index");
    }

    if(newPosition >= this.getCoinPosition(coinIndex)) {
      throw new IllegalMoveException("must almost move to the left");
    }

    if(newPosition < 0) {
      throw new IllegalMoveException("cannot move to negative position");
    }

    if(coinIndex != 0) {
      if(newPosition <= this.getCoinPosition((coinIndex-1))) {
        throw new IllegalMoveException("cannot move past or on top of another coin");
      }
    }

    this.board.set(coinIndex, newPosition);

    if(this.isGameOver()) {
      this.winner = this.players.get(turn);
    }else {
      this.turn = (turn + 1) % this.getPlayers();
    }

  }

  /**
   * Creates String to represent current {@code CoinGameModel} object
   *
   * @return String with representation of current board
   */
  @Override
  public String toString() {
    char[] output = new char[this.boardSize()];
    Arrays.fill(output, '-');

    for (int i = 0; i < this.coinCount(); i++) {
      int j = this.getCoinPosition(i);
      output[j] = 'O';
    }

    return String.valueOf(output);
  }

  @Override
  public void addPlayer(int index){
    if(this.isGameOver()) {
      throw new IllegalArgumentException("cannot add player to finished game");
    }

    if(index < 0 || index > this.getPlayers()) {
      throw new IllegalMoveException("index out of bounds");
    }

    if (index < this.turn) {
      turn = (turn+1);
    }


    this.players.add(index, this.getPlayers());
  }

  @Override
  public void addPlayer(){

    this.addPlayer(this.getPlayers());
  }

  @Override
  public int getPlayers(){

    return (this.players.size());
  }

  @Override
  public int getTurn(){

    return this.players.get(turn);
  }

  @Override
  public int getWinner(){

    if(this.winner == null) {
      throw new IllegalArgumentException("there is currently no winner");
    }
    return this.winner;
  }

}
