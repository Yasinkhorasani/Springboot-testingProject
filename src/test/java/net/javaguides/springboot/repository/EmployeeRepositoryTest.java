package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import  org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    //Junit test für save employee operation
    @DisplayName("Junit test für save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSaveEmployeeObject(){

        //given
        Employee employee = Employee.builder()
                .firstName("Yasin")
                .lastName("Khorasani")
                .email("yasinkhorasani0@gmail.com")
                .build();

        //when - action or the behavior that we are going test
        Employee saveEmployee = employeeRepository.save(employee);

        //then verify the Object

        //Assertions.assertThat(saveEmployee).isNotNull();
        assertThat(saveEmployee).isNotNull();
        //Assertions.assertThat(saveEmployee.getId()).isGreaterThan(0);
        assertThat(saveEmployee.getId()).isGreaterThan(0);
    }
//////////////////////////////////////////////////
    // Junit test for get all employee operation
    @DisplayName("Junit test for get all employee operation")
    @Test
    public void givenEmployeeObject_whenFindAll_thenReturnFindAllEmployeeList(){
        //given
        Employee employee = Employee.builder()
                .firstName("Yasin")
                .lastName("Khorasani")
                .email("yasinkhorasani0@gmail.com")
                .build();

        Employee employee1 = Employee.builder()
                .firstName("Hasti")
                .lastName("Ahoei")
                .email("hastiahoei@gmail.com")
                .build();

         employeeRepository.save(employee);
         employeeRepository.save(employee1);

        //when - action or the behavior that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        //then verify the Object
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    ///////////////////////////////////////////////////////
    // Junit test for get employee by Id operation
    @DisplayName("Junit test for get employee by Id operation")
        @Test
        public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){

            //given
            Employee employee = Employee.builder()
                    .firstName("Yasin")
                    .lastName("Khorasani")
                    .email("yasinkhorasani0@gmail.com")
                    .build();
            employeeRepository.save(employee);

            //when - action or the behavior that we are going test
            Employee employeeById = employeeRepository.findById(employee.getId()).get();

            //then verify the Object
            assertThat(employeeById).isNotNull();
        }

       /////////////////////////////////////////////
    // Junit test for get employee by email operation
    @DisplayName("Junit test for get employee by email operation")
        @Test
        public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){

            //given
            Employee employee = Employee.builder()
                    .firstName("Yasin")
                    .lastName("Khorasani")
                    .email("yasinkhorasani0@gmail.com")
                    .build();
            employeeRepository.save(employee);

            //when - action or the behavior that we are going test
            Employee employeeByEmail = employeeRepository.findByEmail(employee.getEmail()).get();

            //then verify the Object
            assertThat(employeeByEmail).isNotNull();
        }

        ////////////////////////////////////////////
    // Junit test for update employee operation
        @DisplayName("Junit test for update employee operation")
        @Test
        public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdateEmployeeObject(){

            //given
            Employee employee = Employee.builder()
                    .firstName("Yasin")
                    .lastName("Khorasani")
                    .email("yasinkhorasani0@gmail.com")
                    .build();
            employeeRepository.save(employee);

            //when - action or the behavior that we are going test
            Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
            savedEmployee.setEmail("yasinkhorasani@yahoo.com");
            savedEmployee.setFirstName("Yas");
            Employee updatetEmployee = employeeRepository.save(savedEmployee);

            //then verify the Object
            assertThat(updatetEmployee.getEmail()).isEqualTo("yasinkhorasani@yahoo.com");
            assertThat(updatetEmployee.getFirstName()).isEqualTo("Yas");
        }

        // Junit test for delete employee operation
            @Test
            public void givenDeleteEmployeeObject_whenDelete_thenRemoveEmployeeObject(){

                //given
                Employee employee = Employee.builder()
                        .firstName("Yasin")
                        .lastName("Khorasani")
                        .email("yasinkhorasani0@gmail.com")
                        .build();
                employeeRepository.save(employee);

                //when - action or the behavior that we are going test
                employeeRepository.delete(employee);
                //then verify the Object
            }
}
