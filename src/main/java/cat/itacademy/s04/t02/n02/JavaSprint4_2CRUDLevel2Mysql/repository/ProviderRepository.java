package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.eneities.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider,Long> {
    boolean existsByName(String name);
    Optional<Provider>findByName(String name);

}
