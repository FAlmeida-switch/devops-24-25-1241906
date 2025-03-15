package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DatabaseLoaderTest {

    @Test
    void run_shouldSaveEmployee() throws Exception {
// Arrange
        EmployeeRepository repository = mock(EmployeeRepository.class);
        DatabaseLoader databaseLoader = new DatabaseLoader(repository);

// Act
        databaseLoader.run();

// Assert
        verify(repository).save(argThat(employee ->
                "Frodo".equals(employee.getFirstName()) &&
                        "Baggins".equals(employee.getLastName()) &&
                        "ring bearer".equals(employee.getDescription()) &&
                        "12345@gmail.com".equals(employee.getEmail()) &&
                        employee.getJobYears() == 24));
    }
}
