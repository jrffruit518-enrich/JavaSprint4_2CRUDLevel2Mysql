package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.entities.Provider;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.ResourceExistsException;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceImpTest {
    @Mock
    private ProviderRepository providerRepository;
    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private ProviderServiceImp providerServiceImp;
    @Test
    void create_whenNotExists_shouldCreateProvider() {

        ProviderRequest request = new ProviderRequest("GoodMan", "USA");
        Provider savedEntity = new Provider(1L, "GoodMan", "USA");

        when(providerRepository.existsByName("GoodMan")).thenReturn(false);
        when(providerRepository.save(any(Provider.class))).thenReturn(new Provider(1L, "GoodMan", "USA"));

        ProviderResponse response = providerServiceImp.createProvider(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("GoodMan", response.name());
        assertEquals("USA", response.country());
        verify(providerRepository).existsByName("GoodMan"); // 验证调用了新方法
        verify(providerRepository).save(any(Provider.class));
    }

    @Test
    void create_whenNameExists_shouldThrowProviderExistsException() {
        ProviderRequest request = new ProviderRequest("GoodMan", "USA");

        when(providerRepository.existsByName("GoodMan")).thenReturn(true);

        ResourceExistsException exception = assertThrows(
                ResourceExistsException.class,
                () -> providerServiceImp.createProvider(request)
        );

        assertEquals("Provider with name GoodMan already exists", exception.getMessage());
        verify(providerRepository).existsByName("GoodMan");
        verify(providerRepository, never()).save(any());
    }

    @Test
    void create_whenNameIsBlank_shouldThrowValidationException() {
        ProviderRequest request = new ProviderRequest("", "USA");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> providerServiceImp.createProvider(request)
        );

        assertEquals("Provider name cannot be blank", exception.getMessage());

        verify(providerRepository, never()).existsByName(any());
        verify(providerRepository, never()).save(any());
    }

    @Test
    void update_WhenIdExistsAndNameUnique_ShouldUpdateProvider() {
        // 1. Prepare existing entity and update request
        Long providerId = 1L;
        Provider existingProvider = new Provider(providerId, "OldName", "Spain");
        ProviderRequest updateRequest = new ProviderRequest("NewName", "France");

        // 2. Define Mock behaviors
        // Mock finding the original provider
        when(providerRepository.findById(providerId)).thenReturn(Optional.of(existingProvider));
        // Mock checking name conflict (it should not exist)
        when(providerRepository.existsByName("NewName")).thenReturn(false);
        // Mock saving the updated entity
        when(providerRepository.save(any(Provider.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 3. Execute
        ProviderResponse response = providerServiceImp.updateProviderById(providerId, updateRequest);

        // 4. Assertions
        assertNotNull(response);
        assertEquals("NewName", response.name());
        assertEquals("France", response.country());
        verify(providerRepository).save(any(Provider.class));
    }

    @Test
    void update_WhenIdNotFound_ShouldThrowException() {
        Long providerId = 99L;
        ProviderRequest request = new ProviderRequest("AnyName", "AnyCountry");

        when(providerRepository.findById(providerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> providerServiceImp.updateProviderById(providerId, request));

        verify(providerRepository, never()).save(any());
    }

    @Test
    void update_WhenNewNameAlreadyExists_ShouldThrowException() {
        // 1. Prepare data
        Long providerId = 1L;
        Provider existingProvider = new Provider(providerId, "OriginalName", "Spain");
        ProviderRequest request = new ProviderRequest("ConflictName", "Spain");

        // 2. Mock: Finding exists but new name is taken by someone else
        when(providerRepository.findById(providerId)).thenReturn(Optional.of(existingProvider));
        when(providerRepository.existsByName("ConflictName")).thenReturn(true);

        // 3. Assert
        assertThrows(ResourceExistsException.class,
                () -> providerServiceImp.updateProviderById(providerId, request));

        verify(providerRepository, never()).save(any());
    }

    @Test
    void delete_WhenIdExistsAndNoFruits_ShouldInvokeDelete() {
        // Arrange
        Long id = 1L;
        when(providerRepository.existsById(id)).thenReturn(true);
        when(fruitRepository.countByProviderId(id)).thenReturn(0L);

        // Act
        providerServiceImp.deleteProviderById(id);

        // Assert
        verify(providerRepository, times(1)).deleteById(id);
    }

    @Test
    void delete_WhenIdDoesNotExist_ShouldThrowNotFound() {
        // Arrange
        Long id = 99L;
        when(providerRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            providerServiceImp.deleteProviderById(id);
        });
        verify(providerRepository, never()).deleteById(anyLong());
    }

    @Test
    void delete_WhenFruitsAreAssociated_ShouldThrowBadRequest() {
        // Arrange
        Long id = 1L;
        when(providerRepository.existsById(id)).thenReturn(true);
        when(fruitRepository.countByProviderId(id)).thenReturn(5L); // 验收标准：有5个水果关联

        // Act & Assert
        // 这里我们假设你定义了一个 GenericBusinessException 或使用 IllegalArgumentException
        // 映射到 400 Bad Request
        assertThrows(RuntimeException.class, () -> {
            providerServiceImp.deleteProviderById(id);
        });

        verify(providerRepository, never()).deleteById(id);
    }



}
