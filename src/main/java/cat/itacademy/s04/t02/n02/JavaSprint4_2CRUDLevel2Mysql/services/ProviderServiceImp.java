package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.eneities.Provider;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.ProviderExistsException;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.ProviderRepository;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ProviderServiceImp implements ProviderService{
    private final ProviderRepository providerRepository;

    @Override
    public ProviderResponse createProvider(ProviderRequest request) {
        Objects.requireNonNull(request,"Request cannot be null");
        if (request.name()==null||request.name().isBlank()) {
            throw new IllegalArgumentException("Provider name cannot be empty");
        }
        if (providerRepository.existsByName(request.name())) {
            throw new ProviderExistsException("Provider already exists.");
        }
        Provider provider = new Provider(null,request.name(),request.country());

        Provider savedProvider = providerRepository.save(provider);

        ProviderResponse response = new ProviderResponse(savedProvider.getId(),
                savedProvider.getName(),
                savedProvider.getCountry());

        return response;
    }

   /* @Override
    public ProviderResponse findByName(String name) {
        return null;
    }*/

    @Override
    public ProviderResponse updateProviderByName(String name, ProviderRequest request) {
        return null;
    }

    @Override
    public void deleteProviderByName(String name) {

    }
}
