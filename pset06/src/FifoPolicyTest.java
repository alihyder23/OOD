import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeTrue;

public class FifoPolicyTest {

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeCapacity(){
    ReplacementPolicy<Integer> policy = new FifoPolicy(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroCapacity(){
    ReplacementPolicy<Integer> policy = new FifoPolicy(0);
  }

  /**
   * Here's an example of using the LRU policy. The comments on the right
   * show the state of the cache/queue after each {@code require} operation.
   */
  @Test
  public void extendedExampleInteger() {
    ReplacementPolicy<Integer> policy = new FifoPolicy(5);
    assertEquals(5, policy.capacity());
    assertEquals(0, policy.size());

    // Requiring items starts to fill the cache.

    assertNull(policy.require(1));                // 1 _ _ _ _
    assertEquals(1, policy.size());

    assertNull(policy.require(2));                // 1 2 _ _ _
    assertEquals(2, policy.size());

    // Requiring an item that's already in the cache returns null:
    assertNull(policy.require(1));                // 1 2 _ _ _
    assertEquals(2, policy.size());

    assertNull(policy.require(3));                // 1 2 3 _ _
    assertNull(policy.require(4));                // 1 2 3 4 _
    assertEquals(4, policy.size());

    assertNull(policy.require(1));                // 1 2 3 4 _
    assertEquals(4, policy.size());

    assertNull(policy.require(5));                // 1 2 3 4 5
    assertEquals(5, policy.size());

    // Once the cache is full, requiring an item that is absent causes the
    // oldest item to be evicted;

    assertEquals((Integer) 1, policy.require(6)); // 1 2 3 4 5
    assertEquals(5, policy.size());

    assertNull(policy.require(5));                // 2 3 4 5 6
    assertEquals((Integer) 2, policy.require(7)); // 3 4 5 6 7
    assertNull(policy.require(4));                // 3 4 5 6 7
    assertNull(policy.require(5));                // 3 4 5 6 7
    assertEquals((Integer) 3, policy.require(8)); // 4 5 6 7 8
    assertNull(policy.require(5));                // 4 5 6 7 8
    assertEquals((Integer) 4, policy.require(3)); // 5 6 7 8 3
    assertEquals((Integer) 5, policy.require(9)); // 6 7 8 3 9
    assertEquals((Integer) 6, policy.require(0)); // 7 8 3 9 0
    assertEquals((Integer) 7, policy.require(1)); // 8 3 9 0 1
    assertNull(policy.require(8));                // 8 3 9 0 1
    assertNull(policy.require(3));                // 8 3 9 0 1
    assertNull(policy.require(9));                // 8 3 9 0 1
    assertNull(policy.require(0));                // 8 3 9 0 1
    assertNull(policy.require(1));                // 8 3 9 0 1
    assertEquals((Integer) 8, policy.require(2)); // 8 3 9 0 1

    assertEquals(5, policy.capacity());
    assertEquals(5, policy.size());
  }

  @Test
  public void extendedExampleString() {
    ReplacementPolicy<String> policy = new FifoPolicy(5);
    assertEquals(5, policy.capacity());
    assertEquals(0, policy.size());

    // Requiring items starts to fill the cache.

    assertNull(policy.require("one"));                // 1 _ _ _ _
    assertEquals(1, policy.size());

    assertNull(policy.require("two"));                // 1 2 _ _ _
    assertEquals(2, policy.size());

    // Requiring an item that's already in the cache returns null:
    assertNull(policy.require("one"));                // 1 2 _ _ _
    assertEquals(2, policy.size());

    assertNull(policy.require("three"));                // 1 2 3 _ _
    assertNull(policy.require("four"));                // 1 2 3 4 _
    assertEquals(4, policy.size());

    assertNull(policy.require("one"));                // 1 2 3 4 _
    assertEquals(4, policy.size());

    assertNull(policy.require("five"));                // 1 2 3 4 5
    assertEquals(5, policy.size());

    // Once the cache is full, requiring an item that is absent causes the
    // oldest item to be evicted;

    assertEquals((String) "one", policy.require("six")); // 1 2 3 4 5
    assertEquals(5, policy.size());

    assertNull(policy.require("five"));                // 2 3 4 5 6
    assertEquals((String) "two", policy.require("seven")); // 3 4 5 6 7
    assertNull(policy.require("four"));                // 3 4 5 6 7
    assertNull(policy.require("five"));                // 3 4 5 6 7
    assertEquals((String) "three", policy.require("eight")); // 4 5 6 7 8
    assertNull(policy.require("five"));                // 4 5 6 7 8
    assertEquals((String) "four", policy.require("three")); // 5 6 7 8 3
    assertEquals((String) "five", policy.require("nine")); // 6 7 8 3 9
    assertEquals((String) "six", policy.require("zero")); // 7 8 3 9 0
    assertEquals((String) "seven", policy.require("one")); // 8 3 9 0 1
    assertNull(policy.require("eight"));                // 8 3 9 0 1
    assertNull(policy.require("three"));                // 8 3 9 0 1
    assertNull(policy.require("nine"));                // 8 3 9 0 1
    assertNull(policy.require("zero"));                // 8 3 9 0 1
    assertNull(policy.require("one"));                // 8 3 9 0 1
    assertEquals((String) "eight", policy.require("two")); // 8 3 9 0 1

    assertEquals(5, policy.capacity());
    assertEquals(5, policy.size());
  }
}