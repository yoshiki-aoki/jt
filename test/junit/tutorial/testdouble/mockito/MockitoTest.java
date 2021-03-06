package junit.tutorial.testdouble.mockito;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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

  @Test(expected = RuntimeException.class)
  public void void型を返すスタブメソッド() {
    List<String> stub = mock(List.class);
    doThrow(new RuntimeException()).when(stub).clear();
    stub.clear();
  }

  @Test
  public void 任意の整数に対するスタブメソッド() {
    List<String> stub = mock(List.class);
    when(stub.get(anyInt())).thenReturn("Hello");
    assertThat(stub.get(0), is("Hello"));
    assertThat(stub.get(1), is("Hello"));
    assertThat(stub.get(999), is("Hello"));
  }

  @Test
  public void スタブメソッドの検証() {
    List<String> mock = mock(List.class);
    mock.clear();
    mock.add("Hello");
    mock.add("Hello");
    verify(mock).clear();
    verify(mock, times(2)).add("Hello");
    verify(mock, never()).add("World");
  }

  @Test
  public void 部分的なモックオブジェクト() {
    List<String> list = new ArrayList<String>();
    List<String> spy = spy(list);
    when(spy.size()).thenReturn(100);
    spy.add("Hello");

    assertThat(spy.get(0), is("Hello"));
    assertThat(spy.size(), is(100));
  }

  @Test
  public void スパイオブジェクトの作成() {
    List<String> list = new java.util.LinkedList<String>();
    List<String> spy = spy(list);
    doReturn("Mockito").when(spy).get(1);
    spy.add("Hello");
    spy.add("World");
    assertThat(spy.get(0), is("Hello"));
    assertThat(spy.get(1), is("Mockito"));
  }

  @Test
  public void Mockitoのspyを使ったテスト() throws Exception {
    // SetUp
    SpyExample sut = new SpyExample();
    Logger spy = spy(sut.logger);
    final StringBuilder infoLog = new StringBuilder();
    doAnswer(new Answer<Void>() {
      @Override
      public Void answer(InvocationOnMock invocation) throws Throwable {
        infoLog.append(invocation.getArguments()[0]);
        invocation.callRealMethod();
        return null;
      }
    }).when(spy).info(anyString());
    sut.logger = spy;
    // Exercise
    sut.doSomething();
    // Verify
    assertThat(infoLog.toString(), is("doSomething"));
  }
}
