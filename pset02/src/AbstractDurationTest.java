import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Defines tests for {@link AbstractDuration}s without knowing or caring which
 * concrete implementation is being tested. Subclass this and override the
 * {@link #fromSeconds(long)} factory method to return instances of the class
 * to test.
 */
abstract class AbstractDurationTest {
  /**
   * Helps by constructing durations of the appropriate class.
   *
   * @param s duration in seconds
   * @return duration of the appropriate class
   * @throws IllegalArgumentException {@code s} is negative
   */
  protected abstract AbstractDuration fromSeconds(long s);

  /**
   * Helps by constructing durations of the appropriate class.
   *
   * @param d days component of duration
   * @param h hours component of duration
   * @param m minutes component of duration
   * @param s seconds component of duration
   * @return duration of the appropriate class
   * @throws IllegalArgumentException if any argument is negative
   */
  private Duration fromDHMS(long d, int h, int m, int s) {
    return fromSeconds(0).fromDHMS(d, h, m, s);
  }

  @Test
  public void zeroIsZero() {
    Duration zero = fromSeconds(0);
    assertEquals(0, zero.inSeconds());
    assertEquals(0, zero.getDaysComponent());
    assertEquals(0, zero.getHoursComponent());
    assertEquals(0, zero.getMinutesComponent());
    assertEquals(0, zero.getSecondsComponent());
  }

  @Test
  public void testFormatExample1() {
    assertEquals("3 days, 4 hours, 0 minutes, and 9 seconds",
                 fromDHMS(3, 4, 0, 9)
                   .format("%d days, %h hours, %m minutes, and %s seconds"));
  }

  @Test
  public void testFormatExample2() {
    assertEquals("04:05:17",
                 fromDHMS(0, 4, 5, 17).format("%H:%M:%S"));
  }

  @Test
  public void testFormatExample3() {
    assertEquals("%:04:05:08",
                 fromDHMS(0, 4, 5, 8).format("%%:%H:%M:%S"));
  }

  @Test
  public void testFormatExample4() {
    assertEquals("14717: total duration",
                 fromDHMS(0, 4, 5, 17).format("%t: total duration"));
  }

  @Test
  public void testFormatExample5() {
    assertEquals("0 days, 04 hours, 5 minutes, and 17 seconds",
                 fromSeconds(14717).format("%d days, %H hours, %m minutes, and %s seconds"));
  }

  @Test
  public void testFormatExample6() {
    assertEquals("04:05:17",
                 fromSeconds(14717)
                     .format("%H:%M:%S"));
  }

  @Test
  public void testFormatExample7() {
    assertEquals("12d09h56m02s",
                 fromDHMS(12,9,56,2)
                     .format("%dd%Hh%Mm%Ss"));
  }

  @Test
  public void testFormatExample8() {
    assertEquals("12d09h56m02s%",
                 fromDHMS(12, 9, 56, 2)
                     .format("%dd%Hh%Mm%Ss%"));
  }

  @Test
  public void testFormatExample9() {
    assertEquals("9",
                 fromDHMS(12, 9, 56, 2)
                     .format("%h"));
  }


  static long s1 = 8049283294L;
  static long s2 = 328375982;
  static long s3111459 = 299699;

  @Test
  public void testGetDays() {
    assertEquals(4, fromDHMS(4, 22, 9, 19).getDaysComponent());
    assertEquals(3, fromSeconds(s3111459).getDaysComponent());
  }

  @Test
  public void testGetHours() {
    assertEquals(22, fromDHMS(4, 22, 9, 19).getHoursComponent());
    assertEquals(11, fromSeconds(s3111459).getHoursComponent());
  }

  @Test
  public void testGetMinutes() {
    assertEquals(9, fromDHMS(4, 22, 9, 19).getMinutesComponent());
    assertEquals(14, fromSeconds(s3111459).getMinutesComponent());
  }

  @Test
  public void testGetSeconds() {
    assertEquals(19, fromDHMS(4, 22, 9, 19).getSecondsComponent());
    assertEquals(59, fromSeconds(s3111459).getSecondsComponent());
  }

  @Test
  public void testPlus() {
    assertEquals(fromSeconds(s2), fromSeconds(0).plus(fromSeconds(s2)));
    assertEquals(fromSeconds(s1), fromSeconds(s1).plus(fromSeconds(0)));
    assertEquals(fromSeconds(s1 + s2), fromSeconds(s1).plus(fromSeconds(s2)));
  }

  @Test
  public void testMinus() {
    assertEquals(fromSeconds(s1), fromSeconds(s1).minus(fromSeconds(0)));
    assertEquals(fromSeconds(s1 - s2), fromSeconds(s1).minus(fromSeconds(s2)));
  }

  @Test
  public void testMinus_lowerBound() {
    assertEquals(fromSeconds(0), fromSeconds(0).minus(fromSeconds(s2)));
    assertEquals(fromSeconds(0), fromSeconds(s2).minus(fromSeconds(s1)));
  }

  @Test
  public void testEquals_packed() {
    assertEquals(fromSeconds(s1), new PackedDuration(s1));
  }

  @Test
  public void testEquals_wide() {
    assertEquals(fromSeconds(s1), new WideDuration(s1));
  }

  @Test
  public void testStandardization() {
    assertEquals(fromDHMS(235, 27, 12, 72), fromDHMS(236, 3, 13, 12));
    assertEquals(fromDHMS(0, 47, 45, 903), fromDHMS(2, 0, 0, 3));
  }
}
