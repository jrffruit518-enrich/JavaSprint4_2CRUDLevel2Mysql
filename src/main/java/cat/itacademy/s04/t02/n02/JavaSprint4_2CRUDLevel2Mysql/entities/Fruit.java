package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.entities;

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
    @NotBlank(message = "Fruit name is mandatory")
    private String name;

    @NotNull(message = "Weight is mandatory")
    @Min(value = 1, message = "Weight must be at least 1kg")
    private Integer weightInKilos;

    /**
     * Establish a Many-to-One relationship with Provider.
     * We use the Provider entity object instead of a simple Long ID
     * to leverage JPA's object-relational mapping capabilities.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    @NotNull(message = "Provider must be specified")
    private Provider provider;
}
