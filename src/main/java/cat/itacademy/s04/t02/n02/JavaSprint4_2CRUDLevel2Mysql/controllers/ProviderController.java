package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.controllers;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.eneities.Provider;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/providers")
public class ProviderController {

    private final ProviderService providerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProviderResponse createProvider(@RequestBody @Valid ProviderRequest request) {
        return providerService.createProvider(request);

    }

}
