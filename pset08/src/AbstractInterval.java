import java.util.ArrayList;
import java.util.Comparator;
import java.lang.Object;
import java.util.List;
import java.util.Objects;


abstract class AbstractInterval <T> implements Interval{
  protected T lowerbound;
  protected T upperbound;
  protected Comparator<T> comparator;

  public AbstractInterval(T lowerbound, T upperbound, Comparator<T> comparator){
    Objects.requireNonNull(lowerbound);
    Objects.requireNonNull(upperbound);
    Objects.requireNonNull(comparator);

    this.lowerbound = lowerbound;
    this.upperbound = upperbound;
    this.comparator = comparator;

    if(!(this.comparator.compare(lowerbound, upperbound) <= 0)){
      throw new IllegalArgumentException("lowerbound greater than upperbound");
    }
  }

  public AbstractInterval(Comparator<T> comparator){
    Objects.requireNonNull(comparator);
    this.comparator = comparator;
  }

  @Override
  public boolean contains(Object value) {
    if (value == null){
      throw new IllegalArgumentException("value is null");
    }
    if(this.lowerBoundType() == BoundType.Open){
      if(this.upperBoundType() == BoundType.Open){
        return this.comparator.compare((T) value, this.lowerbound) > 0 &&
               this.comparator.compare((T) value, this.upperbound) < 0;
      } else {
        return this.comparator.compare((T) value, this.lowerbound) > 0 &&
               this.comparator.compare((T) value, this.upperbound) <= 0;
      }
    } else {
      if(this.upperBoundType() == BoundType.Open){
        return this.comparator.compare((T) value, this.lowerbound) >= 0 &&
               this.comparator.compare((T) value, this.upperbound) < 0;
      } else {
        return this.comparator.compare((T) value, this.lowerbound) >= 0 &&
               this.comparator.compare((T) value, this.upperbound) <= 0;
      }
    }
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public Object lowerBound() {
    return this.lowerbound;
  }

  @Override
  public Object upperBound() {
    return this.upperbound;
  }

  @Override
  public boolean includes(Interval other) {
    boolean lower;
    boolean upper;

    if(other.isEmpty()){
      return true;
    } else if (this.contains(other.lowerBound()) && this.contains(other.upperBound())){
      return true;

      } else {
      if(this.lowerBoundType() == BoundType.Open && other.lowerBoundType() == BoundType.Open
          && this.lowerBound() == other.lowerBound() && this.contains(other.upperBound())){
        return true;
      }else if(this.upperBoundType() == BoundType.Open && other.upperBoundType() == BoundType.Open
               && this.upperBound() == other.upperBound() && this.contains(other.lowerBound())){
        return true;
      }else if(this.lowerBoundType() == BoundType.Open && other.lowerBoundType() == BoundType.Open
               && this.lowerBound() == other.lowerBound() && this.upperBoundType() == BoundType.Open
               && other.upperBoundType() == BoundType.Open
               && this.upperBound() == other.upperBound()){
        return true;
      } else {
        return false;
      }
    }
  }

  @Override
  public Interval intersection(Interval other) {
    T lbound = null;
    T ubound = null;
    BoundType lower = null;
    BoundType upper = null;
    Interval inter;

    Objects.requireNonNull(other);

    if (other.isEmpty()) {
      return Intervals.empty(comparator);
    }

    if (this.comparator.compare(this.lowerbound, (T) other.lowerBound()) < 0) {
      lbound = (T) other.lowerBound();
      lower = other.lowerBoundType();
    } else if (this.comparator.compare(this.lowerbound, (T) other.lowerBound()) > 0) {
      lbound = (T) this.lowerBound();
      lower = this.lowerBoundType();
    } else {
      lbound = (T) this.lowerBound();
      if (this.lowerBoundType() == BoundType.Open || other.lowerBoundType() == BoundType.Open) {
        lower = BoundType.Open;
      } else {
        lower = BoundType.Closed;
      }
    }

    if (this.comparator.compare(this.upperbound, (T) other.upperBound()) < 0) {
      ubound = (T) this.upperBound();
      upper = this.upperBoundType();
    } else if (this.comparator.compare(this.upperbound, (T) other.upperBound()) > 0){
      ubound = (T) other.upperBound();
      upper = other.upperBoundType();
    } else{
      ubound = (T) this.upperBound();
      if(this.upperBoundType() == BoundType.Open || other.upperBoundType() == BoundType.Open){
        upper = BoundType.Open;
      }
      else{
        upper = BoundType.Closed;
      }
    }

    if(this.comparator.compare(lbound, ubound) < 0) {
      inter = Intervals.empty(this.comparator);
    }

    if(lower == BoundType.Closed && upper == BoundType.Closed){
      inter = Intervals.closed(lbound, ubound, this.comparator);
    }
    else if(lower == BoundType.Closed && upper == BoundType.Open){
      inter = Intervals.closedOpen(lbound, ubound, this.comparator);
    }
    else if(lower == BoundType.Open && upper == BoundType.Open){
      inter = Intervals.open(lbound, ubound, this.comparator);
    }
    else {//(lower == BoundType.Open && upper == BoundType.Closed){
      inter = Intervals.openClosed(lbound, ubound, this.comparator);
    }

    return inter;
  }

  @Override
  public Interval span(Interval other) {
    T lbound = null;
    T ubound = null;
    BoundType lower = null;
    BoundType upper = null;
    Interval inter;

    Objects.requireNonNull(other);

    if (other.isEmpty()) {
      return this;
    }

    if (this.comparator.compare(this.lowerbound, (T) other.lowerBound()) < 0) {
      lbound = (T) this.lowerBound();
      lower = this.lowerBoundType();
    } else if (this.comparator.compare(this.lowerbound, (T) other.lowerBound()) > 0) {
      lbound = (T) other.lowerBound();
      lower = other.lowerBoundType();
    } else {
      lbound = (T) this.lowerBound();
      if (this.lowerBoundType() == BoundType.Closed || other.lowerBoundType() == BoundType.Closed) {
        lower = BoundType.Closed;
      } else {
        lower = BoundType.Open;
      }
    }

    if (this.comparator.compare(this.upperbound, (T) other.upperBound()) < 0) {
      ubound = (T) other.upperBound();
      upper = other.upperBoundType();
    } else if (this.comparator.compare(this.upperbound, (T) other.upperBound()) > 0){
      ubound = (T) this.upperBound();
      upper = this.upperBoundType();
    } else {
      ubound = (T) this.upperBound();
      if(this.upperBoundType() == BoundType.Closed || other.upperBoundType() == BoundType.Closed){
        upper = BoundType.Closed;
      }
      else{
        upper = BoundType.Open;
      }
    }

    if(lower == BoundType.Closed && upper == BoundType.Closed){
      inter = Intervals.closed(lbound, ubound, this.comparator);
    }
    else if(lower == BoundType.Closed && upper == BoundType.Open){
      inter = Intervals.closedOpen(lbound, ubound, this.comparator);
    }
    else if(lower == BoundType.Open && upper == BoundType.Open){
      inter = Intervals.open(lbound, ubound, this.comparator);
    }
    else {//(lower == BoundType.Open && upper == BoundType.Closed){
      inter = Intervals.openClosed(lbound, ubound, this.comparator);
    }

    return inter;
  }

  @Override
  public Comparator getComparator() {
    return comparator;
  }

  @Override
  public boolean equals(Object other){
    Objects.requireNonNull(other);

    Interval o = (Interval) other;

    if (!(other instanceof Interval)){
      return false;
    }

    if(this.isEmpty() && o.isEmpty()){
      return true;
    }

    if(this.lowerbound == o.lowerBound() &&
       this.upperbound == o.upperBound() &&
       this.lowerBoundType() == o.lowerBoundType() &&
       this.upperBoundType() == o.upperBoundType()){

      return true;
    }

    return false;
  }

  @Override
  public String toString(){
    String first;
    String second;

    if(this.isEmpty()){
      return "Empty";
    }

    if (this.lowerBoundType() == BoundType.Open){
      first = "(";
      if(this.upperBoundType() == BoundType.Open){
        second = ")";
      }
      else{
        second = "]";
      }
    }else {
      first = "[";
      if(this.upperBoundType() == BoundType.Open){
        second = ")";
      }
      else{
        second = "]";
      }
    }

    return first + this.lowerbound + "," + this.upperBound() + second;
  }

  @Override
  public int hashCode() {
    int result = lowerbound.hashCode();
    result = 31 * result + upperbound.hashCode();
    return result;
  }
}

