package junit.tutorial.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TimeOutExampleTest {
  @Rule
  public Timeout timeout = new Timeout(100);

  @Test
  public void 長い時間がかかるかもしれないテスト() throws Exception {
    new junit.tutorial.Timeout().doLongTask();
  }

}
