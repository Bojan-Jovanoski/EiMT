package bojan.jovanoski.emt.lab1.Web.Restful;

import bojan.jovanoski.emt.lab1.Models.Product;
import bojan.jovanoski.emt.lab1.Service.CategoryService;
import bojan.jovanoski.emt.lab1.Service.ManufacturerService;
import bojan.jovanoski.emt.lab1.Service.ProductService;
import bojan.jovanoski.emt.lab1.exceptions.ProductNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rest/products/{product_id}")
public class ProductRestfullController {

    ProductService productService;
    CategoryService categoryService;
    ManufacturerService manufacturerService;

    public ProductRestfullController(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public Product goToProduct(@PathVariable("product_id") String product_id) throws IOException {
        Long pid = Long.parseLong(product_id);
        Optional<Product> product = productService.getAllProducts().stream().filter(product1 -> {
            return product1.getId() == pid;
        }).findAny();
        if (!product.isPresent())
            throw new ProductNotFoundException();
        return product.get();
    }
}