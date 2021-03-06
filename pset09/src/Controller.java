import java.io.IOException;
import java.util.Objects;

public final class Controller {
  private final Model model;
  private final BoardView view;
  private final IntReader in;
  private final Appendable out;

  public static final String NOT_A_NUMBER =
      "That isn't a number! Please try again.";

  public static final String COLUMN_DNE =
      "That column doesn't exist! Please try again.";

  public static final String COLUMN_FULL =
      "That column is full! Please try again.";

  public static final String NOBODY_WINS = "Game over! Nobody wins :/";

  public static final String PLAYER_WINS = "Game over! %s wins :)";

  public Controller(Model model) {
    this(model,
         new BoardView(System.out),
         IntReader.create(System.in, System.out, NOT_A_NUMBER),
         System.out);
  }

  /**
   * Constructs a controller given the components it will control.
   *
   * @param model the model
   * @param view the view, for rendering the model
   * @param in the source of user moves
   * @param out the output stream
   */
  public Controller(Model model, BoardView view, IntReader in, Appendable out) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);
    Objects.requireNonNull(in);
    Objects.requireNonNull(out);

    this.model = model;
    this.view = view;
    this.in = in;
    this.out = out;
  }

  /**
   * Determines whether the game is over.
   * @return whether the game is over
   */
  public boolean isGameOver() {
    return model.isGameOver();
  }

  /**
   * Runs this controller on its model.
   *
   * @throws IOException if an I/O operation, such as writing to the console,
   * fails
   */
  public void run() throws IOException {
    while (! isGameOver()) {
      step();
    }
  }

  /**
   * Runs one interaction step of this controller on its model.
   *
   * @throws IOException if an I/O operation, such as writing to the console,
   * fails
   */
  public void step() throws IOException {
    view.draw();

    Player who = model.getNextPlayer();
    int where = in.nextInt(who + ": ", this::validateMove);

    model.move(who, where);

    if (model.isGameOver()) {
      view.draw();

      String winner = model.getWinner().toString();
      out.append(String.format(PLAYER_WINS, winner)).append('\n');
    }
  }

  /**
   * Checks whether a particular move is valid in the current model. Returns
   * an error message if the move is invalid, or {@code null} if the move is
   * valid.
   *
   * @param where the move to check
   * @return {@code null} or an error message
   */
  private String validateMove(int where) {
    //check column and row

    // It's valid!
    return null;
  }
}
