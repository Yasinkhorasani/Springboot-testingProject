package net.javaguides.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITest {

    // injecting Mockmvc class to make HTTP request using perform() method
    @Autowired
    private MockMvc mockMvc;

    //injecting EmployeeRepository to use it,s method,s to perform different operations on database
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        employeeRepository.deleteAll();
    }

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSaveEmployee() throws Exception{

        //given setup
        Employee employee = Employee.builder()
                .firstName("Yasin")
                .lastName("Khorasani")
                .email("yasinkhorsani@yahoo.com")
                .build();

        //when - action or behavior that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then verify the result or output using assert statement
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employee.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    // Junit test for get All Employees REST API
    @DisplayName("Junit test for get All Employees REST API")
    @Test
    public void givenListOfEmployee_whenGetAllEmployees_thenReturnEmployeeList() throws Exception{

        //given
        List<Employee> listOfEmoloyees = new ArrayList<>();
        listOfEmoloyees.add(Employee.builder().firstName("Yasin").lastName("Khorasani").email("yasinkhorasani@yahoo.com").build());
        listOfEmoloyees.add(Employee.builder().firstName("Hasti").lastName("Ahoei").email("hastiahoei@yahoo.com").build());
        employeeRepository.saveAll(listOfEmoloyees);

        //when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then verify the Object
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmoloyees.size())));
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // positive scenario - valid employee ID
    // Junit test for Get employee by ID REST API
    @DisplayName("Junit test for Get employee by ID REST API")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{

        //given
        Employee employee = Employee.builder()
                .firstName("Yasin")
                .lastName("Khorasani")
                .email("yasinkhorsani@yahoo.com")
                .build();
        employeeRepository.save(employee);
        //when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        //then verify the Object
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    //////////////////////////////////////////////////////////////////////////////
    // negative scenario - valid employee ID
    // Junit test for Get employee by ID REST API
    @DisplayName("Junit test for Get employee by ID REST API")
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {

        //given
        Long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Yasin")
                .lastName("Khorasani")
                .email("yasinkhorsani@yahoo.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then verify the Object
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Junit test for update employee REST API -positive scenario
    @DisplayName("Junit test for update employee REST API -positive scenario")
    @Test
    public void givenUpdateEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {

        //given
        Employee savedemployee = Employee.builder()
                .firstName("Yasin")
                .lastName("Khorasani")
                .email("yasinkhorsani@yahoo.com")
                .build();
        employeeRepository.save(savedemployee);

        Employee updatedemployee = Employee.builder()
                .firstName("Yas")
                .lastName("Ahoei")
                .email("yasinkhorsani0@gmail.com")
                .build();

        //when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}",savedemployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedemployee)));

        //then verify the Object
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName",is(updatedemployee.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(updatedemployee.getLastName())))
                .andExpect(jsonPath("$.email",is(updatedemployee.getEmail())));
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Junit test for update employee REST API -negative scenario
    @DisplayName("Junit test for update employee REST API -negative scenario")
    @Test
    public void givenUpdateEmployee_whenUpdateEmployee_thenReturn404() throws Exception {

        //given
        long employeeId = 1L;
        Employee savedemployee = Employee.builder()
                .firstName("Yasin")
                .lastName("Khorasani")
                .email("yasinkhorsani@yahoo.com")
                .build();
        employeeRepository.save(savedemployee);

        Employee updatedemployee = Employee.builder()
                .firstName("Yas")
                .lastName("Ahoei")
                .email("yasinkhorsani0@gmail.com")
                .build();


        //when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}",employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedemployee)));

        //then verify the Object
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    ////////////////////////////////////////////////////////////////////////////////
    // Junit test for delete Employee REST API
    @DisplayName("Junit test for delete Employee REST API")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {

        //given precondition or setup
        Employee savedemployee = Employee.builder()
                .firstName("Yasin")
                .lastName("Khorasani")
                .email("yasinkhorsani@yahoo.com")
                .build();
        employeeRepository.save(savedemployee);

        //when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}",savedemployee.getId()));

        //then verify the Object
        response.andExpect(status().isOk())
                .andDo(print());
    }

}
