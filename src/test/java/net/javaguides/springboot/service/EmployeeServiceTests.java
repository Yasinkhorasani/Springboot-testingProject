package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;

    @BeforeEach
    public void setup(){
     //   employeeRepository = Mockito.mock(EmployeeRepository.class);
     //   employeeService = new EmployeeServiceImpl(employeeRepository);
        employee= Employee.builder()
                .id(1L)
                .firstName("Yasin")
                .lastName("Khorasani")
                .email("yasinkhorasani@yahoo.com")
                .build();
    }
///////////////////////////////////////////////////////////////////////////////////////
    // Junit test for save employee method
        @DisplayName("Junit test for save employee method")
        @Test
        public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){

            //given
            given(employeeRepository.findByEmail(employee.getEmail()))
                    .willReturn(Optional.empty());
            given(employeeRepository.save(employee)).willReturn(employee);

            System.out.println(employeeRepository);
            System.out.println(employeeService);

            //when - action or the behavior that we are going test
            Employee savedEMployee = employeeService.saveEmployee(employee);
            System.out.println(savedEMployee);

            //then verify the Object
            assertThat(savedEMployee).isNotNull();
        }

////////////////////////////////////////////////////////////////////////////////////
    // Junit test for save employee method
    @DisplayName("Junit test for save employee method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException(){
        //given
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

       // given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when - action or the behavior that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,
                () ->{
                    employeeService.saveEmployee(employee);
                });
        //then
        verify(employeeRepository,never()).save(any(Employee.class));
    }
//////////////////////////////////////////////////////////////////////////////////////
    // Junit test for get All employees Method
    @DisplayName("Junit test for get All employees Method")
        @Test
        public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeesList(){
            Employee employee1= Employee.builder()
                    .id(2L)
                    .firstName("Hasti")
                    .lastName("Barahoei")
                    .email("hastiahoei@yahoo.com")
                    .build();
            //given
            given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

            //when - action or the behavior that we are going test
            List<Employee> employeeList = employeeService.getAllEmployees();

            //then verify the Object
            assertThat(employeeList).isNotNull();
            assertThat(employeeList.size()).isEqualTo(2);
        }

////////////////////////////////////////////////////////////////////////////////////////////
    // Junit test for get All employees Method negative Test
    @DisplayName("Junit test for get All employees Method(negative test)")
    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){
        Employee employee1= Employee.builder()
                .id(2L)
                .firstName("Hasti")
                .lastName("Barahoei")
                .email("hastiahoei@yahoo.com")
                .build();
        //given
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behavior that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then verify the Object
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

//////////////////////////////////////////////////////////////////////////////////////////
    //Junit test for get Employee by Id method
    @DisplayName("Junit test for get Employee by Id method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){

        //given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when
        Employee saveEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then
        assertThat(saveEmployee).isNotNull();
    }

//////////////////////////////////////////////////////////////////////////////////////
// Junit test for update Employee Methode
    @DisplayName("Junit test for update Employee Methode")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdateEmployee(){

        //given
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("yasinkhorasani0@gmail.com");
        employee.setFirstName("Yas");

        //when - action or the behavior that we are going test
        Employee updatedEmployee = employeeService.updeteEmployee(employee);

        //then verify the Object
        assertThat(updatedEmployee.getEmail()).isEqualTo("yasinkhorasani0@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Yas");
    }

///////////////////////////////////////////////////////////////////////////////////////////
    // Junit test for delete Employee methode
    @DisplayName("Junit test for delete Employee methode")
        @Test
        public void givenEmployeeId_whenDeleteEmployee_thenNothing(){

        Long employeeId = 1L;
            //given
            willDoNothing().given(employeeRepository).deleteById(employeeId);

            //when - action or the behavior that we are going test
            employeeService.deleteEmployee(employeeId);

            //then verify the Object
            verify(employeeRepository, times(1)).deleteById(employeeId);
        }
}
