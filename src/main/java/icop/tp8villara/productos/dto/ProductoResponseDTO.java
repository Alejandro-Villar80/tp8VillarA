package icop.tp8villara.productos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private Boolean disponible;
}
