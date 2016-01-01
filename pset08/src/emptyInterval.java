import java.util.Comparator;
import java.util.Objects;

public class emptyInterval <T> extends AbstractInterval {

  public emptyInterval(Comparator<T> comparator){super(comparator);}

  @Override
  public boolean contains(Object value) {
    return false;
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public Object lowerBound() {
    throw new IllegalStateException("empty interval");
  }

  @Override
  public Object upperBound() {
    throw new IllegalStateException("empty interval");
  }

  @Override
  public BoundType lowerBoundType() {
    return BoundType.Closed;
  }

  @Override
  public BoundType upperBoundType() {
    return BoundType.Open;
  }

  @Override
  public boolean includes(Interval other) {
    Objects.requireNonNull(other);

    if(other.isEmpty()){
      return true;
    }

    return false;

  }

  @Override
  public Interval intersection(Interval other) {
    Objects.requireNonNull(other);
    return this;
  }

  @Override
  public Interval span(Interval other) {
    Objects.requireNonNull(other);
    return other;
  }

  @Override
  public Comparator getComparator() {
    return comparator;
  }

  @Override
  public boolean equals(Object other){
    Objects.requireNonNull(other);

    if (!(other instanceof Interval)){
      return false;
    }

    Interval o = (Interval) other;

    if(o.isEmpty()){
      return true;
    }

    return false;
  }
}
