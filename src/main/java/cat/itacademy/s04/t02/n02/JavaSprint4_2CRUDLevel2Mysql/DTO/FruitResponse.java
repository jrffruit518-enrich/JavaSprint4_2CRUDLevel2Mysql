package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO;

public record FruitResponse(
        Long id,
        String name,
        Integer weightInKilos,
        Long providerId,
        String providerName
) {
}

