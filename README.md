# UmbraCore
The core plugin for most functionality of the Umbra Factions server. Constantly updated to add new features and gameplay to the server.

## Maven
Our pom.xml is as follows:

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
    
        <groupId>com.umbrafactions</groupId>
        <artifactId>UmbraCore</artifactId>
        <version>0.2.4</version>
    
        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <configuration>
                        <source>1.7</source>
                        <target>1.7</target>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    
        <repositories>
            <repository>
                <id>bukkit-repo</id>
                <url>http://repo.bukkit.org/content/groups/public/</url>
            </repository>
            <repository>
                <id>vault-repo</id>
                <url>http://nexus.theyeticave.net/content/repositories/pub_releases</url>
            </repository>
        </repositories>
    
        <dependencies>
            <dependency>
                <groupId>org.bukkit</groupId>
                <artifactId>bukkit</artifactId>
                <version>1.8.8-R0.1-SNAPSHOT</version>
                <type>jar</type>
                <scope>provided</scope>
            </dependency>
            <dependency>
                <groupId>net.milkbowl.vault</groupId>
                <artifactId>VaultAPI</artifactId>
                <version>1.5</version>
                <scope>provided</scope>
            </dependency>
        </dependencies>
    </project>
Please use it in the root of your fork to get the proper dependencies.