import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Defines tests for {@link AbstractCoinGameModel}s without knowing or caring which
 * concrete implementation is being tested.
 */
public abstract class AbstractCoinGameModelTest {

  String board = "-O-OO-";
  String endGame = "OOO---";
  String noCoins = "-------";
  String illegal = "--O--**--fg-6- -";
  String empty = "";
  String nothing;

  /**
   * Helps by constructing CoinGameModel of the appropriate class.
   *
   * @param board the board to play with
   * @return board of the appropriate class
   * @throws IllegalArgumentException {@code board} is empty
   * @throws java.lang.NullPointerException {@code board} = null
   * @throws java.lang.IllegalArgumentException {@code board} has characters other than 'O' or '-'
   *
   */
  protected abstract AbstractCoinGameModel playGame(String board);

  @Test
  public void testBoardSize() {
    assertEquals(6, playGame(board).boardSize());
  }

  @Test
  public void testCoinCount() {
    assertEquals(3, playGame(board).coinCount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCoinCountIllegalBoard() {
    playGame(illegal).coinCount();

  }

  @Test
  public void testGetCoinPosition() {
    assertEquals(1, playGame(board).getCoinPosition(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCoinPositionNoCoins() {
    playGame(noCoins).getCoinPosition(0);
  }

  @Test
  public void negativeTestIsGameOver() {
    assertEquals(false, playGame(board).isGameOver());
  }

  @Test
  public void testIsGameOver() {
    assertEquals(true, playGame(endGame).isGameOver());
  }

  @Test
  public void testIsGameOverNoCoins() {
    assertEquals(true, playGame(noCoins).isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalBoard() {
    playGame(illegal).boardSize();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCoinLessBoard() {
    playGame(empty).boardSize();
  }

  @Test(expected = NullPointerException.class)
  public void testEmpty() {
    playGame(nothing).boardSize();
  }

}
