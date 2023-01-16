package net.javaguides.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

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
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        //when - action or behavior that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then verify the result or output using assert statement
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                CoreMatchers.is(employee.getEmail())));
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
        BDDMockito.given(employeeService.getAllEmployees()).willReturn(listOfEmoloyees);

        //when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then verify the Object
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(listOfEmoloyees.size())));

        }
}
