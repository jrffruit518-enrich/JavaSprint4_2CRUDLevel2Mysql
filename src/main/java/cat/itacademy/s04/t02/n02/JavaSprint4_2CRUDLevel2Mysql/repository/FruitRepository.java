package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.entities.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FruitRepository extends JpaRepository<Fruit, Long> {
    List<Fruit> findByProviderId(Long providerId);
    List<Fruit> findByProviderName(String providerName);
}
