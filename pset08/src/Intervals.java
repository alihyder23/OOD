import org.omg.CORBA.*;

import sun.jvm.hotspot.utilities.*;

import java.lang.Object;
import java.util.Comparator;
import java.util.Objects;


public class Intervals extends Object {

  public static <T extends Comparable<T>> Interval<T> closed(T lower, T upper){
    Comparator<T> comparator = Comparator.naturalOrder();
    return closed(lower,upper,comparator);
  }

  public static <T> Interval <T> closed(T lower, T upper, Comparator<T> comparator){
    return new closedInterval<T>(lower,upper, comparator);
  }

  public static <T extends Comparable<T>> Interval <T> closedOpen(T lower, T upper){
    Comparator<T> comparator = Comparator.naturalOrder();
    return closedOpen(lower, upper, comparator);
  }

  public static <T> Interval<T> closedOpen(T lower, T upper, Comparator<T> comparator){
    if(comparator.compare(lower, upper) == 0){
      return Intervals.empty(comparator);
    }

    return new closedOpenInterval<T>(lower,upper,comparator);
  }

  public static <T extends Comparable<T>> Interval<T> empty(){
    Comparator<T> comparator = Comparator.naturalOrder();
    return empty(comparator);
  }

  public static <T> Interval<T> empty(Comparator<T> comparator){

    return new emptyInterval<T>(comparator);
  }

  public static <T extends Comparable<T>> Interval<T> interval(T lower, BoundType lowerType,
                                                               T upper, BoundType upperType){
    Comparator<T> comparator = Comparator.naturalOrder();
    return interval(lower, lowerType, upper, upperType, comparator);
  }

  public static <T> Interval<T> interval(T lower, BoundType lowerType, T upper, BoundType upperType,
                                         Comparator<T> comparator){
    if(lowerType == BoundType.Closed){
      if(upperType == BoundType.Closed){
        return closed(lower, upper, comparator);
      } else {
        return closedOpen(lower, upper, comparator);
      }
    } else {
      if(upperType == BoundType.Closed){
        return openClosed(lower, upper, comparator);
      } else {
        return open(lower, upper, comparator);
      }
    }
  }

  public static <T extends Comparable<T>> Interval<T> open(T lower, T upper){
    Comparator<T> comparator = Comparator.naturalOrder();
    return open(lower, upper, comparator);
  }

  public static <T> Interval<T> open(T lower, T upper, Comparator<T> comparator){
    return new openInterval<T>(lower,upper,comparator);
  }

  public static <T extends Comparable<T>> Interval<T> openClosed(T lower, T upper){
    Comparator<T> comparator = Comparator.naturalOrder();
    return openClosed(lower, upper, comparator);
  }

  public static <T> Interval<T> openClosed(T lower, T upper, Comparator<T> comparator){
    if(comparator.compare(lower, upper) == 0){
      return Intervals.empty(comparator);
    }

    return new openClosedInterval<T>(lower,upper,comparator);
  }

  public static <T extends Comparable<T>> Interval<T>	singleton(T value){
    Comparator<T> comparator = Comparator.naturalOrder();
    return closed(value, value, comparator);
  }

  public static <T> Interval<T> singleton(T value, Comparator<T> comparator){
    return new closedInterval<T>(value,value,comparator);
  }

}
