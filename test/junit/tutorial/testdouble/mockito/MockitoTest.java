package junit.tutorial.testdouble.mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

public class MockitoTest {

  @Test
  public void Mockitoを使ってみる() {
    // モックオブジェクトの作成
    List<String> mock = mock(List.class);
    assertThat(mock.get(0), is(nullValue()));
    assertThat(mock.contains("Hello"), is(false));
  }

  @Test
  public void スタブメソッドの定義() {
    List<String> stub = mock(List.class);   // スタブオブジェクトの作成
    when(stub.get(0)).thenReturn("Hello");  // スタブメソッドの定義
    assertThat(stub.get(0), is("Hello"));   // 検証
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void 例外を送出するスタブメソッド() {
    List<String> stub = mock(List.class);
    when(stub.get(0)).thenReturn("Hello");
    when(stub.get(1)).thenReturn("World");
    when(stub.get(2)).thenThrow(new IndexOutOfBoundsException());
    stub.get(2); // 例外が創出される
  }
}