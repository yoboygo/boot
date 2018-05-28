# boot2尝试笔记
------ 
### 未解决的问题
> ##### 1、当遇到异常退出时，`MySQL`服务也会自动关闭
-  ###### `Hikari`连接池`destroy`时会调用`datasourceShutdown`。将连接数据库的用户取消shutdown权限即可。
> ##### 2、正常启动后，访问时会报一个莫名其妙的`NIO`异常   
- ###### 已经不报错了，原因未知
```
2018-05-27 17:27:59.953 DEBUG 13608 --- [nio-8091-exec-5] o.a.tomcat.util.net.SocketWrapperBase    : Socket: [org.apache.tomcat.util.net.NioEndpoint$NioSocketWrapper@493d2619:org.apache.tomcat.util.net.NioChannel@5e951eb3:java.nio.channels.SocketChannel[connected local=/0:0:0:0:0:0:0:1:8091 remote=/0:0:0:0:0:0:0:1:54025]], Read from buffer: [0]
2018-05-27 17:27:59.954 DEBUG 13608 --- [nio-8091-exec-5] o.apache.coyote.http11.Http11Processor   : Error parsing HTTP request header

java.io.EOFException: null
	at org.apache.tomcat.util.net.NioEndpoint$NioSocketWrapper.fillReadBuffer(NioEndpoint.java:1259) ~[tomcat-embed-core-8.5.31.jar:8.5.31]
	at org.apache.tomcat.util.net.NioEndpoint$NioSocketWrapper.read(NioEndpoint.java:1193) ~[tomcat-embed-core-8.5.31.jar:8.5.31]
	at org.apache.coyote.http11.Http11InputBuffer.fill(Http11InputBuffer.java:725) ~[tomcat-embed-core-8.5.31.jar:8.5.31]
	at org.apache.coyote.http11.Http11InputBuffer.parseRequestLine(Http11InputBuffer.java:368) ~[tomcat-embed-core-8.5.31.jar:8.5.31]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:687) ~[tomcat-embed-core-8.5.31.jar:8.5.31]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66) [tomcat-embed-core-8.5.31.jar:8.5.31]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:790) [tomcat-embed-core-8.5.31.jar:8.5.31]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1468) [tomcat-embed-core-8.5.31.jar:8.5.31]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) [tomcat-embed-core-8.5.31.jar:8.5.31]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142) [na:1.8.0_131]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617) [na:1.8.0_131]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) [tomcat-embed-core-8.5.31.jar:8.5.31]
	at java.lang.Thread.run(Thread.java:748) [na:1.8.0_131]
``` 
> ##### 3、登陆成功/失败不能自动跳转
- ###### 因为自定义了`SuccessHandler`，不仅导致不能自动跳转到成功页面，还可能会出现`405`异常，原因未知，暂时去掉了因为自定义了`SuccessHandler`。