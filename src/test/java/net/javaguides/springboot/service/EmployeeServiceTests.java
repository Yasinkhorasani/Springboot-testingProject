package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setup(){

     //   employeeRepository = Mockito.mock(EmployeeRepository.class);
     //   employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    // Junit test for save employee method
        @DisplayName("Junit test for save employee method")
        @Test
        public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){

            //given
            Employee employee= Employee.builder()
                    .id(1L)
                    .firstName("Yasin")
                    .lastName("Khorasani")
                    .email("yasinkhorasani@yahoo.com")
                    .build();
            BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                    .willReturn(Optional.empty());
            BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

            System.out.println(employeeRepository);
            System.out.println(employeeService);

            //when - action or the behavior that we are going test
            Employee savedEMployee = employeeService.saveEmployee(employee);
            System.out.println(savedEMployee);

            //then verify the Object
            Assertions.assertThat(savedEMployee).isNotNull();
        }

}
