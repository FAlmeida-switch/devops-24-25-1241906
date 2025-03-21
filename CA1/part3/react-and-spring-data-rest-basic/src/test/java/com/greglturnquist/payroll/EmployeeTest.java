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
    void shouldReturnTrueWhenComparedToItself() {
        //Arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);

        //Act + Assert
        assertEquals(e1, e1);
    }


    @Test
    void shouldReturnTrueForEqualObjects() {
        //Arrange
        Employee e1 = new Employee("jose", "almeida", "ring bearer", "12345@gmail.com", 12);
        Employee e2 = new Employee("jose", "almeida", "ring bearer", "12345@gmail.com", 12);

        //Act + Assert
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void shouldReturnFalseForNonEqualObjects() {
        //Arrange
        Employee e1 = new Employee("Joao", "Ferreira", "Developer", "12345@gmail.com", 5);
        Employee e2 = new Employee("Joana", "Almeida", "Developer", "12345@gmail.com", 5);

        //Act + Assert
        assertNotEquals(e1, e2);
        assertNotEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void shouldReturnFalseWhenComparedToNull() {
        //Arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);

        //Act + Assert
        assertFalse(e1.equals(null));
    }

    @Test
    void shouldReturnFalseWhenComparedToDifferentClass() {
        //Arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);
        String differentClassObject = "Not an Employee";

        //Act + Assert
        assertFalse(e1.equals(differentClassObject));
    }


    @Test
    void shouldCreateValidEmployee() throws IllegalArgumentException {
        //Arrange
        Employee e1 = new Employee("jose", "almeida", "ring bearer", "12345@gmail.com", 12);

        //Act + Assert
        assertNotNull(e1);
    }

    @Test
    void shouldReturnFalseForDifferentId() {
        //Arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);
        e1.setId(1L);
        Employee e2 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);
        e2.setId(2L);

        //Act + Assert
        assertNotEquals(e1, e2);
    }

    @Test
    void shouldReturnFalseForDifferentLastName() {
        //Arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);
        Employee e2 = new Employee("Joao", "Ferreira", "Developer", "12345@gmail.com", 5);

        //Act + Assert
        assertNotEquals(e1, e2);
    }

    @Test
    void shouldReturnFalseForDifferentDescription() {
        //Arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);
        Employee e2 = new Employee("Joao", "Almeida", "Manager", "12345@gmail.com", 5);

        //Act + Assert
        assertNotEquals(e1, e2);
    }

    @Test
    void shouldReturnCorrectValuesFromGetters() {
        //Arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);

        //Act + Assert
        assertEquals("Joao", e1.getFirstName());
        assertEquals("Almeida", e1.getLastName());
        assertEquals("Developer", e1.getDescription());
        assertEquals("12345@gmail.com", e1.getEmail());
        assertEquals(5, e1.getJobYears());
    }

    @Test
    void shouldSetCorrectValuesIdSetter() {
        //Arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);

        //Act
        e1.setId((long) 123456);

        //Assert
        assertEquals(123456, e1.getId());
    }

    @Test
    void shouldReturnExceptionIfNullIdSetter() {
        //Arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);

        //Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> e1.setId(null));
        assertEquals("The ID can't be null.", exception.getMessage());
    }

    @Test
    void shouldReturnExceptionIfInvalidEmailSetter() {
        //Arrange
        Employee e1 = new Employee("Joao", "Almeida", "Developer", "12345@gmail.com", 5);

        //Act + Assert
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
        //Arrange
        Employee employee = new Employee("John", "Doe", "Developer", "john.doe@example.com", 5);

        //Act
        employee.setId(1L);

        String expected = "Employee{id=1, firstName='John', lastName='Doe', description='Developer', jobYears='5'}";
        String actual = employee.toString();

        //Assert
        assertEquals(expected, actual);
    }
}