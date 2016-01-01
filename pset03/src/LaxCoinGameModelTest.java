import org.junit.Test;
import static org.junit.Assert.*;

/** Tests for {@link LaxCoinGameModel}. */
public class LaxCoinGameModelTest extends AbstractCoinGameModelTest
{
  CoinGameModel laxGame = new LaxCoinGameModel("-O-OO-");

  /**
   * Helps by constructing board of the appropriate class.
   *
   * @param board the board to play with
   * @return board of the appropriate class
   * @throws IllegalArgumentException {@code board} is empty
   *
   */
  @Override
  protected AbstractCoinGameModel playGame(String board) {
    return new LaxCoinGameModel(board);
  }

  @Test
  public void testMove() {
    laxGame.move(0, 0);
    assertEquals("O--OO-", laxGame.toString());
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveCoinOnCoin() {
    laxGame.move(1, 1);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveRight() {
    laxGame.move(0, 4);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveToCurrentSpot() {
    laxGame.move(0, 1);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveToNegativeSpot()
  {
    laxGame.move(0, -1);
  }

  @Test
  public void testPlayFullGame(){
    laxGame.move(2,0);
    laxGame.move(2,2);

    assertEquals(true, laxGame.isGameOver());
  }
}