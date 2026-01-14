package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.entities.Fruit;

import java.util.List;

public interface FruitService {
    FruitResponse createFruit(FruitRequest request);
    List<FruitResponse> findFruitsByProviderName(String name);
    List<FruitResponse>findAllFruits();
}
