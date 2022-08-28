# 工作流执行引擎
> Flow 代表一个工作流 <p>
> Node 代表一个工作节点 <p>
> 不支持DAG，任务线性执行 <p>
> 支持node失败重试 <p>
> 若中间执行失败，从当前节点开始，依次向前回滚 <p>

# Example
> see test case : [FlowTest.java](src/test/java/com/bamboo/flow/FlowTest.java)

### FlowTest.successFlow()
> 求和 0 + 1 + 2 + 3

##### 执行结果
```
 INFO [main] - Flow:add-test start run
 INFO [main] - Flow:add-test Node:n1-add1 start exec
 INFO [main] - Flow:add-test Node:n1-add1 exec success
 INFO [main] - Flow:add-test Node:n2-add2 start exec
 INFO [main] - Flow:add-test Node:n2-add2 exec success
 INFO [main] - Flow:add-test Node:n3-add3 start exec
 INFO [main] - Flow:add-test Node:n3-add3 exec success
 INFO [main] - Flow:add-test run success
```

### FlowTest.errorFlow()执行结果
> 模拟出错场景（除0异常）

##### 执行结果
```
 INFO [main] - Flow:divide-test start run
 INFO [main] - Flow:divide-test Node:n1 start exec
doing task 1
 INFO [main] - Flow:divide-test Node:n1 exec success
 INFO [main] - Flow:divide-test Node:n2 start exec
doing task 2
ERROR [main] - Flow:divide-test Node:n2 exec failed, retry current/total : 1/4
java.lang.ArithmeticException: / by zero
	at com.bamboo.flow.FlowTest.lambda$errorFlow$5(FlowTest.java:52)
	at com.bamboo.flow.Node.exec(Node.java:56)
	at com.bamboo.flow.Flow.run(Flow.java:70)
	at com.bamboo.flow.FlowTest.errorFlow(FlowTest.java:59)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:85)
	at org.testng.internal.Invoker.invokeMethod(Invoker.java:659)
	at org.testng.internal.Invoker.invokeTestMethod(Invoker.java:845)
	at org.testng.internal.Invoker.invokeTestMethods(Invoker.java:1153)
	at org.testng.internal.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:125)
	at org.testng.internal.TestMethodWorker.run(TestMethodWorker.java:108)
	at org.testng.TestRunner.privateRun(TestRunner.java:771)
	at org.testng.TestRunner.run(TestRunner.java:621)
	at org.testng.SuiteRunner.runTest(SuiteRunner.java:357)
	at org.testng.SuiteRunner.runSequentially(SuiteRunner.java:352)
	at org.testng.SuiteRunner.privateRun(SuiteRunner.java:310)
	at org.testng.SuiteRunner.run(SuiteRunner.java:259)
	at org.testng.SuiteRunnerWorker.runSuite(SuiteRunnerWorker.java:52)
	at org.testng.SuiteRunnerWorker.run(SuiteRunnerWorker.java:86)
	at org.testng.TestNG.runSuitesSequentially(TestNG.java:1199)
	at org.testng.TestNG.runSuitesLocally(TestNG.java:1124)
	at org.testng.TestNG.run(TestNG.java:1032)
	at com.intellij.rt.testng.IDEARemoteTestNG.run(IDEARemoteTestNG.java:66)
	at com.intellij.rt.testng.RemoteTestNGStarter.main(RemoteTestNGStarter.java:109)
doing task 2
ERROR [main] - Flow:divide-test Node:n2 exec failed, retry current/total : 2/4
java.lang.ArithmeticException: / by zero
	at com.bamboo.flow.FlowTest.lambda$errorFlow$5(FlowTest.java:52)
	at com.bamboo.flow.Node.exec(Node.java:56)
	at com.bamboo.flow.Flow.run(Flow.java:70)
	at com.bamboo.flow.FlowTest.errorFlow(FlowTest.java:59)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:85)
	at org.testng.internal.Invoker.invokeMethod(Invoker.java:659)
	at org.testng.internal.Invoker.invokeTestMethod(Invoker.java:845)
	at org.testng.internal.Invoker.invokeTestMethods(Invoker.java:1153)
	at org.testng.internal.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:125)
	at org.testng.internal.TestMethodWorker.run(TestMethodWorker.java:108)
	at org.testng.TestRunner.privateRun(TestRunner.java:771)
	at org.testng.TestRunner.run(TestRunner.java:621)
	at org.testng.SuiteRunner.runTest(SuiteRunner.java:357)
	at org.testng.SuiteRunner.runSequentially(SuiteRunner.java:352)
	at org.testng.SuiteRunner.privateRun(SuiteRunner.java:310)
	at org.testng.SuiteRunner.run(SuiteRunner.java:259)
	at org.testng.SuiteRunnerWorker.runSuite(SuiteRunnerWorker.java:52)
	at org.testng.SuiteRunnerWorker.run(SuiteRunnerWorker.java:86)
	at org.testng.TestNG.runSuitesSequentially(TestNG.java:1199)
	at org.testng.TestNG.runSuitesLocally(TestNG.java:1124)
	at org.testng.TestNG.run(TestNG.java:1032)
	at com.intellij.rt.testng.IDEARemoteTestNG.run(IDEARemoteTestNG.java:66)
	at com.intellij.rt.testng.RemoteTestNGStarter.main(RemoteTestNGStarter.java:109)
doing task 2
ERROR [main] - Flow:divide-test Node:n2 exec failed, retry current/total : 3/4
java.lang.ArithmeticException: / by zero
	at com.bamboo.flow.FlowTest.lambda$errorFlow$5(FlowTest.java:52)
	at com.bamboo.flow.Node.exec(Node.java:56)
	at com.bamboo.flow.Flow.run(Flow.java:70)
	at com.bamboo.flow.FlowTest.errorFlow(FlowTest.java:59)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:85)
	at org.testng.internal.Invoker.invokeMethod(Invoker.java:659)
	at org.testng.internal.Invoker.invokeTestMethod(Invoker.java:845)
	at org.testng.internal.Invoker.invokeTestMethods(Invoker.java:1153)
	at org.testng.internal.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:125)
	at org.testng.internal.TestMethodWorker.run(TestMethodWorker.java:108)
	at org.testng.TestRunner.privateRun(TestRunner.java:771)
	at org.testng.TestRunner.run(TestRunner.java:621)
	at org.testng.SuiteRunner.runTest(SuiteRunner.java:357)
	at org.testng.SuiteRunner.runSequentially(SuiteRunner.java:352)
	at org.testng.SuiteRunner.privateRun(SuiteRunner.java:310)
	at org.testng.SuiteRunner.run(SuiteRunner.java:259)
	at org.testng.SuiteRunnerWorker.runSuite(SuiteRunnerWorker.java:52)
	at org.testng.SuiteRunnerWorker.run(SuiteRunnerWorker.java:86)
	at org.testng.TestNG.runSuitesSequentially(TestNG.java:1199)
	at org.testng.TestNG.runSuitesLocally(TestNG.java:1124)
	at org.testng.TestNG.run(TestNG.java:1032)
	at com.intellij.rt.testng.IDEARemoteTestNG.run(IDEARemoteTestNG.java:66)
	at com.intellij.rt.testng.RemoteTestNGStarter.main(RemoteTestNGStarter.java:109)
doing task 2
ERROR [main] - Flow:divide-test Node:n2 exec failed, retry current/total : 4/4
java.lang.ArithmeticException: / by zero
	at com.bamboo.flow.FlowTest.lambda$errorFlow$5(FlowTest.java:52)
	at com.bamboo.flow.Node.exec(Node.java:56)
	at com.bamboo.flow.Flow.run(Flow.java:70)
	at com.bamboo.flow.FlowTest.errorFlow(FlowTest.java:59)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:85)
	at org.testng.internal.Invoker.invokeMethod(Invoker.java:659)
	at org.testng.internal.Invoker.invokeTestMethod(Invoker.java:845)
	at org.testng.internal.Invoker.invokeTestMethods(Invoker.java:1153)
	at org.testng.internal.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:125)
	at org.testng.internal.TestMethodWorker.run(TestMethodWorker.java:108)
	at org.testng.TestRunner.privateRun(TestRunner.java:771)
	at org.testng.TestRunner.run(TestRunner.java:621)
	at org.testng.SuiteRunner.runTest(SuiteRunner.java:357)
	at org.testng.SuiteRunner.runSequentially(SuiteRunner.java:352)
	at org.testng.SuiteRunner.privateRun(SuiteRunner.java:310)
	at org.testng.SuiteRunner.run(SuiteRunner.java:259)
	at org.testng.SuiteRunnerWorker.runSuite(SuiteRunnerWorker.java:52)
	at org.testng.SuiteRunnerWorker.run(SuiteRunnerWorker.java:86)
	at org.testng.TestNG.runSuitesSequentially(TestNG.java:1199)
	at org.testng.TestNG.runSuitesLocally(TestNG.java:1124)
	at org.testng.TestNG.run(TestNG.java:1032)
	at com.intellij.rt.testng.IDEARemoteTestNG.run(IDEARemoteTestNG.java:66)
	at com.intellij.rt.testng.RemoteTestNGStarter.main(RemoteTestNGStarter.java:109)
 WARN [main] - Flow:divide-test run failed, will rollback
 INFO [main] - Flow:divide-test Node:n2 start rollback
rollback task 2
 INFO [main] - Flow:divide-test Node:n2 rollback success
 INFO [main] - Flow:divide-test Node:n1 start rollback
rollback task 1
 INFO [main] - Flow:divide-test Node:n1 rollback success
 WARN [main] - Flow:divide-test finish rollback
```