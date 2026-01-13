package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FruitRequest(
        @NotBlank String name,
        @NotNull @Min(1) Integer weightInKilos,
        @NotNull Long providerId) {
}
