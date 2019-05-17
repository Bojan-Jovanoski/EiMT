package bojan.jovanoski.emt.lab1.Web.Restful;

import bojan.jovanoski.emt.lab1.Models.Category;
import bojan.jovanoski.emt.lab1.Models.Product;
import bojan.jovanoski.emt.lab1.Service.CategoryService;
import bojan.jovanoski.emt.lab1.Service.ManufacturerService;
import bojan.jovanoski.emt.lab1.Service.ProductService;
import bojan.jovanoski.emt.lab1.exceptions.CategoryNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rest/product/category/{categoryId}")
public class CategoryProductsRestfullController {
    ProductService productService;
    CategoryService categoryService;
    ManufacturerService manufacturerService;

    public CategoryProductsRestfullController(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public List<Product> getProducts(@PathVariable("categoryId") String categoryId) throws IOException {
        Long cid = Long.parseLong(categoryId);
        Optional<Category> category = categoryService.getCategories().stream().filter(cat -> {
            return cat.getID() == cid;
        }).findAny();
        if (!category.isPresent())
            throw new CategoryNotFoundException();

        List<Product> products = new ArrayList<>();
        for (Product product: productService.getAllProducts()) {
            if(product.getCategory().equals(category.get()))
                products.add(product);
        }
        return products;
    }
}