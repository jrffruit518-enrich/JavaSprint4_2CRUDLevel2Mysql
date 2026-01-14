package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.controllers;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.ResourceExistsException;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services.ProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProviderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProviderService providerService;

    @Test
    void createProvider_whenValidRequest_shouldReturnProvider() throws Exception {
        ProviderRequest request = new ProviderRequest("GoodMan", "USA");
        ProviderResponse response = new ProviderResponse(1L, "GoodMan", "USA");
        when(providerService.createProvider(any(ProviderRequest.class))).thenReturn(response);

        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("GoodMan"))
                .andExpect(jsonPath("$.country").value("USA"));
    }

    @Test
    void createProvider_whenNameExists_shouldReturn409() throws Exception {
        ProviderRequest request = new ProviderRequest("DuplicateName", "Spain");

        when(providerService.createProvider(any(ProviderRequest.class)))
                .thenThrow(new ResourceExistsException("Provider already exists."));

        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict()) // 验证 409
                .andExpect(jsonPath("$.message").value("Provider already exists."));
    }

    @Test
    void createProvider_whenNameIsEmpty_shouldReturn400() throws Exception {
        ProviderRequest request = new ProviderRequest("", "USA");

        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()) // 400
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void update_WhenValid_ShouldReturn200() throws Exception {
        // Prepare data
        Long id = 1L;
        ProviderRequest request = new ProviderRequest("UpdatedName", "Spain");
        ProviderResponse response = new ProviderResponse(id, "UpdatedName", "Spain");

        // Mock service behavior
        when(providerService.updateProviderById(eq(id), any(ProviderRequest.class)))
                .thenReturn(response);

        // Execute and Assert
        mockMvc.perform(put("/providers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedName"))
                .andExpect(jsonPath("$.country").value("Spain"));
    }

    @Test
    void update_WhenIdNotFound_ShouldReturn404() throws Exception {
        Long id = 99L;
        ProviderRequest request = new ProviderRequest("AnyName", "AnyCountry");

        // Mock service throwing ResourceNotFoundException
        when(providerService.updateProviderById(eq(id), any(ProviderRequest.class)))
                .thenThrow(new ResourceNotFoundException("No provider exists with ID: " + id));

        mockMvc.perform(put("/providers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_WhenNameAlreadyExists_ShouldReturn409() throws Exception {
        Long id = 1L;
        ProviderRequest request = new ProviderRequest("ExistingName", "France");

        // Mock service throwing ResourceExistsException
        when(providerService.updateProviderById(eq(id), any(ProviderRequest.class)))
                .thenThrow(new ResourceExistsException("Provider name already exists"));

        mockMvc.perform(put("/providers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict()); // Validates 409 Conflict
    }

    @Test
    void update_WhenNameIsEmpty_ShouldReturn400() throws Exception {
        Long id = 1L;
        // Sending empty name to trigger @Valid on DTO
        ProviderRequest request = new ProviderRequest("", "Spain");

        mockMvc.perform(put("/providers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // Validates 400 Bad Request
    }

    @Test
    void delete_WhenIdExistsAndNoRelation_ShouldReturn204() throws Exception {
        Long id = 1L;

        // Mock success behavior
        doNothing().when(providerService).deleteProviderById(id);

        mockMvc.perform(delete("/providers/{id}", id))
                .andExpect(status().isNoContent()); // 验证返回 204 No Content

        verify(providerService, times(1)).deleteProviderById(id);
    }

    @Test
    void delete_WhenIdDoesNotExist_ShouldReturn404() throws Exception {
        Long id = 99L;

        // Mock failure: Service throws ResourceNotFoundException
        doThrow(new ResourceNotFoundException("Not found"))
                .when(providerService).deleteProviderById(id);

        mockMvc.perform(delete("/providers/{id}", id))
                .andExpect(status().isNotFound()); // 验证返回 404
    }

    @Test
    void delete_WhenHasFruitRelation_ShouldReturn400() throws Exception {
        Long id = 1L;

        // Mock failure: Service throws IllegalArgumentException (which we map to 400)
        doThrow(new IllegalArgumentException("Provider has associated fruits"))
                .when(providerService).deleteProviderById(id);

        mockMvc.perform(delete("/providers/{id}", id))
                .andExpect(status().isBadRequest()); // 验证返回 400 Bad Request
    }
}
