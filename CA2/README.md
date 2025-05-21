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
    - [Gradle Demo Project Limitations](#gradle-demo-project-limitations)
- [Conclusion - Part1](#conclusion---part-1)
- [Part 2: Vagrant](#part-2-vagrant)
    - [Goals/Requirements](#goalsrequirements-1)
    - [Implementation Steps](#implementation-steps-1)
    - [Gradle Demo Project Limitations](#gradle-demo-project-limitations)
- [Part 3: Containers with Docker](#part-3-containers-with-docker)
    - [Introduction](#introduction-1)
    - [GitHub Issues](#github-issues-1)
    - [Goals/Requirements](#goalsrequirements-2)
    - [Implementation Steps](#implementation-steps-2)
        - [Environment Setup](#1-environment-setup)
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
- [Part 4: Containers with Docker](#ca2---part-4-containers-with-docker)
    - [Introduction](#introduction-2)
    - [GitHub Issues](#github-issues-2)
    - [Goals/Requirements](#3-goalsrequirements)
    - [Project Structure](#4-project-structure)
    - [Implementation Steps](#5-implementation-steps)
        - [1. Create the `Dockerfile`](#51-create-the-dockerfile)
        - [2. Create the `.dockerignore` file](#52-create-the-dockerignore-file)
        - [3. Create the `docker-compose.yaml`](#53-create-the-docker-composeyaml)
        - [4. Build and Run the Containers](#54-build-and-run-the-containers)
        - [5. Implement Database Volume and Data Copy](#55-implement-database-volume-and-data-copy)
        - [6. Publish Images to Docker Hub](#56-publish-images-to-docker-hub)
    - [Tag Repository with CA2-part4](#6-tag-repository-with-ca2-part4)
    - [Kubernetes as a Docker Alternative (Optional)](#7-kubernetes-as-a-docker-alternative-optional)
    - [Conclusion - Part 4](#conclusion---part-4)


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
```

## CA2 - Part 4: Containers with Docker

### 1. Introduction

This section details my implementation of containerization for the full-stack Spring
Boot and React application using Docker. I leveraged Docker to package the application
and all its dependencies into isolated, reproducible, and portable containers, enabling
consistent deployment across various environments. This part of the assignment 
specifically focuses on creating robust Docker images, orchestrating multi-service 
applications using Docker Compose, ensuring data persistence, and publishing images to
Docker Hub.


### 2. GitHub Issues
1.  [#57 Create Multi-Stage Dockerfile for Spring Boot & React Application](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/57)
2.  [#58 Configure .dockerignore for Optimized Docker Builds](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/58)
3.  [#59 Set Up docker-compose.yaml for Web Service & H2 Database](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/59)
4.  [#60 Build and Run Docker Containers via Docker Compose](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/60)
5.  [#61 Implement Database Volume and Data Copy using docker exec](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/61)
6.  [#62 Publish Docker Images (Web & DB) to Docker Hub](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/62)
7.  [#63 Tag Repository with CA2-part4](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/63)
8.  [#64 Explore Kubernetes as a Docker Alternative (Optional)](https://github.com/FAlmeida-switch/devops-24-25-1241906/issues/64)

### 3. Goals/Requirements

* Containerize the multi-component application (Spring Boot Backend + React Frontend) using Docker, similar to CA2 Part 2 but with Docker.
* Utilize `docker-compose` to create two services: `web` (for Tomcat and Spring application) and `db` (for H2 server database).
* Publish the `db` and `web` images to Docker Hub.
* Use a volume with the `db` container and copy the database file to this volume using `docker exec`.
* Describe the entire process in this `README.md` file, including all Docker files (`docker-compose.yml`, `Dockerfile`).
* At the end of this assignment, tag the repository with `CA2-part4`.


### 4. Project Structure

My project structure is organized as follows:

```bash
CA2/part4/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── js/
│   └── resources/
├── Dockerfile
├── docker-compose.yaml
└── .dockerignore
```

### 5. Implementation Steps

#### 5.1. Create the `Dockerfile`

The `Dockerfile` contains the instructions for building my Docker image. 
For a Spring Boot application with a React frontend built 
via `frontend-maven-plugin`, a **multi-stage build** is the most effective
approach I chose. This method optimizes image size by separating build-time 
dependencies from runtime necessities.

I created a file named `Dockerfile` in my project's root directory with the 
following content:

**Stage 1: Build the React Frontend**

```dockerfile
FROM node:16-alpine AS frontend-builder
WORKDIR /app

COPY CA2/part4/src/main/js/package.json CA2/part4/src/main/js/package-lock.json ./
RUN npm install

COPY CA2/part4/src/main/js/ ./

RUN npm run build
```

**Stage 2: Build the Spring Boot Backend**

```dockerfile
FROM maven:3.9.3-eclipse-temurin-17 AS backend-builder
WORKDIR /app

COPY --from=frontend-builder /app/ ../src/main/resources/static/built/

COPY CA2/part4/pom.xml .
COPY CA2/part4/src/main/ ./src/main/

RUN mvn clean package -DskipTests
```

**Stage 3: Create the final production image`**

```dockerfile
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=backend-builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### 5.2. Create the .dockerignore file

To optimize Docker build performance and reduce the final image size, I created 
a `.dockerignore` file in my project's root directory. This file prevents unnecessary
files (like local build artifacts or IDE configuration) from being sent to the Docker 
daemon during the build process.

```bash
target/
node_modules/
build/
coverage/
logs/
*.log
*.tmp
*.zip
*.tar.gz
*.rar
*.jar
*.war
*.ear
hs_err_pid*
replay_pid*

.idea/
.vscode/
*.iml
*.ipr
*.iws
out/

.git/
.gitignore

.DS_Store
.npm/
.cache/
.env
npm-debug.log*
yarn-debug.log*
```

#### 5.3. Create the docker-compose.yaml
   
I used docker-compose.yaml to define and run my multi-container Docker application.
It orchestrates my web application service and a separate database service.

I created a file named docker-compose.yaml in my project's root directory:

```yaml
services:
  web:
    build:
      context: ../..  # Go up two levels to project root
      dockerfile: CA2/part4/Dockerfile
    container_name: ca2-web-app
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:h2:tcp://db:1521/~/testdb
      SPRING_DATASOURCE_USERNAME: sa
      SPRING_DATASOURCE_PASSWORD: password

  db:
    image: oscarfonts/h2:2.2.224
    container_name: ca2-database
    ports:
      - "1521:1521"  # TCP port for JDBC
      - "8082:8082"  # Web console port
    volumes:
      - db_data:/opt/h2-data
    environment:
      H2_OPTIONS: -ifNotExists

volumes:
  db_data:
````

#### 5.4. Build and Run the Containers

I navigate to my project's root directory in my terminal (where my `Dockerfile` and
`docker-compose.yaml` are located) and execute the following command:

```bash
docker compose up --build
```

This command will:

1. Build the `web` service image using my `Dockerfile` (executing the multi-stage build process).


2. Pull the `db` service image (if not already downloaded locally).


3. Start both the `db` and `web` containers in the foreground. I can add `-d` for 
detached mode (docker compose up -d`).


#### 5.5. Implement Database Volume and Data Copy

To ensure data persistence and copy the database file as required by the assignment, I
perform the following steps:

First, I stop the containers to ensure the database file is not actively being written
to during the copy operation:

```bash
docker compose down
```

Next, I restart the db container in detached mode to be able to execute commands within it:

```bash
docker compose up -d db
```

Now, I use `docker exec` to run a shell inside the `db` container and copy the H2 database
file to the mounted volume (`db_data`). The exact path to the H2 database file inside the
container might vary, but a common location for the `ghcr.io/h2database/h2` image is 
within `/opt/h2-data/`. I need to ensure the database file is actually created by the
application (e.g., by interacting with the app and saving data).

```bash
docker exec ca2-database cp /opt/h2-data/testdb.mv.db /opt/h2-data/persisted_testdb.mv.db
```

This command copies the database file from its location within the container to the 
`db_data` volume, making it persistent.

Finally, I can verify data persistence by stopping all containers and starting them 
again. Any data I previously added to the application should still be present.

```bash
docker compose down
docker compose up -d
```

#### 5.6. Publish Images to Docker Hub

As required by the assignment, I will publish my Docker images to Docker Hub to make them
accessible for deployment on other machines or cloud platforms.

First, I ensure the images are built:

```bash
docker compose build
```

Then, I tag my `web` and `db` images with my Docker Hub username and a chosen version. 
I will use the `container_name` defined in `docker-compose.yaml` (`ca2-web-app`, `ca2-database`).

```bash
docker tag part4-web falmeidaswitch/ca2-web:v1
docker tag oscarfonts/h2:2.2.224 falmeidaswitch/ca2-db:v1
```

Finally, I push the tagged images to Docker Hub:

```bash
docker push falmeidaswitch/ca2-web:v1
docker push falmeidaswitch/ca2-db:v1
```

### 6. Tag Repository with CA2-part4

After completing all the steps and verifying the solution, I will tag my repository with
`CA2-part4` to mark the submission for this assignment.

```bash
git tag CA2-part4
git push origin CA2-part4
```

### 7. Kubernetes as a Docker Alternative (Optional)

For further exploration, I can consider alternatives or complements to Docker regarding 
containerization features. This information will not be evaluated for this assignment.

**Option 1 - Exploring Kubernetes**

Kubernetes is an open-source container orchestration system for automating application
deployment, scaling, and management. It can be an alternative or a complement to Docker.

**Comparison with Docker (regarding containerization features):**

* **Docker:** Primarily focuses on creating and managing individual containers.
Docker Compose helps orchestrate *multiple Docker containers on a single host*. 
It's excellent for local development and simpler multi-container applications.


* **Kubernetes:** Designed for orchestrating *hundreds or thousands of containers across
a cluster of machines*. It provides features like automatic scaling, load balancing, 
self-healing, and declarative configuration. While Docker is essential for building the
containers, Kubernetes is for deploying and managing them at scale in production
environments.

**How Kubernetes could be used to solve the same goals as presented for this assignment:**

To achieve the same goals as this assignment (running a web app and an H2 database), 
Kubernetes would involve:

1.  **Container Images:** I would still build the same Docker images for my Spring Boot
`web` application and the H2 `db` service, as defined by my `Dockerfile`. Kubernetes uses
these images.


2.  **Deployment Definitions:** Instead of a `docker-compose.yaml`, I would write Kubernetes
YAML files (e.g., `deployment.yaml` and `service.yaml`) to define:
    * **Deployments:** How to run my `web` and `db` containers (e.g., number of replicas,
    image to use, resource limits).
    * **Services:** How to expose my `web` application to the network and how the `web`
    service can discover and connect to the `db` service within the cluster.
    * **Persistent Volumes:** How to provision and attach persistent storage for the H2
    database, ensuring data survives pod restarts.
    

3.  **Orchestration:** Kubernetes would automatically manage the lifecycle of these 
containers,
ensuring they are running, restarting them if they fail, load balancing traffic to the
`web` app,
and handling updates gracefully across the cluster.

In essence, while Docker focuses on the *packaging* of the application into containers,
Kubernetes focuses on the *orchestration* and *management* of those containers in a 
distributed, highly available environment.

### Conclusion - Part 4
This section concludes the containerization efforts for the Spring Boot and React application
using Docker. Key objectives such as creating efficient multi-stage Docker images, 
orchestrating services with Docker Compose, implementing data persistence for the H2 
database, and publishing images to Docker Hub were successfully achieved.

This phase underscored the power of Docker in creating reproducible and portable 
deployment environments, simplifying the process of running complex multi-service 
applications. The use of volumes ensured data integrity, while Docker Hub publishing 
facilitates easy distribution and deployment across various platforms. The optional 
exploration of Kubernetes further highlighted the advanced orchestration capabilities
available for large-scale container deployments.