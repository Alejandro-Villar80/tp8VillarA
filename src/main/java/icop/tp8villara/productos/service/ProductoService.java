package icop.tp8villara.productos.service;
import icop.tp8villara.productos.dto.ProductoRequestDTO;
import icop.tp8villara.productos.dto.ProductoResponseDTO;
import icop.tp8villara.productos.entity.Producto;
import icop.tp8villara.productos.exception.ProductoNotFoundException;
import icop.tp8villara.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        validar(dto);
        Producto guardado = productoRepository.save(toEntity(dto));
        return toResponseDTO(guardado);
    }

    public List<ProductoResponseDTO> listar() {
        return productoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProductoResponseDTO obtenerPorId(Long id) throws ProductoNotFoundException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        return toResponseDTO(producto);
    }

    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) throws ProductoNotFoundException {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        validar(dto);
        existente.setNombre(dto.getNombre());
        existente.setPrecio(dto.getPrecio());
        existente.setStock(dto.getStock());
        return toResponseDTO(productoRepository.save(existente));
    }

    public void eliminar(Long id) throws ProductoNotFoundException {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        productoRepository.delete(existente);
    }

    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Reglas de negocio (aplican en crear y actualizar)
    private void validar(ProductoRequestDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío");
        }
        if (dto.getPrecio() == null || dto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser nulo ni negativo");
        }
    }

    private Producto toEntity(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        return producto;
    }

    private ProductoResponseDTO toResponseDTO(Producto producto) {
        return new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getStock() > 0
        );
    }
}
