import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Provides functionality common to both representational subclasses,
 */

abstract class AbstractCoinGameModel implements CoinGameModel{
  protected int boardSize;
  protected List<Integer> board;

  /**
   * Helps by constructing board of the appropriate class.
   *
   * @param board the board to play with
   * @return board of the appropriate class
   * @throws IllegalArgumentException {@code board} is illegal
   *
   */
  protected abstract AbstractCoinGameModel playGame(String board);
  

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
    for(int i = 0; i < this.board.size(); ++i) {
      if(!(this.getCoinPosition(i) < this.coinCount())) {
        return false;
      }
    }

    return true;
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
}
