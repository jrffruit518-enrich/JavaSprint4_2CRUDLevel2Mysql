package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO;

import jakarta.validation.constraints.NotBlank;

public record ProviderRequest(
        @NotBlank String name,
        @NotBlank String country
) {
}
