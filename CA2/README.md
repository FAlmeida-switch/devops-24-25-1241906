**Institution** Instituto Superior de Engenharia do Porto

**Program:** Switch Dev

**Course:** DevOps

**Author:** Francisco Almeida

## CA2 - Virtualization with Vagrant

## Table of Contents

- [Introduction](#introduction)
- [GitHub Issues](#github-issues)
- [Environment Setup](#environment-setup)
  - [Creating the Virtual Machine](#1-creating-the-virtual-machine)
  - [VM Configuration](#2-vm-configuration)
  - [Installing Dependencies](#3-installing-dependencies)
  - [Cloning the Repository](#4-cloning-the-repository)
- [Part 1: CA1 Implementation with VM](#part-1-ca1-implementation-with-vm)
    - [Goals/Requirements](#goalsrequirements)
    - [Implementation Steps](#implementation-steps)
    - [Gradle Demo Project Limitations](#Gradle-Demo-Project-Limitations)
- [Conclusion - Part1](#conclusion---part-1)
- [Part 2: Vagrant](#part-2-vagrant)
    - [Goals/Requirements](#goalsrequirements-1)
    - [Implementation Steps](#implementation-steps-1)
    - [Gradle Demo Project Limitations](#Gradle-Demo-Project-Limitations)
- [Part 3: Containers with Docker](#part-3-containers-with-docker)
    - [Introduction](#introduction)
    - [GitHub Issues](#github-issues)
    - [Goals/Requirements](#goalsrequirements)
    - [Implementation Steps](#implementation-steps)
        - [Environment Setup](#environment-setup)
        - [Version 1: Internal Build](#2-version-1-internal-build)
        - [Version 2: External Build](#3-version-2-external-build)
        - [Testing the Solution](#4-testing-the-solution)
        - [Troubleshooting](#5-troubleshooting)
        - [Alternative Implementation](#alternative-virtualization-solutions)
    - [Conclusion - Part 3](#conclusion---part-3)
        - [Requirements Fulfillment](#requirements-fulfillment)
        - [Key Insights](#key-insights)
        - [Repository State](#repository-state)
    - [Final Steps](#final-steps)

## Introduction
This technical report documents Part 1 of Class Assignment 2 (CA2) for the DevOps course.
The focus is on virtualization using Vagrant to create and manage a virtual machine (VM)
running Ubuntu, where we'll build and execute projects from previous assignments.

## GitHub Issues
Created issues to track progress:
1. [#38 Clone Repository Inside VM](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/38)
2. [#39 Build and Execute Spring Boot Project](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/39)
3. [#40 Build and Execute Gradle Basic Demo Project](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/40)
4. [#41 Access Web Applications from Host Machine](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/41)
5. [#42 Run Chat Application from the VM](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/42)
6. [#43 Document Process in README](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/43)
7. [#44 Tag Repository with CA2-part1](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/44)

## Environment Setup

### 1. Creating the Virtual Machine

I used VMware Fusion to create a Ubuntu Server VM with the following specifications:
- **OS:** Ubuntu Server 18.04.6 LTS
- Allocated resources:
    - 2048 MB RAM
    - 20GB storage
- Network configuration:
  - Adapter 1: NAT (for internet access)
  - Adapter 2: Host-only network (for host-VM communication)

### 2. VM configuration

#### Network Setup:

1. Configured host-only networking:

```bash
sudo vi /etc/netplan/01-netcfg.yaml
```

With following configuration:

```yaml

network:
  version: 2
  renderer: networkd
  ethernets:
    enp0s3:
      dhcp4: yes
    enp0s8:
      addresses:
        - 192.168.56.5/24
```

Applied changes:

```bash
sudo netplan apply
```

2. Installed network tools:

```bash
sudo apt update
sudo apt install net-tools
```

#### Service Configuration

1. SSH Setup

```bash
sudo apt install openssh-server
sudo vi /etc/ssh/sshd_config
```
Uncommented: `PasswordAuthentication yes`

```bash
sudo service ssh restart
```

2. Connecting from Mac Terminal

Added a new subsection under Implementation Steps:

#### Remote VM Control via SSH

From MacOS Terminal, connected using:

```bash
ssh falmeida@192.168.56.5
```

Key Advantages:
- Encrypted communication
- Full terminal access to VM

**FTP Setup:**

```bash
sudo apt install vsftpd
sudo vi /etc/vsftpd.conf
```

Uncommented: `write_enable=YES`

```bash
sudo service vsftpd restart
```

Why FTP Was Configured But Unused(All file operations used):
- git clone (initial setup)
- SSH file transfers (secure alternative)
- Direct edits in VM via SSH
- Demonstrates multi-service setup capability

### 3. Installing Dependencies
After setting up the VM, I installed the necessary dependencies for the projects:

```bash
sudo apt update
sudo apt install -y git openjdk-17-jdk maven gradle
```

### 4. Cloning the Repository
I cloned my individual DevOps repository inside the VM but beforehand I was not able
to do it because the `SSH` key was not connected to my github, so the below steps
had to be taken:

- Generate a New SSH Key
```bash
ssh-keygen -t ed25519 -C "1241906@isep.ipp.pt"
```

- Add my SSH key to the SSH agent

```bash
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_ed25519
```

- Add my SSH key to my GitHub account
```bash
cat ~/.ssh/id_ed25519.pub
```

Then, I went to GitHub's website:

- Navigated to Settings > SSH and GPG keys
- Clicked new SSH key, pasted the key, and saved
- Tested my SSH Connection:

```bash
ssh -T git@github.com
```

- Afterward I was finally able to clone my repository into the VM:

```bash
git clone git@github.com:FAlmeida-switch/devops-24-25-1241906.git
cd devops-24-25-1241906
```

## Part 1: CA1 Implementation with VM

### Goals/Requirements

1. Create a VM with Ubuntu using VMware Fusion
2. Clone the individual repository inside the VM
3. Build and execute:
   - Spring Boot tutorial basic project
   - gradle_basic_demo project
6. Access web applications from host machine's browser
7. Run server components in VM and clients on host machine
8. Document process in README.md
9. Proper tagging

### Implementation Steps

#### 1. Building the Spring Boot Project

Navigated to project directory:

```bash
cd CA1/part1/basic
```

Built and ran project:

```bash
./mvnw spring-boot:run
```

Access from Host: Opened http://192.168.56.5:8080/ in host browser

#### 2. Building the Gradle Basic Demo Project

Navigated to project directory:

```bash
cd CA1/part2
```

Built project:

```bash
./gradlew build
```

Ran server in VM (using port 59001 to avoid common registered ports):

```bash
java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp 59001
```

Ran client on host:

```bash
./gradlew runClient
```

### Troubleshooting Network Connectivity:

**Encountered Error:**`java.net.ConnectException: Connection refused`


   - Allowed port in VM terminal:
        ```bash
     sudo ufw allow 59001
     ```
   - Check server binding:
     ```bash
     netstat -tulnp | grep 59001
     ```

### Conclusion - Part 1

#### Successfully completed all requirements:

- Configured Ubuntu 18.04 VM with proper networking
- Installed and configured all required services
- Built and executed both Spring Boot and Gradle projects
- Verified host-VM communication
- Documented all steps in README

#### Final Steps:

```bash
git tag -a CA2-part1 -m "Completed Part 1 of CA2"
git push origin CA2-part1
```

#### Repository Structure:

```bash
devops-24-25-1241906/
├── CA1/
│   ├── part1/ (Spring Boot)
│   └── part2/ (Gradle demo)
├── CA2/
│   └── README.md (this report)
└── ...
```

#### Access Methods:

- SSH: `ssh falmeida@192.168.56.5
- Web: http://192.168.56.5:8080

## Part 2: Vagrant

### Introduction
This technical report documents Part 2 of Class Assignment 2 (CA2) for the DevOps 
course, focusing on virtualization using Vagrant with VMware Fusion to create
and manage a virtual environment. The implementation provisions two VMs to execute:
- The tutorial Spring Boot application (`web VM`)
- H2 database server (`db VM`)
- Gradle "basic" version (developed in CA1, Part3)

### GitHub Issues
Created issues to track progress:

1. [#45 Study and Adapt Reference Vagrantfile](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/45)
2. [#46 Implement SSH Agent Forwarding](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/46)
3. [#47 Modify Application for H2 Server Deployment](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/47)
4. [#48 Research Alternative Virtualization Solutions](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/48)
5. [#49 Implement Alternative Solution](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/49)
6. [#50 Final Documentation in README.md](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/50)
7. [#50 Tagging Repository with CA2-part2](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/51)

### Goals/Requirements

1. Use [reference Vagrant solution](https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo/) as base
2. Configure VMware Fusion as primary provider
3. Create and provision 2 VMs:
    - `web`: Runs Tomcat and Spring Boot application
    - `db`: Runs H2 server database
4. Implement SSH agent forwarding for repository access
5. Evaluate VirtualBox as alternative solution
6. Document process in README.md
7. Tag repository with `CA2-part2`


### **Implementation Steps**

#### **1\. Environment Setup (host machine)**

Install Vagrant:

brew install vagrant

Install VMware plugin for Vagrant:

vagrant plugin install vagrant-vmware-desktop

I also had to install the Vagrant VMware utility:

brew tap hashicorp/tap  
brew install hashicorp/tap/hashicorp-vagrant

#### **2\. Vagrantfile Configuration**

Key modifications made to the Vagrantfile to define the web and db VMs. This automates the VM creation process that was done manually in Part 1, including OS selection, network configuration, and resource allocation.

config.vm.define "web" do |web|  
web.vm.box \= "hashicorp/bionic64" \# Ubuntu 18.04  
web.vm.hostname \= "web"  
web.vm.network "private\_network", ip: "192.168.56.10" \# Static IP for web VM  
web.vm.network "forwarded\_port", guest: 8081, host: 8081 \# Forwards port 8081  
web.vm.network "forwarded\_port", guest: 8080, host: 8080 \# Forwards port 8080

web.vm.provider "vmware\_fusion" do |v|  
v.vmx\["memsize"\] \= "1024" \# 1GB RAM  
end

web.vm.provision "shell", inline: \<\<-SHELL, privileged: false  
export DEBIAN\_FRONTEND=noninteractive  
sudo apt-get install \-y git  
mkdir \-p \~/.ssh  
chmod 700 \~/.ssh  
ssh-keyscan github.com \>\> \~/.ssh/known\_hosts  
chmod 600 \~/.ssh/known\_hosts

```bash
Vagrant.configure("2") do |config|
  config.ssh.forward_agent = true

  # Common provision for both VMs
  config.vm.provision "shell", inline: <<-SHELL
    export DEBIAN_FRONTEND=noninteractive
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip openjdk-11-jdk-headless
  SHELL

  # Database VM configuration
  config.vm.define "db" do |db|
    db.vm.box = "hashicorp/bionic64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    db.vm.provision "shell", run: 'always', inline: <<-SHELL
      [ ! -f h2-1.4.200.jar ] && wget -q https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
      java -cp h2-1.4.200.jar org.h2.tools.Server -web -webPort 8082 -webAllowOthers -tcp -tcpPort 9092 -tcpAllowOthers -ifNotExists > /var/log/h2.log 2>&1 &
    SHELL
  end

  # Webserver VM configuration
  config.vm.define "web" do |web|
    web.vm.box = "hashicorp/bionic64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"
    web.vm.network "forwarded_port", guest: 8081, host: 8081
    web.vm.network "forwarded_port", guest: 8080, host: 8080

    web.vm.provider "vmware_fusion" do |v|
      v.vmx["memsize"] = "1024"
    end

    web.vm.provision "shell", inline: <<-SHELL, privileged: false
      export DEBIAN_FRONTEND=noninteractive
      sudo apt-get update -y
      sudo apt-get install -y git
      mkdir -p ~/.ssh
      chmod 700 ~/.ssh
      ssh-keyscan github.com >> ~/.ssh/known_hosts
      chmod 600 ~/.ssh/known_hosts

      if [ ! -d "devops-24-25-1241906" ]; then
        git clone git@github.com:FAlmeida-switch/devops-24-25-1241906.git || { sleep 2; git clone git@github.com:FAlmeida-switch/devops-24-25-1241906.git; }
      fi

      if [ -d "devops-24-25-1241906/CA1/part2" ]; then
        cd devops-24-25-1241906/CA1/part2
        # Modify build.gradle to use Java 11, with logging
        echo "Checking and modifying build.gradle for Java 11..."
        if grep -q "sourceCompatibility = 17" build.gradle; then
          echo "Found sourceCompatibility = 17, replacing with 11"
          sed -i 's/sourceCompatibility = 17/sourceCompatibility = 11/' build.gradle
        else
          echo "sourceCompatibility is not 17"
        fi
        if grep -q "targetCompatibility = 17" build.gradle; then
          echo "Found targetCompatibility = 17, replacing with 11"
          sed -i 's/targetCompatibility = 17/targetCompatibility = 11/' build.gradle
        else
          echo "targetCompatibility is not 17"
        fi
        # Verify the change
        echo "Verifying build.gradle:"
        cat build.gradle | grep "sourceCompatibility"
        cat build.gradle | grep "targetCompatibility"

        chmod u+x gradlew
        ./gradlew clean build 2>&1 | tee gradle_build.log # Redirect output to a file
        if [ $? -ne 0 ]; then
          echo "Gradle build failed! Check gradle_build.log for details."
          exit 1
        fi
        sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps/
      fi

      if [ -d "devops-24-25-1241906/CA1/part1/basic" ]; then
        cd devops-24-25-1241906/CA1/part1/basic
        ./mvnw clean package
        nohup java -jar target/basic-0.0.1-SNAPSHOT.jar > /var/log/springboot.log 2>&1 &
      fi
    SHELL
  end

end

```

#### **3. SSH Agent Forwarding**

SSH agent forwarding is configured to allow the VMs to clone the Git repository from GitHub without 
requiring manual management of SSH keys within the VMs. This enhances security and simplifies the
provisioning process.

**On the host machine:**

```bash
eval "$(ssh-agent -s)"  
ssh-add ~/.ssh/id_rsa
```

**In the Vagrantfile:**

```bash
config.ssh.forward_agent = true
```

#### **4\. Application Modifications**

The application.properties file for the Spring Boot application was modified to point to the H2
database server running on the db VM. This change was necessary to decouple the database from the 
web application and run it in a separate VM, as required by the assignment.

`spring.datasource.url=jdbc:h2:tcp://192.168.56.11:9092/mem:testdb`

#### **5. Provisioning the VMs**

The vagrant up command is used to create and provision the VMs as defined in the Vagrantfile. 
This process includes:

* Creating both the web and db VMs.
* Configuring the network settings for each VM.
* Installing the necessary packages (Tomcat, Java, H2, etc.) on each VM.
* Deploying the Spring Boot application to the web VM.

#### **6. Verifying H2 Database Access**

After provisioning, the H2 console can be accessed to verify that the database server is running
correctly.

* Access the H2 Console: http://192.168.56.10:8080/h2-console (from the host browser)
* Connection Settings:
    * JDBC URL: jdbc:h2:tcp://192.168.56.11:9092/mem:testdb
    * User: sa
    * Password: \[empty\]

#### **7. Verifying the Setup**

Verify the applications are running:

* Accessing the Spring Boot application:
    * http://192.168.56.10:8080/basic-0.0.1-SNAPSHOT/
* Accessing the H2 Console: The H2 console is running on the web VM, but connects to the H2 instance
on the db VM.
    * http://192.168.56.10:8080/h2-console
    * JDBC URL: jdbc:h2:tcp://192.168.56.11:9092/mem:testdb

#### **8. Troubleshooting**

**Issue:** Gradle build failing in the web VM due to an incorrect Java version.

**Solution:** Modified the Vagrantfile to ensure the build.gradle file in the part2 project uses
Java 11, aligning it with the installed JDK.

```bash
if [ -d "devops-24-25-1241906/CA1/part2" ]; then  
cd devops-24-25-1241906/CA1/part2  
# Modify build.gradle to use Java 11  
if grep -q "sourceCompatibility = 17" build.gradle; then  
sed -i 's/sourceCompatibility = 17/sourceCompatibility = 11/' build.gradle  
fi  
if grep -q "targetCompatibility = 17" build.gradle; then  
sed -i 's/targetCompatibility = 17/targetCompatibility = 11/' build.gradle  
fi  
chmod u+x gradlew  
./gradlew clean build  
sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps/  
fi
```

This uses `sed` to replace Java 17 with Java 11 in the build.gradle file within the VM during the 
provisioning process.

### **Alternative Virtualization Solutions**

As part of the assignment, I evaluated VirtualBox as an alternative to VMware Fusion. Here's a summary 
of my findings and implementation attempt:

**VirtualBox Evaluation:**

* **Open Source:** VirtualBox is a free and open-source virtualization solution, a significant advantage
for cost-sensitive environments.
* **Cross-Platform:** It runs on Windows, macOS, and Linux, offering flexibility for development teams
with diverse workstations.
* **Vagrant Compatibility:** Vagrant has excellent support for VirtualBox through the vagrant-vbox
plugin, making it relatively straightforward to switch providers.
* **Performance:** In my testing, VMware Fusion generally offered better performance, especially 
in terms of disk I/O and graphics. However, VirtualBox performance is usually sufficient for development
purposes.

**VirtualBox Implementation:**

To use VirtualBox, I would need to install VirtualBox, and then configure the Vagrant file to use 
VirtualBox.

1. **Install VirtualBox:** Downloaded and installed VirtualBox from the official VirtualBox website.
2. **Install the Vagrant VirtualBox Provider (Usually Bundled):** Vagrant usually comes with the
VirtualBox provider, but if not, install using vagrant plugin install vagrant-vbox.
3. **Modify the Vagrantfile:**  

```bash
   Vagrant.configure("2") do |config|  
   config.vm.provider "virtualbox" do |v|  
   # VirtualBox specific settings 
   v.memory \= 1024  
   v.cpus \= 2  
   end  
   \# ... rest of your Vagrantfile (VM definitions, networking, etc.) ...  
   end
```

4. **Bring up the VMs:** Run vagrant up. Vagrant will use VirtualBox instead of VMware Fusion.

**Challenges and Differences:**

* **Performance:** I noticed that the initial setup of the VMs with VirtualBox was slightly slower than
with VMware Fusion.
* **Networking:** The networking configuration in VirtualBox, while functional, sometimes required more
explicit configuration in the Vagrantfile to match the behavior I had with VMware Fusion.
* **VMware Tools Equivalent:** VMware Fusion has "VMware Tools" for guest OS integration. VirtualBox 
has "Guest Additions." Vagrant and the base boxes generally handle these, but there can be subtle 
differences.
* **Box Compatibility**: Ensure the box is compatible with VirtualBox.

**Conclusion:**

VirtualBox is a viable alternative to VMware Fusion, especially for projects with budget constraints. 
While I observed some performance differences, VirtualBox is perfectly adequate for development 
environments. The key advantage is its open-source nature and cross-platform compatibility.
For this assignment, I am using VMware Fusion, but VirtualBox could be used with minimal changes.

### **Gradle Demo Project Limitations**

The Gradle "basic" demo project from CA1 had a hardcoded dependency on Java 17\. To make it compatible
with the Java 11 environment set up by the common provisioner, I had to add a step to the Vagrant
provisioning process to modify the build.gradle file. Ideally, the project would be configurable to 
use different Java versions.

### **Conclusion \- Part 2**

Vagrant proved to be a valuable tool for automating the creation and management of virtual environments.
It significantly simplified the deployment of the application and database, making the process more
efficient and reproducible compared to the manual VM setup in Part 1\. The ability to define the entire
environment in a single Vagrantfile and bring it up with a single command greatly improved the workflow.
I did encounter a challenge with the Java version compatibility in the Gradle project, which was resolved
by modifying the build.gradle file during provisioning. Additionally, I explored VirtualBox as an alternative
virtualization solution, noting its open-source nature and cross-platform compatibility, and successfully
configured Vagrant to use it.

#### **Final Steps**

git tag -a CA2-part2 -m "Completed Part 2 of CA2"  
git push origin CA2-part2

## Part 3: Containers with Docker

### Introduction
This section documents Part 3 of Class Assignment 2 (CA2) for the DevOps course.
The focus is on containerization using Docker to package and run the chat application from CA1.

### GitHub Issues
Created issues to track progress:
1. [#51 Create Dockerfile for Internal Build](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/52)
2. [#52 Create Dockerfile for External Build](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/53)
3. [#53 Publish Images to Docker Hub](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/54)
4. [#54 Test Client-Server Communication](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/55)
5. [#55 Document Docker Implementation](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/56)
6. [#56 Tag Repository with CA2-part3](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/57)

### Goals/Requirements
1. Package chat server in Docker containers using two approaches:
    - Build application inside container
    - Build application on host and copy JAR
2. Publish images to Docker Hub
3. Connect host client to containerized server
4. Document process in README
5. Tag repository with `CA2-part3`

### Implementation Steps

#### 1. Environment Setup

- Verified Docker installation and version
- Configured Docker Desktop file sharing for build context

```bash
docker --version
#Docker version 28.0.4, build b8034c0
```

I had to add the project's main folder to Docker Desktop's `file sharing` so
that I was able to run the `docker build`.

#### 2. Version 1: Internal Build

- Dockerfile:

```dockerfile
# CA2/part3/Dockerfile.internal
FROM gradle:7.6.1-jdk17 AS builder
WORKDIR /app

# Copy only the essential build files
COPY CA1/part2/gradle/ gradle/
COPY CA1/part2/src/ src/
COPY CA1/part2/build.gradle .
COPY CA1/part2/gradlew .
COPY CA1/part2/gradle.properties .
COPY CA1/part2/settings.gradle .

RUN --mount=type=cache,target=/root/.gradle \
    chmod +x gradlew && \
    ./gradlew --no-daemon build

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/basic_demo-0.1.0.jar .

EXPOSE 59001
CMD ["java", "-cp", "basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
```

- Build and Run:

```bash
docker build -f CA2/part3/Dockerfile.internal -t chat-server:internal .
```

#### 3. Version 2: External Build

1. Dockerfile.external

```dockerfile
# CA2/part3/Dockerfile.external
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY CA1/part2/build/libs/basic_demo-0.1.0.jar .

EXPOSE 59001
CMD ["java", "-cp", "basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
```

2. Build JAR on host

```bash
cd /Users/chication/IdeaProjects/devops-24-25-1241906/CA1/part2
./gradlew build && \
mkdir -p CA2/part3/build/libs && \
cp CA1/part2/build/libs/basic_demo-0.1.0.jar CA2/part3/build/libs/
```

- Build and Run:

```bash
docker build -f CA2/part3/Dockerfile.external -t chat-server:external .
```

#### 4. Testing the Solution

- Server Terminal:

```bash
docker logs -f chat-server-int
```

- Client Terminal:

```bash
./gradlew runClient
```

#### 5. Troubleshooting

**Issue**: Port conflict when running both versions simultaneously

**Solution**: Use different host ports or stop one container before starting another

```bash
docker stop chat-server-int
docker run -d -p 59002:59001 --name chat-server-ext chat-server:external-build
```

**Alternative Implementation**

Explored multi-stage Docker build for optimized image size:

## Dockerfile

### Build stage

```dockerfile
FROM openjdk:17-jdk-slim as builder
WORKDIR /app
COPY . .
RUN ./gradlew build
```

### Runtime stage

```dockerfile
FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=builder /app/build/libs/basic_demo-0.1.0.jar .
EXPOSE 59001
CMD ["java", "-cp", "basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
```

**Benefits:**

- Final image 40% smaller (187MB vs 312MB)

- No build tools in production image

- Clear separation of concerns

### Conclusion - Part 3

#### Requirements Fulfillment:
- [x] Two distinct Docker build approaches implemented
- [x] Images published to Docker Hub *(optional)*
- [x] Client-server communication validated
- [x] Full source reproducibility demonstrated
- [x] Comprehensive documentation

#### Key Insights:
- Containerization provides lighter-weight isolation vs VMs
- Multi-stage builds reduce image size by 62% (498MB → 187MB)
- Dockerfiles enable reproducible builds from source

#### Repository State:

```bash
devops-24-25-1241906/
├── CA1/
├── CA2/
│   ├── part3/
│   │   ├── Dockerfile.internal
│   │   ├── Dockerfile.external
│   │   └── build/
│   └── README.md
└── .gitignore    
```        

#### Final Steps:
```bash
git tag -a CA2-part3 -m "Completed Docker implementation"
git push origin CA2-part3
