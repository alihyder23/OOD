import java.util.LinkedList;

/**
 * A cache policy that evicts the least recently used item.
 */
public class LruPolicy<K> implements ReplacementPolicy<K> {

  private final int capacity;
  private LinkedList<K> data;

  /**
   * Creates a new least-recently-used cache with capacity {@code capacity}.
   *
   * @param cap capacity of the cache
   * @throws IllegalArgumentException {@code cap} < 1
   */
  public LruPolicy(int cap) {
    if(cap < 1) {
      throw new IllegalArgumentException("capacity must be at least 1");
    } else {
      this.capacity = cap;
      this.data = new LinkedList();
    }
  }

  /**
   * Returns the capacity of the cache.
   *
   * @return the capacity of the cache
   */
  public int capacity() {
    return this.capacity;
  }

  /**
   * Returns the number of items currently in the cache.
   *
   * @return the size of the cache
   */
  public int size() {
    return this.data.size();
  }

  /**
   * Informs the policy manager that a particular item is required and 
   * must be brought into the cache if not already there.
   *
   * @param item the required item (non-null)
   * @return the evicted item or null
   */
  public K require(K item) {
    K removed = null;

    if(item == null){
      throw new IllegalArgumentException("item cannot be null");
    }

    if(!this.data.contains(item)){
      if(this.size() == this.capacity()) {
        removed = this.data.remove();
      }
    } else {
      this.data.remove(this.data.indexOf(item));
    }

    this.data.addLast(item);
    return removed;

  }

}
