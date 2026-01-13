package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.repository.FruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FruitServiceImp implements FruitService{

    private final FruitRepository fruitRepository;

    @Override
    public FruitResponse createFruit(FruitRequest request) {
        return null;
    }

    @Override
    public List<FruitResponse> findFruitByProviderName(String name) {
        return List.of();
    }
}
