package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.eneities.Fruit;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.eneities.Provider;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.ProviderNotExistsException;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.ProviderRepository;
import org.junit.jupiter.api.DisplayName;
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


        assertThrows(ProviderNotExistsException.class, () -> {
            fruitService.createFruit(request);
        });


        verify(fruitRepository, never()).save(any(Fruit.class));
    }

}
