package net.javaguides.springboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSaveEmployee() throws Exception{

        //given setup
        Employee employee = Employee.builder()
                .firstName("Yasin")
                .lastName("Khorasani")
                .email("yasinkhorsani@yahoo.com")
                .build();
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

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

    /////////////////////////////////////////////////////////////////////////////////////////
    // Junit test for get All Employees REST API
        @DisplayName("Junit test for get All Employees REST API")
        @Test
        public void givenListOfEmployee_whenGetAllEmployees_thenReturnEmployeeList() throws Exception{

        //given
        List<Employee> listOfEmoloyees = new ArrayList<>();
        listOfEmoloyees.add(Employee.builder().firstName("Yasin").lastName("Khorasani").email("yasinkhorasani@yahoo.com").build());
        listOfEmoloyees.add(Employee.builder().firstName("Hasti").lastName("Ahoei").email("hastiahoei@yahoo.com").build());
        given(employeeService.getAllEmployees()).willReturn(listOfEmoloyees);

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
            Long employeeId =1L;
            Employee employee = Employee.builder()
                    .firstName("Yasin")
                    .lastName("Khorasani")
                    .email("yasinkhorsani@yahoo.com")
                    .build();
            given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

            //when - action or the behavior that we are going test
            ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

            //then verify the Object
            response.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                    .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                    .andExpect(jsonPath("$.email", is(employee.getEmail())));
        }

        ///////////////////////////////////////////////////////////////////////////////
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
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

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
            long employeeId = 1L;
            Employee savedemployee = Employee.builder()
                    .firstName("Yasin")
                    .lastName("Khorasani")
                    .email("yasinkhorsani@yahoo.com")
                    .build();
            Employee updatedemployee = Employee.builder()
                    .firstName("Yas")
                    .lastName("Ahoei")
                    .email("yasinkhorsani0@gmail.com")
                    .build();
            given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedemployee));
            given(employeeService.saveEmployee(any(Employee.class)))
                    .willAnswer((invocation)-> invocation.getArgument(0));

            //when - action or the behavior that we are going test
            ResultActions response = mockMvc.perform(put("/api/employees/{id}",employeeId)
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
            Employee updatedemployee = Employee.builder()
                    .firstName("Yas")
                    .lastName("Ahoei")
                    .email("yasinkhorsani0@gmail.com")
                    .build();
            given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
            given(employeeService.saveEmployee(any(Employee.class)))
                    .willAnswer((invocation)-> invocation.getArgument(0));

            //when - action or the behavior that we are going test
            ResultActions response = mockMvc.perform(put("/api/employees/{id}",employeeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedemployee)));

            //then verify the Object
            response.andExpect(status().isNotFound())
                    .andDo(print());

        }

}
