package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.controllers;

import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitRequest;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.DTO.FruitResponse;
import cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.services.FruitService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fruits")
@Validated
public class FruitController {
    private final FruitService fruitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FruitResponse createFruit(@RequestBody @Valid FruitRequest request) {
        return fruitService.createFruit(request);
    }

    @GetMapping("/by-provider")
    public List<FruitResponse> findFruitsByProviderName(
            @RequestParam @NotBlank(message = "Provider name must not be blank") String name) {
        return fruitService.findFruitsByProviderName(name);

    }
}
