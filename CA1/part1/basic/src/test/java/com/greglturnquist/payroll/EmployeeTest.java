package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {


    @Test
    void shouldCreateEmployeeUsingProtectedConstructor() {
        // Arrange
        class TestEmployee extends Employee {
            public TestEmployee() {
                super();
            }
        }

        // Act
        Employee e1 = new TestEmployee();

        // Assert
        assertNotNull(e1);
    }

    @Test
    void shouldReturnTrueForEqualObjects() {
        //arrange
        Employee e1 = new Employee("jose", "almeida", "ring bearer", "12345@gmail.com", 12);
        Employee e2 = new Employee("jose", "almeida", "ring bearer", "12345@gmail.com", 12);

        //act + assert
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void shouldReturnFalseForNonEqualObjects() {
        //arrange
        Employee e1 = new Employee("Joao", "Ferreira", "Developer", "12345@gmail.com", 5);
        Employee e2 = new Employee("Joana", "Almeida", "Developer", "12345@gmail.com", 5);

        //act + assert
        assertNotEquals(e1, e2);
        assertNotEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void shouldReturnFalseWhenComparedToNull() {
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);

        assertNotEquals(null, e1);
    }

    @Test
    void shouldReturnFalseWhenComparedToDifferentClass() {
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);
        String differentClassObject = "Not an Employee";

        assertNotEquals(differentClassObject, e1);
    }

    @Test
    void shouldCreateValidEmployee() throws IllegalArgumentException {
        //arrange

        //act
        Employee e1 = new Employee("jose", "almeida", "ring bearer", "12345@gmail.com", 12);
        //assert
        assertNotNull(e1);
    }

    @Test
    void shouldReturnCorrectValuesFromGetters() {
        //arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);

        //act + assert
        assertEquals("Joao", e1.getFirstName());
        assertEquals("Almeida", e1.getLastName());
        assertEquals("Developer", e1.getDescription());
        assertEquals("12345@gmail.com", e1.getEmail());
        assertEquals(5, e1.getJobYears());
    }

    @Test
    void shouldSetCorrectValuesIdSetter() {
        //arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);

        //act
        e1.setId((long) 123456);

        //assert
        assertEquals(123456, e1.getId());
    }

    @Test
    void shouldReturnExceptionIfNullIdSetter() {
        //arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);

        //assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> e1.setId(null));
        assertEquals("The ID can't be null.", exception.getMessage());
    }

    @Test
    void shouldReturnExceptionIfInvalidEmailSetter() {
        //arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);

        //assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> e1.setEmail("º~*`"));
        assertEquals("The email isn't valid.", exception.getMessage());
    }

    public static Stream<Arguments> nullConstructorArguments() {
        return Stream.of(
                Arguments.of(null, "Almeida", "Descrição", "12345@gmail.com", 1),
                Arguments.of("Joao", null, "Descrição", "12345@gmail.com", 50),
                Arguments.of("Joao", "Almeida", null, "12345@gmail.com", 25),
                Arguments.of("Joao", "Almeida", "Descrição", null, -12),
                Arguments.of("Joao", "Almeida", "Descrição", "12345@gmail.com", -12)
        );
    }

    @ParameterizedTest
    @MethodSource("nullConstructorArguments")
    void shouldThrowExceptionWithNullConstructorArguments(String firstName, String lastName, String description, String email, int jobYears) {
        assertThrows(IllegalArgumentException.class, () -> new Employee(firstName, lastName, description, email, jobYears));
    }

    public static Stream<Arguments> emptyConstructorArguments() {
        return Stream.of(
                Arguments.of("", "Almeida", "Descrição", "12345@gmail.com", 1),
                Arguments.of("Joao", "", "Descrição", "12345@gmail.com", 50),
                Arguments.of("Joao", "Almeida", "", "12345@gmail.com", 25),
                Arguments.of("Joao", "Almeida", "Descrição", "", -12),
                Arguments.of("Joao", "Almeida", "Descrição", "12345@gmail.com", -12)
        );
    }

    @ParameterizedTest
    @MethodSource("emptyConstructorArguments")
    void shouldThrowExceptionWithEmptyConstructorArguments(String firstName, String lastName, String description, String email, int jobYears) {
        assertThrows(IllegalArgumentException.class, () -> new Employee(firstName, lastName, description, email, jobYears));
    }

    @Test
    public void testToString() {
        Employee employee = new Employee("John", "Doe", "Developer", "john.doe@example.com", 5);
        employee.setId(1L);

        String expected = "Employee{id=1, firstName='John', lastName='Doe', description='Developer', jobYears='5'}";
        String actual = employee.toString();

        assertEquals(expected, actual);
    }
}

//public static Stream<Arguments> shouldThrowExceptionWithDifferentInvalidInputsParameterizedTest() {
//    return Stream.of(
//            Arguments.of(null, "Almeida", "Descrição", "12345@gmail.com", 1),
//            Arguments.of("Joao", null, "Descrição", "12345@gmail.com", 50),
//            Arguments.of("Joao", "Almeida", null, "12345@gmail.com", 25),
//            Arguments.of("Joao", "Álmeida", "Descrição", "12345@gmail.com", -12)
//    );
//    @ParameterizedTest
//    @MethodSource("shouldThrowExceptionWithDifferentInvalidInputsParameterizedTest")
//    void shouldThrowExceptionWithDifferentInvalidInputs(String firstName, String lastName, String description, String email, int jobYears) throws IllegalArgumentException {
//        // arrange
//        Employee e1 = new Employee("FirstName", "LastName", "Description","12345@gmail.com", 0);
//
//        // act + assert
//        if(firstName == null)
//            assertThrows(IllegalArgumentException.class, () -> e1.setFirstName(firstName));
//        else if(lastName == null)
//            assertThrows(IllegalArgumentException.class, () -> e1.setLastName(lastName));
//        else if(description == null)
//            assertThrows(IllegalArgumentException.class, () -> e1.setDescription(description));
//        else if(email == null)
//            assertThrows(IllegalArgumentException.class, () -> e1.setEmail(email));
//        else if(jobYears < 0)
//            assertThrows(IllegalArgumentException.class, () -> e1.setJobYears(jobYears));
//    }


//    @Test
//    void shouldSetCorrectValuesFirstNameSetter() {
//        //arrange
//        Employee e1 = new Employee("Joao", "Almeida", "Developer","12345@gmail.com", 5);
//
//        //act
//        e1.setFirstName("José");
//
//        //assert
//        assertEquals("José", e1.getFirstName());
//    }

//    @Test
//    void shouldReturnExceptionInFirstNameNullSetter() {
//        //arrange
//        Employee e1 = new Employee("Joao", "Almeida", "Developer","12345@gmail.com", 5);
//
//        //assert
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> e1.setFirstName(null));
//        assertEquals("The firstName can't be null or empty.", exception.getMessage());
//    }

//    @Test
//    void shouldReturnExceptionInFirstNameEmptySetter() {
//        //arrange
//        Employee e1 = new Employee("Joao", "Almeida", "Developer","12345@gmail.com", 5);
//
//        //assert
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> e1.setFirstName(""));
//        assertEquals("The firstName can't be null or empty.", exception.getMessage());
//    }