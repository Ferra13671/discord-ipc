# Discord IPC
Java 17 library for interacting with locally running Discord instance without the use of JNI.  
Currently, only supports retrieving the logged-in user and setting user's activity.  
The library is tested on Windows, Linux and macOS.

# Credits
Thanks meteordevelopment (https://github.com/MeteorDevelopment/discord-ipc) for source code

## Gradle
```groovy
repositories {
    maven {
        name = "ferra13671-maven"
        url = "https://ferra13671.github.io/maven/"
    }
}

dependencies {
    //
    implementation "com.ferra13671:discord-ipc:1.2"
    implementation "com.google.code.gson:gson:2.8.9" // GSON is not included but required
}
```

## Examples
For examples check out `example/src/main/java/example/Main.java`.  
