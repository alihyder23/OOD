import org.junit.Test;

import static org.junit.Assert.*;

public class WebpageTest {
  Publication EZPass = new Webpage("E-ZPass is a life-saver (literally) [Blog post]",
                                   "http://freakonomics.blogs.nytimes.com/2010/10/29/e-zpass-is-a-life-saver-literally/",
                                   "2010, October 29");

  @Test
  public void testCiteApa() {
    assertEquals("E-ZPass is a life-saver (literally) [Blog post]. (2010, October 29). " +
                 "Retrieved from http://freakonomics.blogs.nytimes.com/2010/10/29/e-zpass-is-a-life-saver-literally/",
                 EZPass.citeApa());
  }

  @Test
  public void testCiteMla() {
    assertEquals("E-ZPass is a life-saver (literally) [Blog post]. 2010, October 29. " +
                 "<http://freakonomics.blogs.nytimes.com/2010/10/29/e-zpass-is-a-life-saver-literally/>.",
                 EZPass.citeMla());
  }
}
