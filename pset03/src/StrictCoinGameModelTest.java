import org.junit.Test;
import static org.junit.Assert.*;

/** Tests for {@link StrictCoinGameModel}. */
public class StrictCoinGameModelTest extends AbstractCoinGameModelTest
{
  CoinGameModel strictGame = new StrictCoinGameModel("--O-O-O");

  /**
   * Helps by constructing board of the appropriate class.
   *
   * @param board the board to play with
   * @return board of the appropriate class
   * @throws IllegalArgumentException {@code board} is empty
   * @throws java.lang.NullPointerException {@code board} = null
   * @throws java.lang.IllegalArgumentException {@code board} has characters other than 'O' or '-'
   *
   */
  @Override
  protected AbstractCoinGameModel playGame(String board) {
    return new StrictCoinGameModel(board);
  }

  @Test
  public void testBasicMove() {
    strictGame.move(0, 1);
    assertEquals("-O--O-O", strictGame.toString());
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveOnCoin() {
    strictGame.move(1, 2);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveOverCoin() {
    strictGame.move(1, 0);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveRight() {
    strictGame.move(0, 3);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveToSameSpot() {
    strictGame.move(1, 2);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveToNegative() {
    strictGame.move(0, -1);
  }

  @Test
  public void testPlayFullGame() {
    strictGame.move(0,0);
    strictGame.move(1,1);
    strictGame.move(2,2);

    assertEquals(true, strictGame.isGameOver());
  }


}