package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

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