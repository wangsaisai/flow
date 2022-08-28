package com.bamboo.flow;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 代表流程图中的一个节点，最小执行单位
 *
 * @param <I> 节点输入参数类型
 * @param <O> 节点输出参数类型
 */
@Getter
@Setter
@Builder
@Slf4j
public class Node<I, O> {

  /** 节点名，用于记录log */
  private String name;

  /** 所属流程 */
  private Flow flow;

  /** 节点输入 */
  private I input;

  /** 节点输出 */
  private O output;

  /** 重试次数。默认为0，即无重试，只执行一次 */
  private int retry = 0;

  /** 执行任务 */
  private Function<I, O> task;

  /** 回滚任务。若为null，则不回滚；若非null，执行失败后回滚 */
  private Consumer<I> rollback;

  /** 记录执行失败原因 */
  private Exception error;

  /**
   * 执行节点任务
   *
   * @return 任务执行结果：成功或失败
   */
  public boolean exec() {
    log.info(getQualifiedName() + " start exec");
    for (int i = 0; i < retry + 1; i++) {
      try {
        output = task.apply(input);
        log.info(getQualifiedName() + " exec success");
        return true;
      } catch (Exception e) {
        log.error(
            String.format(
                "%s exec failed, retry current/total : %d/%d",
                getQualifiedName(), i + 1, retry + 1),
            e);
        this.error = e;
      }
    }

    return false;
  }

  /** 执行回滚任务。回滚任务异常，不进行重试 */
  public void rollback() {
    if (rollback != null) {
      log.info(getQualifiedName() + " start rollback");
      try {
        rollback.accept(input);
        log.info(getQualifiedName() + " rollback success");
      } catch (Exception e) {
        log.error(getQualifiedName() + " rollback failed", e);
      }
    }
  }

  private String getQualifiedName() {
    return String.format("Flow:%s Node:%s", flow.getName(), name);
  }
}
