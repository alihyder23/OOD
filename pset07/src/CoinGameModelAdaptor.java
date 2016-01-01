import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class CoinGameModelAdaptor implements NewCoinGameModel {

  private final CoinGameModel adaptee;
  private List<CoinGamePlayerAdaptor> players;

  /**
   * Constructs a NewCoinGameModel Adaptor
   *
   * @param adaptee a CoinGameModel object to use
   * @throws IllegalArgumentException {@code adaptee} is illegal
   *
   */
  public CoinGameModelAdaptor(CoinGameModel adaptee) {
    this.adaptee = adaptee;
    this.players = new ArrayList();

    if(this.adaptee == null) {
      throw new IllegalArgumentException("Cannot accept null adaptee");
    }

    for (int i=0; i < this.adaptee.getPlayers() - 1; i++){
      this.players.add(new CoinGamePlayerAdaptor("Player:" + i));
    }
  }

  /**
   * Private method used by static method to constructs a NewCoinGameModel Adaptor
   *
   * @param initialBoard the board to play with
   * @param players a list of names for players
   * @throws IllegalArgumentException {@code initialBoard} is illegal
   * @throws IllegalArgumentException {@code players} is illegal
   *
   */
  private CoinGameModelAdaptor(String initialBoard, String... players){
    CoinGameModel model = new StrictCoinGameModel(initialBoard, players.length);
    this.adaptee = model;
    this.players = new ArrayList();

    for (int i=0; i < players.length; i++){
      this.players.add(new CoinGamePlayerAdaptor(players[i]));
    }
  }

  /**
   * Public method to construct a NewCoinGameModel Adaptor
   *
   * @param initialBoard the board to play with
   * @param players a list of names for players
   * @throws IllegalArgumentException {@code initialBoard} is illegal
   * @throws IllegalArgumentException {@code players} is illegal
   *
   */
  public static CoinGameModelAdaptor fromString(String initialBoard, String... players) {
    return new CoinGameModelAdaptor(initialBoard, players);
  }

  @Override
  public int boardSize() {
    return adaptee.boardSize();
  }

  @Override
  public int coinCount() {
    return adaptee.coinCount();
  }

  @Override
  public int[] getCoinPositions() {
    int[] result = new int[adaptee.coinCount()];

    for (int i = 0; i < adaptee.coinCount(); i++) {
      result[i] = adaptee.getCoinPosition(i);
    }

    return result;
  }

  @Override
  public CoinGamePlayer[] getPlayOrder() {
    CoinGamePlayer[] playOrder = new CoinGamePlayer[adaptee.getPlayers()];

    for (int i=0; i < players.size(); i++){
      playOrder[i] = players.get(i);
    }

    return playOrder;
  }

  @Override
  public CoinGamePlayer getWinner() {
    return players.get(adaptee.getWinner() % players.size());
  }

  @Override
  public CoinGamePlayer getCurrentPlayer() {
    return players.get(adaptee.getTurn() % players.size());
  }

  @Override
  public CoinGamePlayer addPlayerAfter(CoinGamePlayer predecessor, String name) {
    if(predecessor == null){
      throw new NullPointerException("Predecessor cannot be null");
    }

    if(name == null){
      throw new NullPointerException("Name cannot be null");
    }

    Integer predecessorIndex = null;

    for (int i=0; i< this.players.size(); i++){
      if(predecessor.equals(this.players.get(i))){
        predecessorIndex = i;
      }
    }
    if(predecessorIndex == null){
      throw new IllegalArgumentException("Predecessor not a player in this game's play order");
    }

    this.adaptee.addPlayer(predecessorIndex+1);
    CoinGamePlayerAdaptor newPlayer = new CoinGamePlayerAdaptor(name);
    this.players.add(predecessorIndex+1, newPlayer);

    return newPlayer;
  }








  public class CoinGamePlayerAdaptor implements CoinGamePlayer {
    private final String name;

    public CoinGamePlayerAdaptor(String name){
      if(name == null){
        throw new NullPointerException("Name cannot be null");
      }

      this.name = name;
    }

    public String getName() {
     return name;
    }

    @Override
    public void move(int coinIndex, int newPosition) {
      if(!this.isTurn()){
        throw new IllegalArgumentException("not this players turn");
      }
      adaptee.move(coinIndex, newPosition);
    }

    @Override
    public boolean isTurn() {
      if(getCurrentPlayer().equals(this)){
        return true;
      }
      return false;
    }

    @Override
    public boolean equals(Object obj) {

      if (!(obj instanceof CoinGamePlayer)){
        return false;
      }
      if (obj == this) {
        return true;
      }
      return false;
    }
  }
}
