package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderResponse;


public interface ProviderService {
    ProviderResponse createProvider(ProviderRequest request);
    //ProviderResponse findByName(String name);
    ProviderResponse updateProviderById(Long id,ProviderRequest request);
    void deleteProviderById(Long id) ;
}
