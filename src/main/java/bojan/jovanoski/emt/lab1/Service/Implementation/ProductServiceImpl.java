package bojan.jovanoski.emt.lab1.Service.Implementation;

import bojan.jovanoski.emt.lab1.Models.Category;
import bojan.jovanoski.emt.lab1.Models.Manufacturer;
import bojan.jovanoski.emt.lab1.Models.Product;
import bojan.jovanoski.emt.lab1.Repositories.Persistance.PersistentProductRepository;
import bojan.jovanoski.emt.lab1.Service.CategoryService;
import bojan.jovanoski.emt.lab1.Service.ManufacturerService;
import bojan.jovanoski.emt.lab1.Service.ProductService;
import bojan.jovanoski.emt.lab1.exceptions.CategoryNotFoundException;
import bojan.jovanoski.emt.lab1.exceptions.ManufacturerNotFoundException;
import bojan.jovanoski.emt.lab1.exceptions.ProductAlreadyExistsException;
import bojan.jovanoski.emt.lab1.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private PersistentProductRepository productRepository;
    private ManufacturerService manufacturerService;
    private CategoryService categoryService;

    public ProductServiceImpl(PersistentProductRepository productRepository, ManufacturerService manufacturerService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;

        if (this.categoryService.getCategories().size() == 0) {
            this.categoryService.addNewCategory("Shoes");
            this.categoryService.addNewCategory("Jackets");
        }
        if (this.manufacturerService.getAllManufacturers().size() == 0) {
            this.manufacturerService.addNewManufacturer("Nike");
            this.manufacturerService.addNewManufacturer("Adidas");
        }
    }

    public Double getPrice(Category category){
        return productRepository.getPrice(category);
    }

    public Product addNewProduct(String name, long manufacturerID, long categoryID, String description, Double price, String linkToImg) throws ProductAlreadyExistsException, ManufacturerNotFoundException, CategoryNotFoundException{
        Optional<Manufacturer> manufacturer = manufacturerService
                .getAllManufacturers()
                .stream()
                .filter(v -> {
                    return v.getID() == manufacturerID;
                }).findAny();

        if(!manufacturer.isPresent()) throw new ManufacturerNotFoundException();

        Optional<Category> category = categoryService
                .getCategories()
                .stream()
                .filter(v -> {
                    return v.getID() == categoryID;
                }).findAny();
        if(!category.isPresent()) throw new CategoryNotFoundException();

        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setManufacturer(manufacturer.get());
        newProduct.setCategory(category.get());
        newProduct.setDescription(description);
        newProduct.setLinkToImg(linkToImg);

        if(productRepository.getProductList().stream().anyMatch(v -> {
            return v.equals(newProduct);
        })) {
            return update(newProduct);
        }

        productRepository.addProduct(name, description, linkToImg, price, manufacturerID, categoryID);
        return newProduct;
    }

    public Product addNewProduct(Product product, long manufacturerID, long categoryID) throws ProductAlreadyExistsException, ManufacturerNotFoundException, CategoryNotFoundException{
        Optional<Manufacturer> manufacturer = manufacturerService.getAllManufacturers().stream().filter(v -> { return v.getID() == manufacturerID; }).findAny();
        if(!manufacturer.isPresent()) throw new ManufacturerNotFoundException();

        Optional<Category> category = categoryService.getCategories().stream().filter(v -> { return v.getID() == categoryID; }).findAny();
        if(!category.isPresent()) throw new CategoryNotFoundException();

        product.setCategory(category.get());
        product.setManufacturer(manufacturer.get());

        productRepository.addProduct(product.getName(), product.getDescription(), product.getLinkToImg(), product.getPrice(), manufacturerID, categoryID);
        return product;
    }

    public List<Product> getAllProducts(){
        return productRepository.getProductList();
    }

    public Product update(Product product) throws ProductNotFoundException{
        Optional<Product> productOptional = productRepository.getProductList().stream().filter(v -> {
            return v.equals(product);
        }).findAny();
        if(!productOptional.isPresent()) throw new ProductNotFoundException();

        Product temp = productOptional.get();
        if(temp.getManufacturer() == null)
            temp.setManufacturer(product.getManufacturer());

        if(temp.getCategory() == null)
            temp.setCategory(product.getCategory());

        temp.setDescription(product.getDescription());
        temp.setName(product.getName());
        productRepository.updateProductDescription(temp.getId(), temp.getDescription());
        productRepository.updateProductName(temp.getId(), temp.getName());

        return temp;
    }

    public void deleteProduct(Product product) throws ProductNotFoundException{
        productRepository.deleteProduct(product);
    }

    public void deleteById(Long id) throws ProductNotFoundException{
        productRepository.deleteById(id);
    }

    public Product getById(Long productID) throws ProductNotFoundException{
        Optional<Product> product = productRepository.findById(productID);
        if(!product.isPresent())
            throw new ProductNotFoundException();
        return product.get();
    }

    public Product getByName(String name) throws ProductNotFoundException{
        Optional<Product> product = productRepository.getByName(name);
        if(!product.isPresent())
            throw new ProductNotFoundException();
        return product.get();
    }
}

