/**
 * Provides functionality common to both representational subclasses,
 * including comparisons, hashing, addition, subtraction, and the ability
 * to construct new durations of the same class as a given instance.
 */
abstract class AbstractDuration implements Duration {
  protected static final int SECS_IN_DAY = 24 * 60 * 60;

  /**
   * Constructs a new duration having the same class as {@code this}.
   *
   * @param seconds length of the new duration in seconds (non-negative)
   * @return the new duration
   * @throws IllegalArgumentException {@code seconds} is negative
   */
  protected abstract AbstractDuration fromSeconds(long seconds);

  /**
   * Constructs a new duration having the same class as {@code this}.
   *
   * @param days the days component of the new duration (non-negative)
   * @param hours the hours component of the new duration (non-negative)
   * @param minutes the minutes component of the new duration (non-negative)
   * @param seconds the seconds component of the new duration (non-negative)
   * @return the new duration
   * @throws IllegalArgumentException if any argument is negative
   */
  protected abstract AbstractDuration fromDHMS(long days, int hours,
                                               int minutes, int seconds);

  /**
   * Returns the sum of two durations. The result will have the same dynamic
   * class as {@code this}.
   *
   * @param other the duration to add to {@code this}
   * @return the sum of the durations
   */
  @Override
  public Duration plus(Duration other) {
    long result = inSeconds() + other.inSeconds();

    if (result < 0) {
      throw new RuntimeException("Duration overflow");
    }

    return fromSeconds(result);
  }

  /**
   * Returns the difference of two durations. Returns the zero duration rather
   * than negative. The result will have the same dynamic class as
   * {@code this}.
   *
   * @param other the duration to subtract from {@code this}
   * @return the difference of the durations
   */
  @Override
  public Duration minus(Duration other) {
    long result = inSeconds() - other.inSeconds();
    return fromSeconds(result < 0 ? 0 : result);
  }

  @Override
  public int compareTo(Duration other) {
    return Long.compare(inSeconds(), other.inSeconds());
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Duration)) return false;
    return inSeconds() == ((Duration) other).inSeconds();
  }

  @Override
  public int hashCode() {
    return (int) (inSeconds() ^ (inSeconds() >>> 32));
  }

  @Override
  public String format(String template){

    String temp = template;

    StringBuilder output = new StringBuilder(temp);

    int i = 0;

    while(i < output.length()-1){
      if (output.charAt(i)=='%')
      {
        i++;

        if (output.charAt(i)=='t')
        {
          output.delete(i-1,i+1);
          output.insert(i-1, this.inSeconds());
        }

        if (output.charAt(i)=='d')
        {
          output.delete(i-1,i+1);
          output.insert(i-1, this.getDaysComponent());
        }

        if (output.charAt(i)=='h')
        {
          output.delete(i-1,i+1);
          output.insert(i-1, this.getHoursComponent());
        }

        if (output.charAt(i)=='H')
        {
          output.delete(i-1,i+1);

          if (this.getHoursComponent() < 10)
          {
            output.insert(i-1, '0');
            output.insert(i, this.getHoursComponent());
          }
          else
          {
            output.insert(i-1, this.getHoursComponent());
          }
        }

        if (output.charAt(i)=='m')
        {
          output.delete(i-1,i+1);
          output.insert(i-1, this.getMinutesComponent());
        }

        if (output.charAt(i)=='M')
        {

          output.delete(i-1,i+1);

          if (this.getMinutesComponent() < 10)
          {
            output.insert(i-1, '0');
            output.insert(i, this.getMinutesComponent());
          }
          else
          {
            output.insert(i-1, this.getMinutesComponent());
          }
        }

        if (output.charAt(i)=='s') {
          output.delete(i-1,i+1);
          output.insert(i-1, this.getSecondsComponent());
        }

        if (output.charAt(i)=='S')
        {
          output.delete(i-1,i+1);

          if (this.getSecondsComponent() < 10)
          {
            output.insert(i-1, '0');
            output.insert(i, this.getSecondsComponent());
          }
          else
          {
            output.insert(i-1, this.getSecondsComponent());
          }
        }

        if (output.charAt(i)=='%')
        {
          output.delete(i-1,i+1);
          output.insert(i-1, '%');
        }
      }
      i++;
    }

    return (output.toString());
  }
}
