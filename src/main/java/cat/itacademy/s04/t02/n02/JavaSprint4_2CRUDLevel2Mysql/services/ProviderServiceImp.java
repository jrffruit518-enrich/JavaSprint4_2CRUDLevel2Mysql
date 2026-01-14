package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.entities.Provider;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.ResourceExistsException;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.ProviderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProviderServiceImp implements ProviderService{
    private final ProviderRepository providerRepository;

    @Override
    @Transactional
    public ProviderResponse createProvider(ProviderRequest request) {
        if (providerRepository.existsByName(request.name())) {
            throw new ResourceExistsException("Provider already exists.");
        }
        Provider provider = new Provider(null,request.name(),request.country());

        Provider savedProvider = providerRepository.save(provider);

        ProviderResponse response = getProviderResponse(savedProvider);

        return response;
    }


    @Override
    @Transactional
    public ProviderResponse updateProviderById(Long id, ProviderRequest request) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No provider exists with the given ID: " + id));

        if (!provider.getName().equals(request.name())
                && providerRepository.existsByName(request.name())) {
            throw new ResourceExistsException("Provider name already exists: " + request.name());
        }
        provider.setName(request.name());
        provider.setCountry(request.country());
        Provider updatedProvider = providerRepository.save(provider);

        return getProviderResponse(updatedProvider);
    }
    @Override
    @Transactional
    public void deleteProviderByName(String name) {

    }

    private ProviderResponse getProviderResponse(Provider provider) {
        return new ProviderResponse(provider.getId(),
                provider.getName(),
                provider.getCountry());
    }
}
