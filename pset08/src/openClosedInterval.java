import java.util.Comparator;


public class openClosedInterval <T> extends AbstractInterval {

  public openClosedInterval(T lowerbound, T upperbound, Comparator<T> comparator){
    super(lowerbound,upperbound,comparator);
  }

  @Override
  public BoundType lowerBoundType() {
    return BoundType.Open;
  }

  @Override
  public BoundType upperBoundType() {
    return BoundType.Closed;
  }
}
