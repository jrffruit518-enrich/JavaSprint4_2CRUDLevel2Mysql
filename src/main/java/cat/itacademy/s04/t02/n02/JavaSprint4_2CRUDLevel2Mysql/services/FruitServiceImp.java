package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.entities.Fruit;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.entities.Provider;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.ProviderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FruitServiceImp implements FruitService{

    private final FruitRepository fruitRepository;
    private final ProviderRepository providerRepository;

    @Override
    @Transactional
    public FruitResponse createFruit(FruitRequest request) {
    // Business Logic: The provider must exist in our system to associate it with a fruit
        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider does not exist with ID: " + request.providerId()));

        // Create new Fruit entity with the associated Provider object
        Fruit fruit = new Fruit(null, request.name(), request.weightInKilos(), provider);

        // Persist the entity to database
        Fruit savedFruit = fruitRepository.save(fruit);

        // Map the persisted entity back to a Response DTO
        return getFruitResponse(savedFruit);
    }

    @Override
    public List<FruitResponse> findFruitsByProviderName(String name) {
        if (!providerRepository.existsByName(name)) {
            throw new ResourceNotFoundException("Provider does not exist with name: " + name);
        };

        return fruitRepository.findByProviderName(name)
                .stream()
                .map(this::getFruitResponse)
                .toList();
    }



    private FruitResponse getFruitResponse(Fruit fruit) {
        return new FruitResponse(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos(),
                fruit.getProvider().getId(),
                fruit.getProvider().getName());
            }

}
