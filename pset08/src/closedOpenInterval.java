import java.util.Comparator;

public class closedOpenInterval <T> extends AbstractInterval {

  public closedOpenInterval(T lowerbound, T upperbound, Comparator<T> comparator){
    super(lowerbound,upperbound,comparator);
  }

  @Override
  public BoundType lowerBoundType() {
    return BoundType.Closed;
  }

  @Override
  public BoundType upperBoundType() {
    return BoundType.Open;
  }
}
