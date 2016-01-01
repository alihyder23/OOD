import java.util.Comparator;
import org.junit.Test;
import static org.junit.Assert.*;


public class IntervalsTest {

  Comparator<Integer> comparator = Comparator.naturalOrder();

  @Test
  public void intervalsToString() {
    Interval inter = Intervals.closedOpen(5, 5);
    Interval inter1 = Intervals.openClosed(5, 5);
    Interval inter2 = Intervals.empty();
    Interval inter3 = Intervals.open(10, 20);
    Interval inter4 = Intervals.closed(10, 20);
    Interval inter5 = Intervals.openClosed(10, 20);
    Interval inter6 = Intervals.closedOpen(10, 20);

    assertEquals("Empty", inter.toString());
    assertEquals("Empty", inter1.toString());
    assertEquals("Empty", inter2.toString());
    assertEquals("(10,20)", inter3.toString());
    assertEquals("[10,20]", inter4.toString());
    assertEquals("(10,20]", inter5.toString());
    assertEquals("[10,20)", inter6.toString());
  }

  @Test
  public void intervalsContains(){
    Comparator<Integer> comparator = Comparator.naturalOrder();
    Interval empty = Intervals.empty(comparator);
    Interval inter = Intervals.open(3, 19, comparator);
    Interval inter1 = Intervals.openClosed(5, 8, comparator);
    Interval inter2 = Intervals.closed(3, 19, comparator);
    Interval inter3 = Intervals.closedOpen(5, 8, comparator);

    assertEquals(false, empty.contains(3));
    assertEquals(false, empty.contains(4));

    assertEquals(false, inter.contains(3));
    assertEquals(false, inter.contains(19));
    assertEquals(true, inter.contains(4));
    assertEquals(true, inter.contains(18));

    assertEquals(false, inter1.contains(5));
    assertEquals(true, inter1.contains(8));
    assertEquals(true, inter1.contains(6));
    assertEquals(true, inter1.contains(7));

    assertEquals(true, inter2.contains(3));
    assertEquals(true, inter2.contains(19));
    assertEquals(false, inter2.contains(2));
    assertEquals(false, inter2.contains(20));

    assertEquals(true, inter3.contains(5));
    assertEquals(false, inter3.contains(8));
    assertEquals(true, inter3.contains(7));
    assertEquals(false, inter3.contains(4));
  }

  @Test
  public void intervalsIsEmpty(){
    Comparator<Integer> comparator = Comparator.naturalOrder();
    Interval empty = Intervals.empty(comparator);
    Interval inter = Intervals.open(3, 3, comparator);
    Interval inter1 = Intervals.openClosed(5, 5, comparator);
    Interval inter2 = Intervals.openClosed(5, 8, comparator);
    Interval inter3 = Intervals.closed(3, 3, comparator);
    Interval inter4 = Intervals.closedOpen(5, 5, comparator);
    Interval inter5 = Intervals.closedOpen(5, 8, comparator);

    assertEquals(true, empty.isEmpty());
    assertEquals(false, inter.isEmpty());
    assertEquals(true, inter1.isEmpty());
    assertEquals(false, inter2.isEmpty());
    assertEquals(false, inter3.isEmpty());
    assertEquals(true, inter4.isEmpty());
    assertEquals(false, inter5.isEmpty());
  }

  @Test
  public void intervalsInclude() {
    Comparator<Integer> comparator = Comparator.naturalOrder();

    assertEquals(true,Intervals.open(3,19,comparator).includes(Intervals.open(5,8,comparator)));
    assertEquals(true, Intervals.closed(4,6,comparator).includes(Intervals.open(5,6,comparator)));
    assertEquals(false, Intervals.open(4,6,comparator).includes(Intervals.closed(4,6,comparator)));

    Interval empty = Intervals.empty(comparator);
    Interval inter = Intervals.closed(4, 6, comparator);
    Interval inter1 = Intervals.open(4, 6, comparator);
    Interval inter2 = Intervals.closedOpen(4,6,comparator);
    Interval inter3 = Intervals.openClosed(4, 6, comparator);

    assertEquals(true, inter.includes(inter));
    assertEquals(true, inter.includes(inter1));
    assertEquals(true, inter.includes(inter2));
    assertEquals(true, inter.includes(inter3));
    assertEquals(true, inter.includes(empty));

    assertEquals(false, inter1.includes(inter));
    assertEquals(true, inter1.includes(inter1));
    assertEquals(false, inter1.includes(inter2));
    assertEquals(false, inter1.includes(inter3));
    assertEquals(true, inter1.includes(empty));

    assertEquals(false, inter2.includes(inter));
    assertEquals(true, inter2.includes(inter1));
    assertEquals(true, inter2.includes(inter2));
    assertEquals(false, inter2.includes(inter3));
    assertEquals(true, inter2.includes(empty));

    assertEquals(false, inter3.includes(inter));
    assertEquals(true, inter3.includes(inter1));
    assertEquals(false, inter3.includes(inter2));
    assertEquals(true, inter3.includes(inter3));
    assertEquals(true, inter3.includes(empty));

    assertEquals(false, empty.includes(inter));
    assertEquals(false, empty.includes(inter1));
    assertEquals(false, empty.includes(inter2));
    assertEquals(false, empty.includes(inter3));
    assertEquals(true, empty.includes(empty));
  }

  @Test
  public void intervalsIntersection() {
    Comparator<Integer> comparator = Comparator.naturalOrder();

    assertEquals(Intervals.open(5, 8),
                 Intervals.open(3, 19).intersection(Intervals.open(5, 8)));
    assertEquals(Intervals.closedOpen(4, 5),
                 Intervals.open(3, 5).intersection(Intervals.closed(4, 6)));
    assertEquals(Intervals.closed(5, 5),
                 Intervals.closed(4,5).intersection(Intervals.closedOpen(5, 6)));
    assertEquals(Intervals.empty(),
                 Intervals.closedOpen(4,5).intersection(Intervals.closedOpen(5, 6)));

    Interval empty = Intervals.empty(comparator);
    Interval inter = Intervals.closed(4, 6, comparator);
    Interval inter1 = Intervals.open(4, 6, comparator);
    Interval inter2 = Intervals.closedOpen(4,6,comparator);
    Interval inter3 = Intervals.openClosed(4, 6, comparator);

    assertEquals(Intervals.closed(4, 6, comparator), inter.intersection(inter));
    assertEquals(Intervals.open(4, 6, comparator), inter.intersection(inter1));
    assertEquals(Intervals.closedOpen(4, 6, comparator), inter.intersection(inter2));
    assertEquals(Intervals.openClosed(4, 6, comparator), inter.intersection(inter3));
    assertEquals(empty, inter.intersection(empty));

    assertEquals(Intervals.open(4, 6, comparator), inter1.intersection(inter));
    assertEquals(Intervals.open(4, 6, comparator), inter1.intersection(inter1));
    assertEquals(Intervals.open(4, 6, comparator), inter1.intersection(inter2));
    assertEquals(Intervals.open(4, 6, comparator), inter1.intersection(inter3));
    assertEquals(empty, inter1.intersection(empty));

    assertEquals(Intervals.closedOpen(4, 6, comparator), inter2.intersection(inter));
    assertEquals(Intervals.open(4, 6, comparator), inter2.intersection(inter1));
    assertEquals(Intervals.closedOpen(4, 6, comparator), inter2.intersection(inter2));
    assertEquals(Intervals.open(4, 6, comparator), inter2.intersection(inter3));
    assertEquals(empty, inter2.intersection(empty));

    assertEquals(Intervals.openClosed(4, 6, comparator), inter3.intersection(inter));
    assertEquals(Intervals.open(4, 6, comparator), inter3.intersection(inter1));
    assertEquals(Intervals.open(4, 6, comparator), inter3.intersection(inter2));
    assertEquals(Intervals.openClosed(4, 6, comparator), inter3.intersection(inter3));
    assertEquals(empty, inter3.intersection(empty));

    assertEquals(empty, empty.intersection(inter));
    assertEquals(empty, empty.intersection(inter1));
    assertEquals(empty, empty.intersection(inter2));
    assertEquals(empty, empty.intersection(inter3));
    assertEquals(empty, empty.intersection(empty));
  }

  @Test
  public void intervalsSpan() {
    Comparator<Integer> comparator = Comparator.naturalOrder();

    assertEquals(Intervals.open(2, 8, comparator),
                 Intervals.open(2, 3, comparator).span(Intervals.open(5, 8, comparator)));
    assertEquals(Intervals.openClosed(3, 6, comparator),
                 Intervals.open(3, 5, comparator).span(Intervals.closed(4, 6, comparator)));
    assertEquals(Intervals.closed(3,4, comparator),
                 Intervals.openClosed(3,4,comparator).span(Intervals.closedOpen(3,4,comparator)));

    Interval empty = Intervals.empty(comparator);
    Interval inter = Intervals.closed(4, 6, comparator);
    Interval inter1 = Intervals.open(4, 6, comparator);
    Interval inter2 = Intervals.closedOpen(4,6,comparator);
    Interval inter3 = Intervals.openClosed(4, 6, comparator);

    assertEquals(Intervals.closed(4, 6, comparator), inter.span(inter));
    assertEquals(Intervals.closed(4, 6, comparator), inter.span(inter1));
    assertEquals(Intervals.closed(4, 6, comparator), inter.span(inter2));
    assertEquals(Intervals.closed(4, 6, comparator), inter.span(inter3));
    assertEquals(inter, inter.span(empty));

    assertEquals(Intervals.closed(4, 6, comparator), inter1.span(inter));
    assertEquals(Intervals.open(4, 6, comparator), inter1.span(inter1));
    assertEquals(Intervals.closedOpen(4, 6, comparator), inter1.span(inter2));
    assertEquals(Intervals.openClosed(4, 6, comparator), inter1.span(inter3));
    assertEquals(inter1, inter1.span(empty));

    assertEquals(Intervals.closed(4, 6, comparator), inter2.span(inter));
    assertEquals(Intervals.closedOpen(4, 6, comparator), inter2.span(inter1));
    assertEquals(Intervals.closedOpen(4, 6, comparator), inter2.intersection(inter2));
    assertEquals(Intervals.closed(4, 6, comparator), inter2.span(inter3));
    assertEquals(inter2, inter2.span(empty));

    assertEquals(Intervals.closed(4, 6, comparator), inter3.span(inter));
    assertEquals(Intervals.openClosed(4, 6, comparator), inter3.span(inter1));
    assertEquals(Intervals.closed(4, 6, comparator), inter3.span(inter2));
    assertEquals(Intervals.openClosed(4, 6, comparator), inter3.span(inter3));
    assertEquals(inter3, inter3.span(empty));

    assertEquals(inter, empty.span(inter));
    assertEquals(inter1, empty.span(inter1));
    assertEquals(inter2, empty.span(inter2));
    assertEquals(inter3, empty.span(inter3));
    assertEquals(empty, empty.span(empty));
  }

  @Test
  public void intervalsBoundsTest() {
    Interval inter1 = Intervals.open(10, 20);
    Interval inter2 = Intervals.closed(10, 20);
    Interval inter3 = Intervals.openClosed(10, 20);
    Interval inter4 = Intervals.closedOpen(10, 20);

    assertEquals(BoundType.Open, inter1.lowerBoundType());
    assertEquals(BoundType.Closed, inter2.lowerBoundType());
    assertEquals(BoundType.Open, inter3.lowerBoundType());
    assertEquals(BoundType.Closed, inter4.lowerBoundType());

    assertEquals(BoundType.Open, inter1.upperBoundType());
    assertEquals(BoundType.Closed, inter2.upperBoundType());
    assertEquals(BoundType.Closed, inter3.upperBoundType());
    assertEquals(BoundType.Open, inter4.upperBoundType());

    assertEquals(10,inter1.lowerBound());
    assertEquals(10,inter2.lowerBound());
    assertEquals(10,inter3.lowerBound());
    assertEquals(10,inter4.lowerBound());

    assertEquals(20,inter1.upperBound());
    assertEquals(20,inter2.upperBound());
    assertEquals(20,inter3.upperBound());
    assertEquals(20,inter4.upperBound());
  }

  @Test
  public void intervalsComparatorTest() {
    Interval inter1 = Intervals.open(10, 20);
    Interval inter2 = Intervals.closed(10, 20);
    Interval inter3 = Intervals.openClosed(10, 20);
    Interval inter4 = Intervals.closedOpen(10, 20);

    Interval inter5 = Intervals.open(20, 10,Comparator.reverseOrder());
    Interval inter6 = Intervals.closed(20, 10,Comparator.reverseOrder());
    Interval inter7 = Intervals.openClosed(20, 10,Comparator.reverseOrder());
    Interval inter8 = Intervals.closedOpen(20, 10,Comparator.reverseOrder());

    assertEquals(Comparator.naturalOrder(), inter1.getComparator());
    assertEquals(Comparator.naturalOrder(), inter2.getComparator());
    assertEquals(Comparator.naturalOrder(), inter3.getComparator());
    assertEquals(Comparator.naturalOrder(), inter4.getComparator());
    assertEquals(Comparator.reverseOrder(), inter5.getComparator());
    assertEquals(Comparator.reverseOrder(), inter6.getComparator());
    assertEquals(Comparator.reverseOrder(), inter7.getComparator());
    assertEquals(Comparator.reverseOrder(), inter8.getComparator());
  }

  @Test
  public void EmptyContainsTest() {
    Interval empty = Intervals.empty();

    assertFalse(empty.contains(5));
  }

  @Test(expected = IllegalStateException.class)
  public void EmptyLowerBoundTest() {
    Interval empty = Intervals.empty();

    empty.lowerBound();
  }

  @Test(expected = IllegalStateException.class)
  public void EmptyUpperBoundTest() {
    Interval empty = Intervals.empty();

    empty.upperBound();
  }

  @Test
  public void EmptyBoundTypesTest() {
    Interval empty = Intervals.empty();

    assertEquals(BoundType.Closed, empty.lowerBoundType());
    assertEquals(BoundType.Open, empty.upperBoundType());
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullInputsTests1() {
    Intervals.open(15,10);
  }

  @Test(expected = NullPointerException.class)
  public void nullInputsTests2() {
    Intervals.open(null, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullInputsTests3() {
    Intervals.open(5,10).contains(null);
  }

  @Test
  public void reverseOrderTests() {
    Interval inter = Intervals.open(16,10,Comparator.reverseOrder());
    Interval inter1 = Intervals.closed(15,11,Comparator.reverseOrder());

    assertEquals(16, inter.lowerBound());
    assertEquals(10, inter.upperBound());

    assertEquals("(16,10)", inter.toString());
    assertEquals("[15,11]", inter1.toString());

    assertEquals(true, inter.includes(inter1));
    assertEquals(false, inter1.includes(inter));

    assertEquals(Intervals.closed(15,11,Comparator.reverseOrder()), inter.intersection(inter1));
    assertEquals(Intervals.closed(15,11,Comparator.reverseOrder()), inter1.intersection(inter));

    assertEquals(Intervals.open(16,10,Comparator.reverseOrder()), inter.span(inter1));
    assertEquals(Intervals.open(16,10,Comparator.reverseOrder()), inter1.span(inter));
  }

  @Test
  public void stringIntervalsTest() {
    Interval inter = Intervals.open("cat", "dog");
    Interval inter1 = Intervals.closed("car", "dump");

    assertEquals("cat", inter.lowerBound());
    assertEquals("dog", inter.upperBound());

    assertEquals("(cat,dog)", inter.toString());
    assertEquals("[car,dump]", inter1.toString());

    assertEquals(false, inter.includes(inter1));
    assertEquals(true, inter1.includes(inter));

    assertEquals(Intervals.open("cat", "dog"), inter.intersection(inter1));
    assertEquals(Intervals.open("cat", "dog"), inter1.intersection(inter));

    assertEquals(Intervals.closed("car", "dump"), inter.span(inter1));
    assertEquals(Intervals.closed("car", "dump"), inter1.span(inter));
  }

  @Test
  public void Test() {
    Interval inter = Intervals.open(5,5);

  }
}
