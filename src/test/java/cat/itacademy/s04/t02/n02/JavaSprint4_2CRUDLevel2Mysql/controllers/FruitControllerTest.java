package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.controllers;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.EntityNotFoundException;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

// --- 修正后的静态导入 ---
import java.util.List;
// For building the request (GET, POST, etc.)
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// For asserting the results (Status, JSON body, etc.)
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // 关键：使用 Servlet 版本的 post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // 关键：使用 MockMvcResultMatchers 的 jsonPath

@SpringBootTest
@AutoConfigureMockMvc
public class FruitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FruitService fruitService;

    @Test
    @DisplayName("Should return 201 Created when fruit data is valid")
    void createFruit_WhenValid_ShouldReturn201() throws Exception {
        // Prepare request and expected response
        FruitRequest request = new FruitRequest("Mango", 5, 1L);
        FruitResponse response = new FruitResponse(100L, "Mango", 5, 1L, "GoodMan");

        // Mock service behavior
        when(fruitService.createFruit(any(FruitRequest.class))).thenReturn(response);

        // Execute and Verify
        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.name").value("Mango"))
                .andExpect(jsonPath("$.providerName").value("GoodMan"));
    }

    @Test
    @DisplayName("Should return 404 Not Found when provider ID does not exist")
    void createFruit_WhenProviderNotFound_ShouldReturn404() throws Exception {
        // Prepare request
        FruitRequest request = new FruitRequest("Mango", 5, 99L);

        // Mock service to throw exception when provider is missing
        when(fruitService.createFruit(any(FruitRequest.class)))
                .thenThrow(new EntityNotFoundException("Provider does not exist."));

        // Execute and Verify
        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound()); // Matches 404
    }

    @Test
    @DisplayName("Should return 400 Bad Request when fruit name is blank")
    void createFruit_WhenNameIsBlank_ShouldReturn400() throws Exception {
        // Invalid request: name is empty
        FruitRequest request = new FruitRequest("", 5, 1L);

        // Execute and Verify: Validation happens before service call
        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getFruitsByProvider_WhenExists_ShouldReturn200() throws Exception {
        // Prepare mock data
        String providerName = "AppleCorp";
        FruitResponse response = new FruitResponse(1L, "Apple", 10, 1L, providerName);

        // Define Service behavior
        when(fruitService.findFruitsByProviderName(providerName)).thenReturn(List.of(response));

        // Perform GET request and Assert
        mockMvc.perform(get("/fruit/by-provider/{name}", providerName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[0].providerName").value(providerName));
    }

    @Test
    void getFruitsByProvider_WhenNotExists_ShouldReturn404() throws Exception {
        String providerName = "NonExistent";

        // Mock the service to throw the custom exception
        when(fruitService.findFruitsByProviderName(providerName))
                .thenThrow(new EntityNotFoundException("Provider not found"));

        // Perform GET request and Assert
        mockMvc.perform(get("/fruit/by-provider/{name}", providerName))
                .andExpect(status().isNotFound())
                // Check if GlobalExceptionHandler works
                .andExpect(jsonPath("$.message").value("Provider not found"))
                .andExpect(jsonPath("$.status").value(404));
    }

}
