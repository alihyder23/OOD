import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class StrictCoinGameModel extends AbstractCoinGameModel {

  @Override
  protected AbstractCoinGameModel playGame(String board){return new StrictCoinGameModel(board);}

  /**
   * Constructs a CoinGameModel based on Lax rules
   *
   * @param board the board to play with
   * @throws IllegalArgumentException {@code board} is illegal
   *
   */
  public StrictCoinGameModel(String board) {

    /*INVARIANT: String board cannot be empty or contain illegal characters*/

    this.boardSize = board.length();
    this.board = new ArrayList();

    int i = 0;

    if(this.boardSize == 0) {
      throw new IllegalArgumentException("empty string entered to initialize board");
    }

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
  }

  /**
   * Moves coin {@code coinIndex} to position {@code newPosition},
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
  }

}