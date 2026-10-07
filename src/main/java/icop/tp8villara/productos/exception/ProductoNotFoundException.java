package icop.tp8villara.productos.exception;

public class ProductoNotFoundException extends Exception {

    public ProductoNotFoundException(Long id) {
        super("No existe un producto con id " + id);
    }
}
