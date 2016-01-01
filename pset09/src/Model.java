import java.util.Iterator;
import java.util.List;

/**
 * Created by AliHyder on 4/21/15.
 */
public class Model implements ReadOnlyBoardViewModel {

  private int status;
  private final Position board = ;

  public Model(){
    board = Position.fromRowColumn(1,1);
    board = null;
  }

  @Override
  public String get(int row, int column, int width) {
    return board.get(row).get(column).toString();
  }

  @Override
  public Iterator<Integer> rows() {
    return board.iterator();
  }

  @Override
  public Iterator<Integer> columns() {
    return null;
  }

  @Override
  public Boolean isValidPosition(int row, int column) {
    return null;
  }

  public Boolean isGameOver() {
    return null;
  }

  public Player getNextPlayer() {
    return null;
  }

  public void move(Player who, int where) {
  }

  public void getStatus() {
  }

  public Player getWinner() {
    return null;
  }
}
