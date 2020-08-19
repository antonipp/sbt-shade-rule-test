lazy val root = (project in file("."))
  .settings(
    organization in ThisBuild := "com.example",
    scalaVersion in ThisBuild := "2.11.12",
    version      in ThisBuild := "1.0.0",
    name := "ShadeRuleTest",
    libraryDependencies ++= Seq(
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
    ),
    logLevel in assembly := Level.Debug,
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
    ),
    assemblyMergeStrategy in assembly := {
        case PathList("META-INF", xs @ _*) => MergeStrategy.discard
        case x                             => MergeStrategy.first
    },
  )
