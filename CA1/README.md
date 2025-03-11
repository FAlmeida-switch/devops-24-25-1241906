**Institution** Instituto Superior de Engenharia do Porto

**Program:** Switch Dev

**Course:** DevOps

**Author:** Francisco Almeida

## CA1 - Version Control with Git: 
### Part 1

##Table of Contents

- [Introduction](#introduction)
- [Environment Setup](#environment-setup)
- [Part 1: Development Without Branches](#part-1-development-without-branches)
    - [Goals and Requirements](#goals-and-requirements)
    - [Key Developments](#key-developments)
- [Conclusion](#conclusion)

## Introduction
- This technical report was created with the purpose of providing a detailed  assignment for the DevOps course. 
The assignment is divided into two parts: **Part 1**, utilizing version control with Git without branches, and **Part 2**, 
where I implement branching. The conclusion contemplates the results obtained throughout the 
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

## Part 1: Development Without Branches

### Goals and Requirements
-   Basic version control operations without branching (Master branch only).
-   New features introduction (i.e. `jobYears`).
- Version Tagging.

### Development

1. **Recursively copied the code contained inside the basic folder in Tutorial React.js and Spring Data REST Application
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
2. **Commit & Push.**

```Terminal
git add .
git commit -m "Added basic folder to CA1/part1."
git push
```

3. **Tagging.**

I tagged the initial setup as `v1.1.0` and subsequently pushed this tag to the remote repository:

```Terminal
git tag -a v1.1.0 -m "Initial version 1.1.0"
git push origin v1.1.0
```
4. **New Feature along with Issues.**

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

5. **Debugging.**

After testing the integration of `jobYears` inside the application I ran the command
`./mvnw spring-boot:run` and then verified `http://localhost:8080/`. This made sure that all the updates
and changes were properly applied. I tried changing the input fields multiple times to check for any
possible errors or bugs, although none was found. The following tags were created after the integrity of 
code was confirmed:

```Terminal
git tag -a v1.2.0 -m "ca1-part1.1"
git push origin v1.2.0
```



