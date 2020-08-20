## Base case (inAll)
```
    assemblyShadeRules in assembly ++= Seq(
        ShadeRule.rename("io.netty.**" -> "repackaged.io.netty.@1")
        .inAll
    )
```

```
$ sbt clean assembly
# Inspect the JAR contents
# Netty classes have been renamed
$ jar tf target/scala-2.11/ShadeRuleTest-assembly-1.0.0.jar | grep repackaged
repackaged/
repackaged/io/
repackaged/io/netty/
repackaged/io/netty/bootstrap/
repackaged/io/netty/buffer/
repackaged/io/netty/buffer/search/
repackaged/io/netty/channel/
repackaged/io/netty/channel/embedded/
repackaged/io/netty/channel/epoll/
repackaged/io/netty/channel/group/
...

# Extract the JAR to a temporary directory with 'jar xf'
# Inspect a class from the Azure library
# It uses repackaged.io.netty!
$ javap com/azure/core/http/netty/NettyAsyncHttpClientBuilder.class | grep repackaged
  public com.azure.core.http.netty.NettyAsyncHttpClientBuilder nioEventLoopGroup(repackaged.io.netty.channel.nio.NioEventLoopGroup);
  public com.azure.core.http.netty.NettyAsyncHttpClientBuilder eventLoopGroup(repackaged.io.netty.channel.EventLoopGroup);
```

## Test 1 (inLibrary - "azure-storage-blob" only)
```
    assemblyShadeRules in assembly ++= Seq(
        ShadeRule.rename("io.netty.**" -> "repackaged.io.netty.@1")
            .inLibrary("com.azure" % "azure-storage-blob" % "12.8.0"),
    ),
```

```
$ sbt clean assembly
# Inspect the JAR contents
$ jar tf target/scala-2.11/ShadeRuleTest-assembly-1.0.0.jar | grep repackaged
# Nothing here

# Extract the JAR to a temporary directory with 'jar xf'
# Inspect a class from the Azure library
$ javap com/azure/core/http/netty/NettyAsyncHttpClientBuilder.class | grep repackaged
# Nothing here
```

## Test 2 (inLibrary - "azure-storage-blob" and "netty")
```
    assemblyShadeRules in assembly ++= Seq(
        ShadeRule.rename("io.netty.**" -> "repackaged.io.netty.@1")
        .inLibrary(
            "com.azure" % "azure-storage-blob" % "12.8.0",
            "io.netty" % "netty-buffer" % "4.1.51.Final",
            "io.netty" % "netty-codec" % "4.1.51.Final",
            "io.netty" % "netty-codec-http" % "4.1.51.Final",
            "io.netty" % "netty-codec-http2" % "4.1.51.Final",
            "io.netty" % "netty-codec-socks" % "4.1.51.Final",
            "io.netty" % "netty-common" % "4.1.51.Final",
            "io.netty" % "netty-handler" % "4.1.51.Final",
            "io.netty" % "netty-handler-proxy" % "4.1.51.Final",
            "io.netty" % "netty-resolver" % "4.1.51.Final",
            "io.netty" % "netty-tcnative-boringssl-static" % "2.0.31.Final",
            "io.netty" % "netty-transport" % "4.1.51.Final",
            "io.netty" % "netty-transport-native-epoll" % "4.1.51.Final",
            "io.netty" % "netty-transport-native-kqueue" % "4.1.51.Final",
            "io.netty" % "netty-transport-native-unix-common" % "4.1.51.Final"
        )
    )
```

```
$ sbt clean assembly
# Inspect the JAR contents
$ jar tf target/scala-2.11/ShadeRuleTest-assembly-1.0.0.jar | grep repackaged
# Nothing here

# Extract the JAR to a temporary directory with 'jar xf'
# Inspect a class from the Azure library
$ javap com/azure/core/http/netty/NettyAsyncHttpClientBuilder.class | grep repackaged
# Nothing here
```

## Test 3 (inLibrary + inProject - "azure-storage-blob" only)
```
    assemblyShadeRules in assembly ++= Seq(
        ShadeRule.rename("io.netty.**" -> "repackaged.io.netty.@1")
        .inLibrary(
            "com.azure" % "azure-storage-blob" % "12.8.0"
        )
        .inProject
    )
```

```
$ sbt clean assembly
# Inspect the JAR contents
$ jar tf target/scala-2.11/ShadeRuleTest-assembly-1.0.0.jar | grep repackaged
# Nothing here

# Extract the JAR to a temporary directory with 'jar xf'
# Inspect a class from the Azure library
$ javap com/azure/core/http/netty/NettyAsyncHttpClientBuilder.class | grep repackaged
# Nothing here
```

## Test 4 (inLibrary + inProject - "azure-storage-blob" and "netty")
```
    assemblyShadeRules in assembly ++= Seq(
        ShadeRule.rename("io.netty.**" -> "repackaged.io.netty.@1")
        .inLibrary(
            "com.azure" % "azure-storage-blob" % "12.8.0",
            "io.netty" % "netty-buffer" % "4.1.51.Final",
            "io.netty" % "netty-codec" % "4.1.51.Final",
            "io.netty" % "netty-codec-http" % "4.1.51.Final",
            "io.netty" % "netty-codec-http2" % "4.1.51.Final",
            "io.netty" % "netty-codec-socks" % "4.1.51.Final",
            "io.netty" % "netty-common" % "4.1.51.Final",
            "io.netty" % "netty-handler" % "4.1.51.Final",
            "io.netty" % "netty-handler-proxy" % "4.1.51.Final",
            "io.netty" % "netty-resolver" % "4.1.51.Final",
            "io.netty" % "netty-tcnative-boringssl-static" % "2.0.31.Final",
            "io.netty" % "netty-transport" % "4.1.51.Final",
            "io.netty" % "netty-transport-native-epoll" % "4.1.51.Final",
            "io.netty" % "netty-transport-native-kqueue" % "4.1.51.Final",
            "io.netty" % "netty-transport-native-unix-common" % "4.1.51.Final"
        )
        .inProject
    )
```

```
$ sbt clean assembly
# Inspect the JAR contents
# Netty classes have been renamed!
$ jar tf target/scala-2.11/ShadeRuleTest-assembly-1.0.0.jar | grep repackaged
repackaged/
repackaged/io/
repackaged/io/netty/
repackaged/io/netty/bootstrap/
repackaged/io/netty/buffer/
repackaged/io/netty/buffer/search/
repackaged/io/netty/channel/
repackaged/io/netty/channel/embedded/
repackaged/io/netty/channel/epoll/
repackaged/io/netty/channel/group/
...

# Extract the JAR to a temporary directory with 'jar xf'
# Inspect a class from the Azure library
$ javap com/azure/core/http/netty/NettyAsyncHttpClientBuilder.class | grep repackaged
# Nothing here

# References in the Azure library have not been updated!
$ javap com/azure/core/http/netty/NettyAsyncHttpClientBuilder.class | grep netty
  public com.azure.core.http.netty.NettyAsyncHttpClientBuilder nioEventLoopGroup(io.netty.channel.nio.NioEventLoopGroup);
  public com.azure.core.http.netty.NettyAsyncHttpClientBuilder eventLoopGroup(io.netty.channel.EventLoopGroup);
```