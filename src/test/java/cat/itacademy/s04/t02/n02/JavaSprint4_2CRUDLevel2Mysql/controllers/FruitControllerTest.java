package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.controllers;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.ProviderNotExistsException;
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
                .thenThrow(new ProviderNotExistsException("Provider does not exist."));

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

}
