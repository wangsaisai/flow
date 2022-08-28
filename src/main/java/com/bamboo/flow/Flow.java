package com.bamboo.flow;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/** 线性工作流，包含若个个节点，各个节点顺序执行。若某节点执行失败，则从当前节点开始，依次向前回滚 */
@Slf4j
public class Flow {

  /** 流程名，用于记录log */
  @Getter private final String name;

  /** 节点列表 */
  private final List<Node> nodes;

  /** 节点关联 */
  private final Map<Node, List<Runnable>> links;

  @Getter private Exception error;

  public Flow(String name) {
    this.name = name;
    this.nodes = new ArrayList<>();
    this.links = new HashMap<>();
  }

  public void addNode(Node node) {
    nodes.add(node);
    node.setFlow(this);
  }

  public static Flow of(String name, Node... nodes) {
    Flow flow = new Flow(name);
    for (Node node : nodes) {
      flow.addNode(node);
    }
    return flow;
  }

  /**
   * 连接2个node，将node n1的输出，进行转换，作为node n2的输入 在node n1执行成功后，对node n2进行赋值
   *
   * @param n1 Node n1
   * @param n2 Node n2
   * @param converter 转换函数
   * @param <FO> n1 输出类型
   * @param <SI> n2 输入类型
   */
  public <FO, SI> void linkNode(Node<?, FO> n1, Node<SI, ?> n2, Function<FO, SI> converter) {
    links.putIfAbsent(n1, new ArrayList<>());
    links.get(n1).add(() -> n2.setInput(converter.apply(n1.getOutput())));
  }

  /**
   * 执行流程
   *
   * @return 只有所有节点全部执行成功，才认为流程执行成功
   */
  public boolean run() {
    log.info("Flow:" + name + " start run");
    for (int i = 0; i < nodes.size(); i++) {
      Node node = nodes.get(i);
      boolean result = node.exec();
      if (result) {
        links.getOrDefault(node, Collections.emptyList()).forEach(Runnable::run);
      } else {
        this.error = node.getError();
        // 节点执行失败，从当前节点开始，依次向前回滚
        log.warn("Flow:" + name + " run failed, will rollback");
        for (int j = i; j >= 0; j--) {
          Node nodex = nodes.get(j);
          nodex.rollback();
        }
        log.warn("Flow:" + name + " finish rollback");
        return false;
      }
    }

    log.info("Flow:" + name + " run success");
    return true;
  }
}
