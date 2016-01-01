import org.junit.Test;
import static org.junit.Assert.*;

/** Tests for {@link CoinGameModelAdaptor}. */
public class CoinGameModelAdaptorTest {

  String [] Players = {"ali", "luke"};

  CoinGameModelAdaptor Game = CoinGameModelAdaptor.fromString("--O-O-O", Players);



  @Test
  public void testPlayers() {
    CoinGamePlayer [] order = Game.getPlayOrder();
    assertEquals(order[0].getName(), "ali");
    assertEquals(order[1].getName(), "luke");

    assertEquals(order[0].isTurn(), true);
    assertEquals(order[1].isTurn(), false);

    order[0].move(0, 0);

    assertEquals(order[0].isTurn(), false);
    assertEquals(order[1].isTurn(), true);

    order[1].move(1, 1);

    assertEquals(order[0].isTurn(), true);
    assertEquals(order[1].isTurn(), false);

    order[0].move(2, 2);

    assertEquals(order[0].isTurn(), true);
    assertEquals(order[1].isTurn(), false);

    assertEquals(Game.getWinner().getName(), "ali");
  }

  @Test
  public void testAddPlayers() {
    CoinGamePlayer [] order = Game.getPlayOrder();
    assertEquals(order[0].getName(), "ali");
    assertEquals(order[1].getName(), "luke");

    Game.addPlayerAfter(order[0], "x");

    order = Game.getPlayOrder();

    assertEquals(order[0].getName(), "ali");
    assertEquals(order[1].getName(), "x");
    assertEquals(order[2].getName(), "luke");

    order[0].move(0,1);

    assertEquals(order[0].isTurn(), false);
    assertEquals(order[1].isTurn(), true);
    assertEquals(order[2].isTurn(), false);

    order[1].move(0, 0);

    assertEquals(order[0].isTurn(), false);
    assertEquals(order[1].isTurn(), false);
    assertEquals(order[2].isTurn(), true);

    order[2].move(1, 1);

    assertEquals(order[0].isTurn(), true);
    assertEquals(order[1].isTurn(), false);
    assertEquals(order[2].isTurn(), false);

    order[0].move(2, 2);

    assertEquals(order[0].isTurn(), true);
    assertEquals(order[1].isTurn(), false);
    assertEquals(order[2].isTurn(), false);

    assertEquals(Game.getWinner().getName(), "ali");

  }

  @Test(expected = NullPointerException.class)
  public void testAddPlayerNullName() {
    CoinGamePlayer [] order = Game.getPlayOrder();
    assertEquals(order[0].getName(), "ali");
    assertEquals(order[1].getName(), "luke");

    Game.addPlayerAfter(order[0], null);
  }

  @Test(expected = NullPointerException.class)
  public void testAddPlayerNullPredecessor() {
    CoinGamePlayer [] order = Game.getPlayOrder();
    assertEquals(order[0].getName(), "ali");
    assertEquals(order[1].getName(), "luke");

    Game.addPlayerAfter(null, "x");
  }

  @Test
  public void testOtherConstructor() {
    CoinGameModel strictGame = new StrictCoinGameModel("O-OO-", 2);
    CoinGameModelAdaptor Game1 = new CoinGameModelAdaptor(strictGame);

    CoinGamePlayer [] order = Game1.getPlayOrder();

    assertEquals(order[0].getName(), "Player:0");
    assertEquals(order[1].getName(), "Player:1");
    //assertEquals(order[2].getName(), "Player:2");

  }

  @Test
  public void testCoinCount() {
    assertEquals(3, Game.coinCount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalBoard() {
    CoinGameModelAdaptor illegalGame = CoinGameModelAdaptor.fromString("--O-O ueO-", Players);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlankBoard() {
    CoinGameModelAdaptor blankGame = CoinGameModelAdaptor.fromString("------", Players);
  }

  @Test
  public void testGetCoinPosition() {
    int [] pos = {2,4,6};
    assertEquals(2, Game.getCoinPositions()[0]);
    assertEquals(4, Game.getCoinPositions()[1]);
    assertEquals(6, Game.getCoinPositions()[2]);
  }

  @Test
  public void testGetCurrentPlayer() {
    assertEquals("ali", Game.getCurrentPlayer().getName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayersFinishedBoard() {
    CoinGameModelAdaptor endGame = CoinGameModelAdaptor.fromString("OOO----", Players);
    CoinGamePlayer [] order = endGame.getPlayOrder();
    assertEquals(order[0].getName(), "ali");
    assertEquals(order[1].getName(), "luke");

    endGame.addPlayerAfter(order[0], "x");
  }

  @Test
  public void testGetTurn() {
    assertEquals("ali", Game.getCurrentPlayer().getName());
    Game.getCurrentPlayer().move(0, 0);
    assertEquals("luke", Game.getCurrentPlayer().getName());
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveOnCoin() {
    assertEquals("ali", Game.getCurrentPlayer().getName());
    Game.getCurrentPlayer().move(1, 2);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveOverCoin() {
    assertEquals("ali", Game.getCurrentPlayer().getName());
    Game.getCurrentPlayer().move(1, 0);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveRight() {
    assertEquals("ali", Game.getCurrentPlayer().getName());
    Game.getCurrentPlayer().move(0, 3);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveToSameSpot() {
    assertEquals("ali", Game.getCurrentPlayer().getName());
    Game.getCurrentPlayer().move(1, 2);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testMoveToNegative() {
    assertEquals("ali", Game.getCurrentPlayer().getName());
    Game.getCurrentPlayer().move(0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOutOfTurn() {
    CoinGamePlayer [] order = Game.getPlayOrder();
    assertEquals(order[0].getName(), "ali");
    assertEquals(order[1].getName(), "luke");

    assertEquals("ali", Game.getCurrentPlayer().getName());

    order[1].move(0, 0);
  }

  public void testPlayerEquals() {
    CoinGamePlayer [] order = Game.getPlayOrder();
    assertEquals(order[0].getName(), "ali");
    assertEquals(order[1].getName(), "luke");

    assertEquals(true, Game.getCurrentPlayer().equals(order[0]));
    assertEquals(false, Game.getCurrentPlayer().equals(order[1]));
  }

  public void testPlayerEqualsSameName() {
    CoinGamePlayer [] order = Game.getPlayOrder();
    assertEquals(order[0].getName(), "ali");
    assertEquals(order[1].getName(), "luke");
    CoinGamePlayer player = order[1];
    CoinGamePlayer newPlayer = Game.addPlayerAfter(order[1], "luke");

    assertEquals(false, player.equals(newPlayer));
  }

}