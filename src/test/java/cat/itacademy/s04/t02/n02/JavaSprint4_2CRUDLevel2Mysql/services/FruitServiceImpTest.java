package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.entities.Fruit;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.entities.Provider;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.ProviderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FruitServiceImpTest {

    @Mock
    private FruitRepository fruitRepository;

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private FruitServiceImp fruitService;

    @Test
    @DisplayName("Should create fruit and return 201 when provider exists")
    void createFruit_WhenProviderExists_ShouldReturnFruitResponse() {

        Long providerId = 1L;
        FruitRequest request = new FruitRequest("Apple", 10, providerId);
        Provider mockProvider = new Provider(providerId, "GoodMan", "USA");


        Fruit savedFruit = new Fruit(100L, "Apple", 10, mockProvider);


        when(providerRepository.findById(providerId)).thenReturn(Optional.of(mockProvider));

        when(fruitRepository.save(any(Fruit.class))).thenReturn(savedFruit);


        FruitResponse response = fruitService.createFruit(request);


        assertNotNull(response);
        assertEquals("Apple", response.name());
        assertEquals(providerId, response.providerId());
        assertEquals("GoodMan", response.providerName());


        verify(providerRepository).findById(providerId);
        verify(fruitRepository).save(any(Fruit.class));
    }

    @Test
    @DisplayName("Should throw ProviderNotExistsException when provider ID does not exist")
    void createFruit_WhenProviderNotExists_ShouldThrowException() {

        Long nonExistentId = 99L;
        FruitRequest request = new FruitRequest("Banana", 5, nonExistentId);


        when(providerRepository.findById(nonExistentId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> {
            fruitService.createFruit(request);
        });


        verify(fruitRepository, never()).save(any(Fruit.class));
    }

    @Test
    @DisplayName("Should return fruit list when provider name exists")
    void findFruitsByProviderName_WhenProviderExists_ShouldReturnList() {
        // 1. Prepare Data
        String providerName = "GoodMan";
        Provider mockProvider = new Provider(1L, providerName, "USA");

        // Create a list of fruits associated with this provider
        List<Fruit> mockFruits = List.of(
                new Fruit(101L, "Apple", 10, mockProvider),
                new Fruit(102L, "Banana", 20, mockProvider)
        );

        // 2. Mocking behaviors
        // First, check if provider exists by name
        when(providerRepository.existsByName(providerName)).thenReturn(true);
        // Then, return the fruits for this provider
        when(fruitRepository.findByProviderName(providerName)).thenReturn(mockFruits);

        // 3. Execute
        List<FruitResponse> result = fruitService.findFruitsByProviderName(providerName);

        // 4. Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Apple", result.get(0).name());
        assertEquals(providerName, result.get(0).providerName());

        // 5. Verify interactions
        verify(providerRepository).existsByName(providerName);
        verify(fruitRepository).findByProviderName(providerName);
    }

    @Test
    @DisplayName("Should throw ProviderNotExistsException when provider name does not exist")
    void findFruitsByProviderName_WhenProviderDoesNotExist_ShouldThrowException() {
        // 1. Prepare Data
        String unknownProvider = "UnknownCorp";

        // 2. Mocking: Provider does not exist
        when(providerRepository.existsByName(unknownProvider)).thenReturn(false);

        // 3. Execution & Assertion
        assertThrows(ResourceNotFoundException.class, () -> {
            fruitService.findFruitsByProviderName(unknownProvider);
        });

        // 4. Verify: Should never call the fruit repository if provider is missing
        verify(fruitRepository, never()).findByProviderName(anyString());
    }


}
