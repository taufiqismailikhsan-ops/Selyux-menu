Gradle Wrapper bootstrap for SelyuxStyleMenu

Target Gradle: 8.14.3
Distribution: https://services.gradle.org/distributions/gradle-8.14.3-bin.zip

Files:
- gradle/wrapper/gradle-wrapper.properties
- gradlew
- gradlew.bat

The gradlew scripts download the official Gradle 8.14.3 wrapper JAR from the Gradle GitHub repository on first run and verify its SHA-256 checksum before executing it.

Note: the standard Gradle project normally commits gradle/wrapper/gradle-wrapper.jar too. This bootstrap variant avoids needing to manually carry the binary JAR into the repository.
