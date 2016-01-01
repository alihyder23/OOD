/**
 * Represents bibliographic information for Webpages.
 */
public class Webpage implements Publication {
  private final String title, url, retrieved;

  /**
   * Constructs a {@code Webpage} object.
   *
   * @param title     the title of the Webpage
   * @param url    the url of the Webpage
   * @param retrieved the date the website was viewed in your browser (YYYY, Month Day)
   */
  public Webpage(String title, String url, String retrieved)
  {
    this.title = title;
    this.url = url;
    this.retrieved = retrieved;
  }

  @Override
  public String citeApa() {
    return title + ". (" + retrieved + "). Retrieved from " + url;
  }

  @Override
  public String citeMla() {
    return title + ". " + retrieved + ". <" +
           url + ">.";
  }
}

