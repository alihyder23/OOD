import org.junit.Test;
import static org.junit.Assert.*;

/** Tests for {@link StrictCoinGameModel}. */
public class StrictCoinGameModelTest {

  @Test
  public void testScenario1_1() {
    assertEquals("OOOO------",
                 CoinGameTestScenarios.scenario1("---O-O-O-O"));
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testScenario1_2() {
    CoinGameTestScenarios.scenario1("O-O-O---");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testScenario3_1() {
    assertEquals("OOOO-----O-",
                 CoinGameTestScenarios.scenario3("-O-OO-O--O-"));
  }

  @Test
  public void testScenario4_1() {
    assertEquals("OOO-O---",
                 CoinGameTestScenarios.scenario4("OOO---O-"));
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testScenario4_2() {
    CoinGameTestScenarios.scenario4("O-O-O-O-");
  }

  @Test
  public void testScenario5_1() {
    assertEquals(true,
                 CoinGameTestScenarios.scenario5("O------O-"));
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testScenario5_2() {
    CoinGameTestScenarios.scenario5("-O-----O-");
  }

  CoinGameModel strictGame = new StrictCoinGameModel("--O-O-O", 3);

  @Test
  public void testGame() {
    assertEquals(0, strictGame.getTurn());

    strictGame.move(0,0);

    assertEquals(1, strictGame.getTurn());

    strictGame.addPlayer(1);

    assertEquals(3, strictGame.getTurn());

    strictGame.move(1,3);

    assertEquals(1, strictGame.getTurn());

    strictGame.move(1,2);

    assertEquals(2, strictGame.getTurn());

    strictGame.move(1,1);

    assertEquals(0, strictGame.getTurn());

    strictGame.move(2,2);

    assertEquals(0, strictGame.getWinner());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerInvalidIndex() {
    strictGame.addPlayer(5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerNegativeIndex() {
    strictGame.addPlayer(-1);
  }

  @Test
  public void testAddPlayerBeginning() {
    strictGame.move(0,0);
    strictGame.move(1,1);
    strictGame.addPlayer(0);
    assertEquals(2, strictGame.getTurn());
  }

  @Test
  public void testAddPlayerEnd() {
    strictGame.move(0,0);
    strictGame.move(1,1);
    strictGame.addPlayer();
    assertEquals(2, strictGame.getTurn());
  }

  @Test
  public void testAddPlayerMiddle() {
    strictGame.move(0,0);
    strictGame.move(1,1);
    strictGame.addPlayer(2);
    assertEquals(3, strictGame.getTurn());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerGameOver() {
    strictGame.move(0,0);
    strictGame.move(1,1);
    strictGame.move(2,2);
    strictGame.addPlayer();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetWinnerGameNotOver() {
    strictGame.move(0,0);
    strictGame.move(1,1);
    strictGame.getWinner();
  }

  @Test
  public void testGetWinnerGameOver() {
    strictGame.move(0,0);
    strictGame.move(1,1);
    strictGame.move(2,2);

    assertEquals(2,strictGame.getWinner());
  }

  @Test
  public void testGetWinnerPlayerAdded() {
    strictGame.move(0,0);
    strictGame.move(1,1);
    strictGame.addPlayer();
    strictGame.move(2,2);

    assertEquals(2,strictGame.getWinner());
  }

  @Test
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
  public void testMoveToNegativePosition() {
    strictGame.move(0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCoinIndex() {
    strictGame.move(3, 4);
  }

}