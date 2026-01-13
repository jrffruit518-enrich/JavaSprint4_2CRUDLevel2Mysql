package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.eneities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="fruits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private  String name;

    @NotNull
    @Min(1)
    private  Integer weightInKilos;


    @ManyToOne
    @JoinColumn(name = "provider_id",nullable = false)
    @NotNull
    private Provider provider;
}
