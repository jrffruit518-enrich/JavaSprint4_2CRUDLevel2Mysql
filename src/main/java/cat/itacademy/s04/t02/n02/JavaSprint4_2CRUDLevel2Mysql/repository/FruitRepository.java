package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.eneities.Fruit;

import java.util.List;

public interface FruitRepository<Fruit, Long> {
    boolean existsByName(String name);
    List<Fruit> findByProviderId(Long providerId);
    List<Fruit> findByProviderName(String providerName);
}
