package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.controllers;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.ProviderResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services.ProviderService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    public ProviderResponse updateProviderById(
            @PathVariable Long id,
            @RequestBody @Valid ProviderRequest request
    ) {
        return providerService.updateProviderById(id, request);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProviderById(
            @PathVariable Long id) {
        providerService.deleteProviderById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<ProviderResponse> findAllProviders() {
        return providerService.findAllProviders();
    }


}
