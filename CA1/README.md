**Institution** Instituto Superior de Engenharia do Porto

**Program:** Switch Dev

**Course:** DevOps

**Author:** Francisco Almeida

## CA1 - Version Control with Git:

## Table of Contents

- [Introduction](#introduction)
- [Environment Setup](#environment-setup)
- [Part 1.1: Development Without Branches](#part-1-development-without-branches)
  - [Goals/Requirements](#goals-and-requirements)
  - [Development](#Development)
- [Part 1.2: Development With Branches](#part-2-development-with-branches)
  - [Goals/Requirements](#goals-and-requirements)
  - [Development](#Development)
  - [Final Results](#Final-Results)
  - [Alternative Implementation](#Alternative-Implementation)
- [Part 2: Build Tools with Gradle](#part-2-Build-Tools-with-Gradle)
  - [Goals/Requirements](#goals-and-requirements)
  - [Development](#Development)
- [Part 3: Converting Basic Version to Gradle](#Converting-Basic-Version-to-Gradle)
  - [Goals/Requirements](#goals-and-requirements)
  - [Development](#Development)

## Introduction
- This technical report was created with the purpose of providing a detailed  assignment for the DevOps course.
  The assignment is divided into three parts: **Part 1.1**, utilizing version control with Git without branches, **Part 1.2**,
  where I implement branching and **Parts 2** and **3** where the Gradle build tool is implemented on a new demo project, but also
  within **part 1** of the assignment. The conclusion contemplates the results obtained throughout the
  full process.

## Environment Setup

**Copying the Tutorial Application:** I created a local folder for my repository, initializing it through Git, and then proceeded to
clone the full application to it, so that I could have access to it within my version control system.
```Terminal
mkdir ~/Switch/II/DevOps/DevOpsRepo/devops-24-25-1241906
cd ~/Switch/II/DevOps/DevOpsRepo/devops-24-25-1241906
git init

cp -r ~/Switch/II/DevOps/tut ~/Switch/II/DevOps/DevOpsRepo/devops-24-25-1241906
```

**Connecting to GitHub:** I created a remote repository on Github and then connected my local one to it. This connection
makes it possible to upload our changes and updates to the newly created remote repository.
```Terminal
git remote add origin <repository-URL>
```

**Commit #1:** After trial and error processes I proceeded to create and commit the README.md to the remote repository, and
afterwards pushing it.

```Terminal
git add .
git commit -m "Initial project version."
git push
```

At this point I tried some git commands, like the fetch/log/revert for experimenting, so that I could have a clear understanding
of what happens with each one of them. By the end of it I had to revert to an initial phase so that the README.md file could be
accessed once again.

```Terminal
git revert e411b44
git push
```

## Part 1.1 - Development Without Branches

### Goals and Requirements
-   Basic version control operations without branching (Master branch only).
-   New features introduction (i.e. `jobYears`).
-   Version Tagging.

### Development

Recursively copied the code contained inside the basic folder in Tutorial React.js and Spring Data REST Application
into a new folder named `CA1`. I use the `-p` command to create `part1` inside the newly created folder and copied the
original `pom.xml` file from the full application to the `part1` folder. I also removed `.gitignore` from the newly imported
file and created a new one in gitignore.io with the following: `react, maven, gradle, node, intellij, java`.**

```Terminal
mkdir -p ~/devops-24-25-1241906/CA1/part1
cp -r ~/devops-24-25-1241906/tut/basic ~/devops-24-25-1241906/CA1/
cd ~/devops-24-25-1241906/CA1/basic
rm .gitignore
cp ~/devops-24-25-1241906/tut/pom.xml ~/devops-24-25-1241906/CA1/part1
```

**Commit & Push.**

```Terminal
git add .
git commit -m "Added basic folder to CA1/part1."
git push
```

**Tagging.**

I tagged the initial setup as `v1.1.0` and subsequently pushed this tag to the remote repository:

```Terminal
git tag -a v1.1.0 -m "Initial version 1.1.0"
git push origin v1.1.0
```
**New Feature along with Issues.**

- **Issues**: Created some issues for the initial phase of the assignment development:
```Terminal
gh issue create --title "Generate Employee class tests."
gh issue create --title "Update Employee Class." --body "Add jobYears attribute, with its getter and setter."
gh issue create --title "Update the README.md file inside CA1."
gh issue create --title "Update app.js with jobYears."
```

- **Employee.java**: Added a new integer field named `jobYears`, which should be an `int`.
  I added this attribute, along with its getter and setter methods to allow for data encapsulation,
  aswell as validators for all parameters.
- Below are the key updates made to the `Employee` Class:

```java
public Employee(String firstName, String lastName, String description, int jobYears) throws IllegalArgumentException{
    setFirstName(firstName);
    setLastName(lastName);
    setDescription(description);
    setJobYears(jobYears);
}

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return Objects.equals(id, employee.id) &&
            Objects.equals(firstName, employee.firstName) &&
            Objects.equals(lastName, employee.lastName) &&
            Objects.equals(description, employee.description) &&
            Objects.equals(jobYears, employee.jobYears);
}

@Override
public int hashCode() {

    return Objects.hash(id, firstName, lastName, description);
}

public Long getId() {
    return id;
}

public void setId(Long id) throws IllegalArgumentException {
    if (id == null)
        throw new IllegalArgumentException("The ID can't be null.");
    this.id = id;
}

public String getFirstName() {
    return firstName;
}

public void setFirstName(String firstName) throws IllegalArgumentException {
    if (firstName == null || firstName.trim().isEmpty())
        throw new IllegalArgumentException("The firstName can't be null or empty.");
    this.firstName = firstName;
}

public String getLastName() { return lastName; }

public void setLastName(String lastName) throws IllegalArgumentException {
    if (lastName == null || lastName.trim().isEmpty())
        throw new IllegalArgumentException("The lastName can't be null or empty.");
    this.lastName = lastName;
}

public String getDescription() { return description; }

public void setDescription(String description) throws IllegalArgumentException {
    if (description == null || description.trim().isEmpty())
        throw new IllegalArgumentException("The description can't be null or empty.");
    this.description = description;
}

public int getJobYears() {return jobYears;}

public void setJobYears(int jobYears) throws IllegalArgumentException {
    if (jobYears < 0)
        throw new IllegalArgumentException("jobYears can't be negative.");
    this.jobYears = jobYears;
}



@Override
public String toString() {
    return "Employee{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", description='" + description + '\'' +
            ", jobYears='" + jobYears + '\'' +
            '}';
}


```
**EmployeeTest.java**:
- Setup and Initialization: Utilized @ParameterizedTest and @MethodSource for parameterized values with different inputs.
- Input Validation: Ensured the constructor and setters reject invalid inputs (null or empty Strings and negative jobYears), preventing improper object creation.
- Successful Creation: Verified that valid inputs result in successful object creation without exceptions, confirming the Employee class functions correctly under proper usage.
- Object Comparison: Tested the equals and hashCode methods for accurate object comparison.
- String Output: Checked the toString method to ensure it accurately represents Employee object details, aiding in debugging and logging.

Here are some examples of the tests implemented:
```java


class EmployeeTest {


  @Test
  void shouldCreateValidEmployee() throws IllegalArgumentException {
    //arrange

    //act
    Employee e1 = new Employee("jose", "almeida", "ring bearer", 12 );
    //assert
    assertNotNull(e1);
  }

  @Test
  void shouldReturnCorrectValuesFromGetters() {
    //arrange
    Employee e1 = new Employee("Joao", "Almeida", "Developer", 5);

    //act + assert
    assertEquals("Joao", e1.getFirstName());
    assertEquals("Almeida", e1.getLastName());
    assertEquals("Developer", e1.getDescription());
    assertEquals(5, e1.getJobYears());
  }

  public static Stream<Arguments> invalidConstructorArguments() {
    return Stream.of(
            Arguments.of(null, "Almeida", "Descrição", 1),
            Arguments.of("Joao", null, "Descrição", 50),
            Arguments.of("Joao", "Almeida", null, 25),
            Arguments.of("Joao", "Almeida", "Descrição", -12)
    );
  }

  @ParameterizedTest
  @MethodSource("invalidConstructorArguments")
  void shouldThrowExceptionWithInvalidConstructorArguments(String firstName, String lastName, String description, int jobYears) {
    assertThrows(IllegalArgumentException.class, () -> new Employee(firstName, lastName, description, jobYears));
  }

  public static Stream<Arguments> shouldThrowExceptionWithDifferentInvalidInputsParameterizedTest() {
    return Stream.of(
            Arguments.of(null, "Almeida", "Descrição", 1),
            Arguments.of("Joao", null, "Descrição", 50),
            Arguments.of("Joao", "Almeida", null, 25),
            Arguments.of("Joao", "Álmeida", "Descrição", -12)
    );
  }

  @ParameterizedTest
  @MethodSource("shouldThrowExceptionWithDifferentInvalidInputsParameterizedTest")
  void shouldThrowExceptionWithDifferentInvalidInputs(String firstName, String lastName, String description, int jobYears) throws IllegalArgumentException {
    // arrange
    Employee e1 = new Employee("FirstName", "LastName", "Description", 0);

    // act + assert
    if(firstName == null)
      assertThrows(IllegalArgumentException.class, () -> e1.setFirstName(firstName));
    else if(lastName == null)
      assertThrows(IllegalArgumentException.class, () -> e1.setLastName(lastName));
    else if(description == null)
      assertThrows(IllegalArgumentException.class, () -> e1.setDescription(description));
    else if(jobYears < 0)
      assertThrows(IllegalArgumentException.class, () -> e1.setJobYears(jobYears));
  }

  @Test
  void shouldReturnTrueForEqualObjects() {
    Employee e1 = new Employee("Joao", "Almeida", "Developer", 5);
    Employee e2 = new Employee("Joao", "Almeida", "Developer", 5);

    assertEquals(e1, e2);
    assertEquals(e1.hashCode(), e2.hashCode());
  }

  @Test
  void shouldReturnFalseForNonEqualObjects() {
    Employee e1 = new Employee("Joao", "Ferreira", "Developer", 5);
    Employee e2 = new Employee("Joana", "Almeida", "Developer", 5);

    assertNotEquals(e1, e2);
    assertNotEquals(e1.hashCode(), e2.hashCode());
  }
}
```
- **DatabaseLoader.java**:
  Updated this file to include the `jobYears` and new insertions. This process would make sure that
  the application could run without errors. Below is the updated code for it:

```java
public class DatabaseLoader implements CommandLineRunner { // <2>

  private final EmployeeRepository repository;

  @Autowired // <3>
  public DatabaseLoader(EmployeeRepository repository) {
    this.repository = repository;
  }

  @Override
  public void run(String... strings) throws IllegalArgumentException { // <4>
    this.repository.save(new Employee("Frodo", "Baggins", "ring bearer", 24));
  }
}
```

- **app.js**: Modified `app.js` to display `jobYears` in the employee list. The table provided when checking the
  `http://localhost:8080/` now properly employs the column for jobYears table:

```javascript
class EmployeeList extends React.Component{
  render() {
    const employees = this.props.employees.map(employee =>
            <Employee key={employee._links.self.href} employee={employee}/>
    );
    return (
            <table>
              <tbody>
                <tr>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Description</th>
                  <th>jobYears</th>
                </tr>
                {employees}
              </tbody>
            </table>
    )
  }
}
// end::employee-list[]

// tag::employee[]
class Employee extends React.Component{
  render() {
    return (
            <tr>
              <td>{this.props.employee.firstName}</td>
              <td>{this.props.employee.lastName}</td>
              <td>{this.props.employee.description}</td>
              <td>{this.props.employee.jobYears}</td>
            </tr>
    )
  }
}
```

**Debugging.**

After testing the integration of `jobYears` inside the application I ran the command
`./mvnw spring-boot:run` and then verified `http://localhost:8080/`. This made sure that all the updates
and changes were properly applied. I tried changing the input fields multiple times to check for any
possible errors or bugs, although none was found.


**Tagging.**

The following tags were created after the integrity of the code was confirmed:

```Terminal
git tag -a v1.2.0 -m "ca1-part1.1"
git push origin v1.2.0
```

## Part 1.2 - Development With Branches

### Goals and Requirements
On the second part I had to employ branching on the assignment so that I could create other branches
for developing before implementing the code into the main branch. The testing and debugging of the code
should therefore occur in these branches before merging them to the main branch. The utilization of
version control branching should be verifiable by the end of the development.

### Development

During this step I had to deploy new branches for each assignment, in order to test and debug in them,
so that the main branch remained stable, so that afterwards there could be a merge of the branch to
the main one.

**Develop new features in branches**

I utilized the `git branch` command followed by `email-field` so that a new branch was created.
I then switched to that branch with `git checkout email-field`, so that all changes in my code
would be developed in it and not the main one, diverging from its timeline. I confirmed the switch
again by running the same command as initially:

```Terminal
git branch email-field
git checkout email-field
git branch
```
**Creation of Issues**

I switched back to the main branch for the creation multiple issues for the developing process:

```Terminal
git checkout main
gh issue create --title "Create email field."
gh issue create --title "Create Unit Tests for Employee and validate its attributes."
gh issue create --title "Update the README.md file for Part 2."
gh issue create --title "Update necessary files."
gh issue create --title "Debug server and client."
gh issue create --title "Merge with main and create new tag."
gh issue create --title "Create new branch (fix-invalid-email)."
gh issue create --title "Debug fix-invalid-email branch."
gh issue create --title "Merge fix-invalid-email into main and tag."
```

**Deploying email field**
The addition of the `email` field was very similar to the implementation of `jobYears`. I switched
to the `email-field` branch and proceeded to add the email field to `Employee`. Its validation
was more demanding than `jobYears`. I created the getter and setter for it and then updated the app.js
and DatabaseLoader files to assure the integrity of the application. Here is a followup example of
the validations for the `setEmail` method:

```java
public void setEmail(String email) throws IllegalArgumentException {
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("The email isn't valid.");
		}
		if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$")) {
			this.email = email;
		} else {
			throw new IllegalArgumentException("The email isn't valid.");
		}
	}
```

**Unit Testing**: I verified the implementation by creating unit tests for the edge cases where
the email field could fail (null, empty or failing the other validations). Below is an example of
such usage:

```java
@Test
void shouldReturnExceptionIfInvalidEmailSetter() {
  //Arrange
  Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);

  //Act + Assert
  Exception exception = assertThrows(IllegalArgumentException.class, () -> e1.setEmail("º~*`"));
  assertEquals("The email isn't valid.", exception.getMessage());
}
```

**Debugging**: After being tested and validated, the addition of the email field went through debugging
both on the client and the server side.

**Merge the code with the master**

Implementing the email-field feature was done in a separate branch, so after it was debugged and tested
I had to merge it into the main branch as a new feature and with a new tag version. After the merging the changes
were pushed to the remote repository along with it.

```Terminal
# Commit the feature changes:
git add .
git commit -m "Added DatabaseLoaderTest and fixes to built folder."

git push 

# Switch to the main branch/merge:
git checkout main
git merge email-field

# Push to the main branch:
git push

# Tag:
git tag -a v1.3.0 -m "Email-field implementation."
git push origin v1.3.0
```

**Create a new branch to fix invalid email**

To fix the bug in the `Employee` class, a branch called `fix-invalid-email` was created.
Its implementation followed the logic presented at the email-field implementation. In this scenario
we had to guarantee that the email had to contain an `@` so the email setter within
`Employee` had to be upgraded:

```java
public void setEmail(String email) throws IllegalArgumentException {
    if (email == null || email.isEmpty()) {
        throw new IllegalArgumentException("The email can't be null or empty.");
    }
    if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$")) {
        this.email = email;
    } else {
        throw new IllegalArgumentException("The email isn't valid.");
    }
}
```

**End of assignment**

After careful implementation and consequent debug, the updates were merged into the master branch,
and the application version was updated to `v1.3.1`. After this process I tagged this version of the
repository with `ca1-part1.2`.

### Final Results

![Browser result on http://localhost:8080](https://i.postimg.cc/ZR3g53z8/Screenshot-2025-03-16-171747.png)
I began by creating the jobYears field at the initial phase of the assignment, followed by
the addition of the Email field. These two new features both had getters and setters created, aswell
as the remaining three fields, which were thoroughly upgraded and tested.

### Alternative Implementation

For this part of the assignment, I explored an alternative version control system:
Apache Subversion (SVN). Below, I present a comparison between SVN and Git,
and how SVN could be used to achieve the same goals as the required.

**Comparison of SVN and Git**

1. **Version Control Features**:

- Centralized vs. Distributed:  Git is distributed, with each user having a complete copy. SVN has a single central repository.
- Branching and Merging: Git is better at managing multiple lines of development. SVN is less flexible and more complex.
- Performance: Git is faster, especially for committing and branching, because it operates locally.
- History and Snapshots: Git stores snapshots of the project. SVN stores differences between versions (deltas).

2. **Using SVN for the Assignment**:

- Stable Versions: Use the master directory for stable versions.
- Feature Development: Create branches for new features (e.g., email-field).
- Bug Fixes: Create branches for bug fixes (e.g., fix-invalid-email).
- Merging and Tagging: Merge branches back into trunk and create tags for new versions.

**Creating a Repository**:

Open PowerShell as Administrator.
Navigate to the repository folder.
Run the following command:

```Terminal
svnadmin create C:\Users\Franc\Documents\svn-repo
```

**Import Initial Project**:
Navigate to project directory and run the following command
to import the project into the repository:

```Terminal
svn import C:\Users\Franc\OneDrive\Documents\Switch\II\DevOps\DevOpsRepo\devops-24-25-1241906\CA1 file:///C:/Users/Franc/Documents/svn-repo -m "Initial import"
```

**Checkout the Repository**:
Create a working copy by checking out the repository:

```Terminal
svn checkout file:///C:/Users/Franc/Documents/svn-repo C:\Users\Franc\Documents\working-copy
```

Using SVN for the Assignment
Now that you have your repository set up, you can proceed with the commands for
creating branches, merging, and tagging:

### Create a branch for the email field feature

```Terminal
svn copy file:///C:/Users/Franc/Documents/svn-repo/trunk file:///C:/Users/Franc/Documents/svn-repo/branches/email-field -m "Creating branch for email field feature"
```

**Create a branch for fixing invalid email bug**:
```Terminal
svn copy file:///C:/Users/Franc/Documents/svn-repo/trunk file:///C:/Users/Franc/Documents/svn-repo/branches/fix-invalid-email -m "Creating branch for fixing invalid email bug"
```

**Adding Support for the Email Field**:
Implement the email field in the email-field branch. Add unit tests for the email field validation.

**Debugging and Testing**:
Debug and test the server and client parts of the solution in the respective branches.

**Merging and Tagging**:
Merge the email-field branch back into master

```Terminal
svn merge --reintegrate file:///C:/Users/Franc/Documents/svn-repo/branches/email-field
svn commit -m "Merging email-field feature into trunk"
```

**Tag the new version:**

```Terminal
svn copy file:///C:/Users/Franc/Documents/svn-repo/trunk file:///C:/Users/Franc/Documents/svn-repo/tags/v1.3.0 -m "Tagging version 1.3.0"
```

**Merge the fix-invalid-email branch back into the trunk:**

```Terminal
svn merge --reintegrate file:///C:/Users/Franc/Documents/svn-repo/branches/fix-invalid-email
svn commit -m "Merging fix-invalid-email bug fix into trunk"
```

**Tag the new version:**

```Terminal
svn copy file:///C:/Users/Franc/Documents/svn-repo/trunk file:///C:/Users/Franc/Documents/svn-repo/tags/v1.3.1 -m "Tagging version 1.3.1"
```

**Final Tag:**

Mark the repository with the tag ca1-part1.2 at the end of the assignment.

```Terminal
svn copy file:///C:/Users/Franc/Documents/svn-repo/trunk file:///C:/Users/Franc/Documents/svn-repo/tags/ca1-part1.2 -m "Tagging final version ca1-part1.2"
```

## Part 2 - Build Tools with Gradle

### Goals and Requirements
- Downloading example application for a new folder called `part 2` inside `CA1` and removing `.gitignore`.
- Add a new task to execute the server.
- Add simple unit test/update gradle script, also adding junit 4.12 dependency.
- Add a new task of type `Copy` (copy `src` folder to a new `backup` folder).
- Add a new task of type `Zip` (copy the contents of `src` folder to a new `zip` folder).

### Development

2.1 **Importing the Application**

I began by opening some issues with some of the objectives that are fundamental steps to accomplish on the assignment.

```Terminal
gh issue create --title "Import the application and copy it inside a new CA1/part2 folder."
gh issue create --title "readme.md file and experiment with the application."
gh issue create --title "Add a task to execute the server."
gh issue create --title "Add a simple unit test."
gh issue create --title "Upgrade the Gradle script to execute the test." --body "Add junit 4.12 dependency."
gh issue create --title "Add a task of type Copy (copy src contents to a new backup folder)."
gh issue create --title "Add a task of type Zip (copy src contents to a new zip file)."
```

I started by creating a new folder inside `CA1` called `part2`for this part of the assignment,
and also importing the tutorial to the `devops` folder, removing the .gitignore from it. After this process
I copied the application into `part2`.

```Terminal
mkdir CA1/part2
git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git ~/devops-24-25-1241906/
cd devops-24-25-1241906\gradle_basic_demo
rm .gitignore
cp -r .\gradle_basic_demo\* CA1\part2\
```

2.2 **Fixing/Building Application**

I had to change the SDK version in the Project Structure settings to `corretto-17` so that the build would be successful.
I read the documentation inside readme.md and proceeded to run 3 different commands on 3 terminals:

1. Build the .jar file:

```Terminal
./gradlew build 
```

2. Run the server:

```Terminal
java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp 59001
```

3. Run the client:

```Terminal
./gradlew runClient
```

A small chat window appeared when I ran the client and I was also able to read that someone
had joined it through the Server terminal window.

2.3 **Creating runServer task**

The creation of a runServer was necessary to automatize its functioning, therefore, inside `build.gradle`,
the following task was created, following the runClient example:

```java
task runServer(type:JavaExec, dependsOn:classes){
    group = "DevOps"
    description = "Launches a server with the following port: <59001> "

    classpath = sourceSets.main.runtimeClasspath

    mainClass = 'basic_demo.ChatServerApp'

    args 'localhost', '59001'
}
```

2.4 **Unit Test**

Regarding the required simple unit test for the application, the following folder was created
and marked as a test folder through the Open Modules Settings inside intelliJ: `src/test/java` so that
when we generate tests they are directly inserted in it. I also had to insert the `junit 4.12` dependency
into the `build.gradle` file so that the test would run:

```java
testImplementation 'junit:junit:4.12'
```

`AppTest`:

```java
package basic_demo;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
@Test public void testAppHasAGreeting() {
    App classUnderTest = new App();
    assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
```

After this step I ran the test, achieving the following result:

```Terminal
> Task :compileJava UP-TO-DATE
> Task :processResources UP-TO-DATE
> Task :classes UP-TO-DATE
> Task :compileTestJava UP-TO-DATE
> Task :processTestResources NO-SOURCE
> Task :testClasses UP-TO-DATE
> Task :test
BUILD SUCCESSFUL in 539ms
4 actionable tasks: 1 executed, 3 up-to-date
5:29:06 PM: Execution finished ':test --tests "basic_demo.AppTest.testAppHasAGreeting"'.
```

2.5 **Add a new task of type Copy**

For this part of the assignment I opened `build.gradle` and added the following lines of code:

```java
task copySources(type: Copy) {
    group = "DevOps"
    description = "Copies the source files into a backup folder"

    from 'src'
    into 'backup'
}
```

I had to create a new folder inside `CA1/part2` called backup so that the task would run
without errors, thus copying the files from src into it. This was done in order to protect
any future damage to the src folder, maintaining a safe copy from it at the time.

2.6 **Add a new task of type Zip**

This part follows the example of the Copy one above, for zipping the files in `src` into a
new zipped file:

```java
task zipSources(type: Zip) {
    group = "DevOps"
    description = "Zips the source files into a zip file"

    from 'src'
    archiveFileName = 'src.zip'
    destinationDirectory = file('backup')
}
```

The resulting zipped `src.zip` was created in the destination directory `backup`,
to maintain coherence on the backed source files, therefore organizing the project files
in a single Zip Archive. This time I tried running the command `./gradlew zipSources` on the terminal
for testing:

[![Screenshot-2025-03-20-135824.png](https://i.postimg.cc/4ykYQwsD/Screenshot-2025-03-20-135824.png)](https://postimg.cc/LYD4HtqD)

These were the results of these two previous steps of the assignment (2.5, 2.6):

[![Screenshot-2025-03-20-140536.png](https://i.postimg.cc/rwWYhw17/Screenshot-2025-03-20-140536.png)](https://postimg.cc/hXDrvg30)

### Final Result

This part of the assignment gave me important insights on the Gradle build tool and knowledge on why
it is widely used among IT and other types of companies worldwide. I began by preparing my workspace with
some changes on files and updating the different utilized versions of Java and Gradle and organizing each
component (tests and sources) of the project. After creating multiple issues on GitHub with the main
proposed objectives I proceeded to create a unit test for one the classes and also creating tasks in Gradle,
testing and running them with positive results. Overall, it was an engaging experience that enhanced my understanding
of the Gradle build tool.

## Part 3 - Converting Basic Version to Gradle

### Goals and Requirements
- Branching.
- Dependencies.
- Basic implementation with Gradle (instead of Maven) with plugins.
- Version Tagging.

### Development

I started out by creating a new branch in my main repository, called `tut-basic-gradle` (shown below) so that we could
implement the Gradle build tool (Maven was being utilized beforehand).

```Terminal
git branch tut-basic-gradle
git checkout tut-basic-gradle
```

**Initial Steps**:
After branching out from the main into this newly created branch, I had to access https://start.spring.io to
create a new Gradle Spring Boot project with these dependencies: Rest Repositories; Thymeleaf; JPA; H2. The newly
generated zip file was consequently extracted into a new folder `CA1/part3`.

```Terminal
cd C:\Users\Franc\Downloads
New-Item -Path C:\Users\Franc\OneDrive\Documents\Switch\II\DevOps\DevOpsRepo\devops-24-25-1241906\CA1\part3 -ItemType Directory
Expand-Archive -Path react-and-spring-data-rest-basic.zip -DestinationPath C:\Users\Franc\OneDrive\Documents\Switch\II\DevOps\DevOpsRepo\devops-24-25-1241906\CA1\part3
```

By following the guidelines of the `README.adoc` file inside the basic folder, i ran the `./gradlew tasks` command
inside the terminal to download the necessary files to run Gradle version 8.13 nad check all available
tasks. I proceeded to delete the `src` folder, so that we copy the one from `part1`.

```Terminal
rm part3/react-and-spring-data-rest-basic/src
cp -r part1/basic/src part3/react-and-spring-data-rest-basic
```

Next, I also copied `webpack.config.js` and `package.json` and deleted the
`src/main/resources/static/built/` folder so that it is generated from the javascript
by the webpack tool.

```Terminal
cp part1/basic/webpack.config.js part3/react-and-spring-data-rest-basic
cp part1/basic/package.json part3/react-and-spring-data-rest-basic
rm part3/react-and-spring-data-rest-basic/src/main/resources/static/built/
rm -r part3/react-and-spring-data-rest-basic/src/main/resources/static/built/
```

After these steps I tried to start experimenting with the `./gradlew bootRun` command, but
some dependencies were still needed to handle the frontend part of this phase. I downloaded
the repository provided in the guideline, from `https://github.com/Siouan/frontend-gradle-plugin`
into `part3`. I also copied the `.gitignore` file from the basic folder to `part3/react-and-spring-data-rest-basic`.

```Terminal
cd CA1/part3/react-and-spring-data-rest-basic
git clone https://github.com/siouan/frontend-gradle-plugin.git

```

**Configuring the Plugin**

To implement Gradle into the project, similar steps to part1 were taken
in order to manage the frontend.
After cloning the repository I added the following line to `build.gradle`
so that it was able to handle the project's java version:

```java
id "org.siouan.frontend-jdk17" version "8.0.0"
```

The lines of code below were also added for managing the `Node.js` component
specifying its version, but also the scripts for assembling, cleaning and checking
the frontend:

```java
frontend {
nodeVersion = "16.20.2",
assembleScript = "run build",
cleanScript = "run clean",
checkScript = "run check"
}
```

After these two steps I updated the object section in `package.jason`:

```java
"scripts": {
"webpack": "webpack",
"build": "npm run webpack",
"check": "echo Checking frontend",
"clean": "echo Cleaning frontend",
"lint": "echo Linting frontend",
"test": "echo Testing frontend"
},
```

After trying to compile the project at this point I kept running into some errors,
with some dependencies on the classes inside `src/main/java` so I had to update the 
import statements from `javax.persistence` to `jakarta.persistence`.
- I tried running `./gradlew build` and it built successfully. 
- Next, `./gradlew bootRun` was executed and after accessing `http://localhost:8080/`, it contained
visual elements this time, proving that it was now ready for the next step.

**Gradle tasks**

To streamline the handling of project files, particularly in terms of distribution
and cleanup, two specialized Gradle tasks have been created: copyJar and cleanWebpack.

1 - **Task**: copyJar

**Goal**:
The `copyJar` task is designed to transfer the `.jar` file generated by the bootJar
task from its output location to a dist directory at the project's root. This ensures
that the correct, fully assembled `.jar` file is used for distribution, minimizing errors
and ensuring that deployments contain the latest build. I had to format the task to 
a more recent Gradle's configuration API, where a task is introduced by
"task.register", for example:

```java
tasks.register('copyJar', Copy) {
  dependsOn bootJar
  from bootJar.outputs
  into file("dist")
}
```

**Dependencies**:
This task relies on the successful completion of the bootJar task, ensuring that the
copy operation occurs only after the .jar file has been generated. This dependency
maintains a clear and reliable build sequence.

2 - **Task**: cleanWebpack

**Goal**:

The `cleanWebpack` task is responsible for removing all files generated by Webpack,
located in the `src/main/resources/static/built` directory. This keeps the build 
environment clean and ensures that only necessary files are included in each build,
preventing conflicts from outdated files.

**Configuration**:

```java
tasks.register('cleanWebpack', Delete) {
  delete 'src/main/resources/static/built'
}

tasks.named('clean') {
  dependsOn tasks.named('cleanWebpack')
}
```

**Dependencies**:
This task is set to run automatically before Gradle's standard clean task, integrating
it into the regular cleanup process.

**Task**: copyJar

`./gradlew copyJar`

**Outcome**:
- The `.jar` file generated by the bootJar task is copied from its output directory to the `dist` folder
located at the project's root. 
- Ensures that the correct, fully assembled `.jar` file is used for distribution. 
- Minimizes errors and guarantees that deployments contain 
the most current build.

**Task**: cleanWebpack

`./gradlew cleanWebpack`

Outcome:

- All files generated by Webpack, located in the `src/main/resources/static/built` directory, are deleted.
- Maintains a clean build environment.
- Ensures that only necessary files are included in each build, preventing potential conflicts from stale or outdated files.




