package bojan.jovanoski.emt.lab1.Service;

import bojan.jovanoski.emt.lab1.Models.Category;
import bojan.jovanoski.emt.lab1.Models.Product;
import bojan.jovanoski.emt.lab1.exceptions.CategoryNotFoundException;
import bojan.jovanoski.emt.lab1.exceptions.ManufacturerNotFoundException;
import bojan.jovanoski.emt.lab1.exceptions.ProductAlreadyExistsException;
import bojan.jovanoski.emt.lab1.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    Product addNewProduct(String name, long manufacturerID, long categoryID, String description,Double price, String linkToImg) throws ProductAlreadyExistsException, ManufacturerNotFoundException, CategoryNotFoundException;
    Product addNewProduct(Product product, long manufacturerID, long categoryID) throws ProductAlreadyExistsException;
    List<Product> getAllProducts();
    Product update(Product product) throws ProductNotFoundException;
    void deleteProduct(Product product) throws ProductNotFoundException;
    Product getById(Long productID) throws ProductNotFoundException;
    Product getByName(String name) throws ProductNotFoundException;
    Double getPrice(Category category) throws CategoryNotFoundException;
    void deleteById(Long id) throws ProductNotFoundException;
}
