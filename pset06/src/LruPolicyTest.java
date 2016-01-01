import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeTrue;

public class LruPolicyTest {

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
    ReplacementPolicy<Integer> policy = new LruPolicy(5);
    assertEquals(5, policy.capacity());
    assertEquals(0, policy.size());

    // Requiring items starts to fill the cache.

    assertNull(policy.require(1));                            // 1 _ _ _ _
    assertEquals(1, policy.size());

    assertNull(policy.require(2));                            // 1 2 _ _ _
    assertEquals(2, policy.size());

    // Requiring an item that's already in the cache moves it to the back:
    assertNull(policy.require(1));                            // 2 1 _ _ _
    assertEquals(2, policy.size());

    assertNull(policy.require(3));                            // 2 1 3 _ _
    assertNull(policy.require(4));                            // 2 1 3 4 _
    assertEquals(4, policy.size());

    assertNull(policy.require(1));                            // 2 3 4 1 _
    assertEquals(4, policy.size());

    assertNull(policy.require(5));                            // 2 3 4 1 5
    assertEquals(5, policy.size());

    // Once the cache is full, requiring an item that is absent causes the
    // oldest item to be evicted; requiring an item that is present still
    // moves it to the back.

    assertEquals((Integer) 2, policy.require(6)); // 3 4 1 5 6
    assertEquals(5, policy.size());

    assertNull(policy.require(5));                // 3 4 1 6 5
    assertEquals((Integer) 3, policy.require(7)); // 4 1 6 5 7
    assertNull(policy.require(4));                // 1 6 5 7 4
    assertNull(policy.require(5));                // 1 6 7 4 5
    assertEquals((Integer) 1, policy.require(8)); // 6 7 4 5 8
    assertNull(policy.require(5));                // 6 7 4 8 5
    assertEquals((Integer) 6, policy.require(3)); // 7 4 8 5 3
    assertEquals((Integer) 7, policy.require(9)); // 4 8 5 3 9
    assertEquals((Integer) 4, policy.require(0)); // 8 5 3 9 0
    assertEquals((Integer) 8, policy.require(1)); // 5 3 9 0 1
    assertEquals((Integer) 5, policy.require(2)); // 3 9 0 1 2
    assertEquals((Integer) 3, policy.require(4)); // 9 0 1 2 4
    assertNull(policy.require(9));                // 0 1 2 4 9
    assertNull(policy.require(1));                // 0 2 4 9 1
    assertNull(policy.require(4));                // 0 2 9 1 4
    assertNull(policy.require(1));                // 0 2 9 4 1
    assertNull(policy.require(1));                // 0 2 9 4 1
    assertEquals((Integer) 0, policy.require(5)); // 2 9 4 1 5

    assertEquals(5, policy.capacity());
    assertEquals(5, policy.size());
  }

  @Test
  public void extendedExampleString() {
    ReplacementPolicy<String> policy = new LruPolicy(5);
    assertEquals(5, policy.capacity());
    assertEquals(0, policy.size());

    // Requiring items starts to fill the cache.

    assertNull(policy.require("one"));                            // 1 _ _ _ _
    assertEquals(1, policy.size());

    assertNull(policy.require("two"));                            // 1 2 _ _ _
    assertEquals(2, policy.size());

    // Requiring an item that's already in the cache moves it to the back:
    assertNull(policy.require("one"));                            // 2 1 _ _ _
    assertEquals(2, policy.size());

    assertNull(policy.require("three"));                            // 2 1 3 _ _
    assertNull(policy.require("four"));                            // 2 1 3 4 _
    assertEquals(4, policy.size());

    assertNull(policy.require("one"));                            // 2 3 4 1 _
    assertEquals(4, policy.size());

    assertNull(policy.require("five"));                            // 2 3 4 1 5
    assertEquals(5, policy.size());

    // Once the cache is full, requiring an item that is absent causes the
    // oldest item to be evicted; requiring an item that is present still
    // moves it to the back.

    assertEquals((String) "two", policy.require("six")); // 3 4 1 5 6
    assertEquals(5, policy.size());

    assertNull(policy.require("five"));                // 3 4 1 6 5
    assertEquals((String) "three", policy.require("seven")); // 4 1 6 5 7
    assertNull(policy.require("four"));                // 1 6 5 7 4
    assertNull(policy.require("five"));                // 1 6 7 4 5
    assertEquals((String) "one", policy.require("eight")); // 6 7 4 5 8
    assertNull(policy.require("five"));                // 6 7 4 8 5
    assertEquals((String) "six", policy.require("three")); // 7 4 8 5 3
    assertEquals((String) "seven", policy.require("nine")); // 4 8 5 3 9
    assertEquals((String) "four", policy.require("zero")); // 8 5 3 9 0
    assertEquals((String) "eight", policy.require("one")); // 5 3 9 0 1
    assertEquals((String) "five", policy.require("two")); // 3 9 0 1 2
    assertEquals((String) "three", policy.require("four")); // 9 0 1 2 4
    assertNull(policy.require("nine"));                // 0 1 2 4 9
    assertNull(policy.require("one"));                // 0 2 4 9 1
    assertNull(policy.require("four"));                // 0 2 9 1 4
    assertNull(policy.require("one"));                // 0 2 9 4 1
    assertNull(policy.require("one"));                // 0 2 9 4 1
    assertEquals((String) "zero", policy.require("five")); // 2 9 4 1 5

    assertEquals(5, policy.capacity());
    assertEquals(5, policy.size());
  }
}
