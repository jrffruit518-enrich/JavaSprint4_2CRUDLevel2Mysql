package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.ProviderRepository;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProviderServiceImp implements ProviderService{
    private final ProviderRepository providerRepository;

    @Override
    public ProviderResponse createProvider(ProviderRequest request) {
        return null;
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
