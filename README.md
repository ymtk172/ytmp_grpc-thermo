# How to use
Generate class files.
```
./mvnw generate-sources
```
Install this package to `~/.m2/repository/` .
```
./mvnw clean compile install
```
Write below on the other POM to use this package.
```
<dependency>
    <groupId>com.yama-lc.ytmp.grpc</groupId>
    <artifactId>thermo</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```