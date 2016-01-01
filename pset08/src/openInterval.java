import java.util.Comparator;


public class openInterval <T> extends AbstractInterval {

  public openInterval(T lowerbound, T upperbound, Comparator<T> comparator){
    super(lowerbound,upperbound,comparator);
  }

  @Override
  public BoundType lowerBoundType() {
    return BoundType.Open;
  }

  @Override
  public BoundType upperBoundType() {
    return BoundType.Open;
  }
}
