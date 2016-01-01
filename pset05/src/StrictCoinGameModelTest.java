import org.junit.Test;
import static org.junit.Assert.*;

/** Tests for {@link StrictCoinGameModel}. */
public class StrictCoinGameModelTest {


  CoinGameModel strictGame = new StrictCoinGameModel("--O-O-O", 2);

  /*@Test
  public void testBoardSize() {
    assertEquals(7, strictGame.boardSize());
  }

  /*@Test
  public void testBoardSize() {
    assertEquals(7, strictGame.boardSize());
  }

  @Test
  public void testCoinCount() {
    assertEquals(3, strictGame.coinCount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalBoard() {
    CoinGameModel illegalStrictGame = new StrictCoinGameModel("--O-O ueO-", 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlankBoard() {
    CoinGameModel blankGame = new StrictCoinGameModel("------", 2);
  }

  @Test
  public void testGetCoinPosition() {
    assertEquals(2, strictGame.getCoinPosition(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCoinPositionOutOfBounds(){
    strictGame.getCoinPosition(5);
  }

  @Test
  public void negativeTestIsGameOver() {
    assertEquals(false, strictGame.isGameOver());
  }

  @Test
  public void testIsGameOver() {
    assertEquals(true, new StrictCoinGameModel("OOO----", 2).isGameOver());
  }

  @Test
  public void testNumPlayers() {
    assertEquals(3, strictGame.getPlayers());
  }

  @Test
  public void testAddPlayers() {
    assertEquals(3, strictGame.getPlayers());
    strictGame.addPlayer();
    assertEquals(4, strictGame.getPlayers());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayersFinishedBoard() {
    CoinGameModel endGame = new StrictCoinGameModel("OOO----", 2);
    endGame.addPlayer();
  }

  @Test
  public void testGetTurn() {
    assertEquals(0, strictGame.getTurn());
    strictGame.move(0, 0);
    assertEquals(1, strictGame.getTurn());
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
    assertEquals(0, strictGame.getTurn());
    strictGame.move(2,5);

    assertEquals(1, strictGame.getTurn());
    strictGame.move(0,0);

    strictGame.addPlayer();

    assertEquals(2, strictGame.getTurn());
    strictGame.move(1,1);

    assertEquals(3, strictGame.getTurn());
    strictGame.move(2,3);

    assertEquals(0, strictGame.getTurn());
    strictGame.move(2,2);

    assertEquals(0, strictGame.getWinner());
    assertEquals(true, strictGame.isGameOver());


  }*/

}