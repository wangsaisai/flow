package com.bamboo.flow;

import java.util.function.Function;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FlowTest {

  @Test
  public void successFlow() {
    Node<Integer, Integer> n1 = Node.<Integer, Integer>builder()
        .name("n1-add1")
        .input(0)
        .task(i -> i + 1)
        .build();

    Node<Integer, Integer> n2 = Node.<Integer, Integer>builder()
        .name("n2-add2")
        .task(i -> i + 2)
        .build();

    Node<Integer, Integer> n3 = Node.<Integer, Integer>builder()
        .name("n3-add3")
        .task(i -> i + 3)
        .build();

    Flow flow = Flow.of("add-test", n1, n2, n3);
    flow.linkNode(n1, n2, Function.identity());
    flow.linkNode(n2, n3, Function.identity());

    flow.run();
    Assert.assertEquals(n3.getOutput().intValue(), 1 + 2 + 3);
  }

  @Test
  public void errorFlow() {
    Node<String, String> n1 = Node.<String, String>builder()
        .name("n1")
        .task(s -> {
          System.out.println("doing task 1");
          return s;
        })
        .rollback(s -> System.out.println("rollback task 1"))
        .build();

    Node<String, String> n2 = Node.<String, String>builder()
        .name("n2")
        .retry(3)
        .task(s -> {
          System.out.println("doing task 2");
          // will throw exception here
          int v = 1/0;
          return s;
        })
        .rollback(s -> System.out.println("rollback task 2"))
        .build();

    Flow flow = Flow.of("divide-test", n1, n2);
    boolean result = flow.run();

    Assert.assertFalse(result);
    Assert.assertTrue(flow.getError() instanceof ArithmeticException);
    Assert.assertEquals(flow.getError().getMessage(), "/ by zero");
  }


}
