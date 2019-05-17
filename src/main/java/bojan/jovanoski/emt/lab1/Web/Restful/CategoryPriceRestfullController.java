package bojan.jovanoski.emt.lab1.Web.Restful;

import bojan.jovanoski.emt.lab1.Models.Category;
import bojan.jovanoski.emt.lab1.Service.CategoryService;
import bojan.jovanoski.emt.lab1.Service.ManufacturerService;
import bojan.jovanoski.emt.lab1.Service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/product/category/{categoryId}/price")
public class CategoryPriceRestfullController {
    ProductService productService;
    CategoryService categoryService;
    ManufacturerService manufacturerService;

    public CategoryPriceRestfullController(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public Map<String, Double> getPrice(@PathVariable("categoryId") String categoryId) throws IOException {
        Category category = categoryService.getById(Long.parseLong(categoryId));

        return Collections.singletonMap("Total price", productService.getPrice(category));
    }
}
