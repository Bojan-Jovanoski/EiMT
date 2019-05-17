package bojan.jovanoski.emt.lab1.Web.Restful;

import bojan.jovanoski.emt.lab1.Models.Product;
import bojan.jovanoski.emt.lab1.Service.CategoryService;
import bojan.jovanoski.emt.lab1.Service.ManufacturerService;
import bojan.jovanoski.emt.lab1.Service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/products")
public class AllProductsRestfullController {
    private ProductService productService;
    private CategoryService categoryService;
    private ManufacturerService manufacturerService;

    public AllProductsRestfullController(ProductService pService, CategoryService cService, ManufacturerService mService){
        this.productService = pService;
        this.categoryService = cService;
        this.manufacturerService = mService;
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
}