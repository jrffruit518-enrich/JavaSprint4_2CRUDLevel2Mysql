package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderResponse;

import java.util.List;


public interface ProviderService {
    ProviderResponse createProvider(ProviderRequest request);
    List<ProviderResponse> findAllProviders();
    ProviderResponse updateProviderById(Long id,ProviderRequest request);
    void deleteProviderById(Long id) ;
}
