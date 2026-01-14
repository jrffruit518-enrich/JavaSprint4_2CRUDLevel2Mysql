package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.entities.Provider;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.ProviderExistsException;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceImpTest {
    @Mock
    private ProviderRepository providerRepository;

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

        ProviderExistsException exception = assertThrows(
                ProviderExistsException.class,
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



}
