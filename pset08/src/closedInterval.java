import java.util.Comparator;
import java.lang.Object;


public class closedInterval <T> extends AbstractInterval{


  public closedInterval(T lowerbound, T upperbound, Comparator<T> comparator){
    super(lowerbound, upperbound,comparator);
  }

  @Override
  public BoundType lowerBoundType() {
    return BoundType.Closed;
  }

  @Override
  public BoundType upperBoundType() {
    return BoundType.Closed;
  }
}
