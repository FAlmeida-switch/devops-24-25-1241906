**Institution** Instituto Superior de Engenharia do Porto

**Program:** Switch Dev

**Course:** DevOps

**Author:** Francisco Almeida

## CA2 - Virtualization with Vagrant

## Table of Contents

- [Introduction](#introduction)
- [GitHub Issues](#github-issues)
- [Environment Setup](#environment-setup)
  - [VM Configuration](#VM-Configuration)
  - [Installing Dependencies](#Installing-Dependencies)
  - [Cloning the Repository](#Cloning-the-Repository)
- [Part 1: Virtualization with Vagrant](#part-1-virtualization-with-vagrant)
    - [Goals/Requirements](#goals-and-requirements)
    - [Implementation Steps](#implementation-steps)
    - [Gradle Demo Project Limitations](#Gradle-Demo-Project-Limitations)
- [Conclusion](#conclusion)

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
   Add a new subsection under Implementation Steps:

#### Remote VM Control via SSH
From Mac Terminal, connected using:
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

Demonstrates multi-service setup capability

### 3. Installing Dependencies
After setting up the VM, I installed the necessary dependencies for the projects:

```bash
sudo apt update
sudo apt install -y git openjdk-17-jdk maven gradle
```

### 4. Cloning the Repository
I cloned my individual DevOps repository inside the VM but beforehand I was not able
to do it because the `SSH`key was not connected to my github, so the below steps
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

Then, I went to GitHub:
    - Navigated to Settings > SSH and GPG keys
    - Clicked new SSH key, paste the key, and saved

- Test my SSH Connection:

```bash
ssh -T git@github.com
```

- Afterward I was finally able to clone my repository into the VM:

```bash
git clone git@github.com:FAlmeida-switch/devops-24-25-1241906.git
cd devops-24-25-1241906
```

## Part 1: Virtualization with Vagrant

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

### Conclusion

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