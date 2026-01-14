package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO;

import jakarta.validation.constraints.NotBlank;

public record ProviderRequest(
        @NotBlank(message = "Provider name cannot be empty")
        String name,

        @NotBlank(message = "Country cannot be empty")
        String country
) {
}
