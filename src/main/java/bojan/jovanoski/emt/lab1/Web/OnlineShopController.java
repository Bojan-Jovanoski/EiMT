package bojan.jovanoski.emt.lab1.Web;


import bojan.jovanoski.emt.lab1.Models.Category;
import bojan.jovanoski.emt.lab1.Models.Manufacturer;
import bojan.jovanoski.emt.lab1.Models.Product;
import bojan.jovanoski.emt.lab1.Service.CategoryService;
import bojan.jovanoski.emt.lab1.Service.ManufacturerService;
import bojan.jovanoski.emt.lab1.Service.ProductService;
import bojan.jovanoski.emt.lab1.exceptions.CategoryNotFoundException;
import bojan.jovanoski.emt.lab1.exceptions.ManufacturerNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class OnlineShopController {
    private long counter;
    private long counterCat;
    private long counterMan;
    private long manufacturerID;
    private long categoryID;
    private ProductService productService;
    private CategoryService categoryService;
    private ManufacturerService manufacturerService;
    private Product product;

    public OnlineShopController(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService){
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;


        Product tempProduct = new Product();
        tempProduct.setName("Hyperdunk X");
        tempProduct.setDescription("Nike basketball shoe");
        tempProduct.setLinkToImg("https://c.static-nike.com/a/images/t_PDP_1280_v1/f_auto/bpuknsy73bmhmyj0agjj/hyperdunk-basketball-shoe-HlV5cq.jpg");
        tempProduct.setPrice(180);

        Manufacturer tempManufacturer = manufacturerService.getByName("Nike");
        Category tempCategory = categoryService.getByName("Shoes");

        if(!productService.getAllProducts().stream().anyMatch(product1 -> {
            return product1.equals(tempProduct);
        }))
            productService.addNewProduct(tempProduct, tempManufacturer.getID(), tempCategory.getID());

        Product tempProduct2 = new Product();
        tempProduct2.setName("AdiRose 9");
        tempProduct2.setDescription("Adidas basketball shoe");
        tempProduct2.setLinkToImg("https://images.nikedropshipping.com/images/201807/uploaded/8a82be0e5af733fab281857470d58014.jpg");
        tempProduct2.setPrice(135);

        Manufacturer tempManufacturer2 = manufacturerService.getByName("Adidas");
        Category tempCategory2 = categoryService.getByName("Shoes");
        if(!productService.getAllProducts().stream().anyMatch(product1 -> {
            return product1.equals(tempProduct2);
        }))
            productService.addNewProduct(tempProduct2, tempManufacturer2.getID(), tempCategory2.getID());


        product = new Product();
        manufacturerID = 1l;
        categoryID = 1l;
    }

    @GetMapping("products")
    public String products(Model model){
        model.addAttribute("productList", productService.getAllProducts());
        model.addAttribute("manufacturerList", manufacturerService.getAllManufacturers());
        model.addAttribute("categories", categoryService.getCategories());
        return "products";
    }

    @GetMapping("products/add")
    public String Addproducts(Model model){
        model.addAttribute("productList", productService.getAllProducts());
        model.addAttribute("manufacturerList", manufacturerService.getAllManufacturers());
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("product", product);
        return "productAdd";
    }

    @ExceptionHandler({ManufacturerNotFoundException.class, CategoryNotFoundException.class})
    @PostMapping("add")
    public String addProduct(HttpServletRequest request, Model model){
        String name = request.getParameter("name");

        long categoryID = Long.parseLong(request.getParameter("category.ID"));//BE CAREFUL
        long manufacturerID = Long.parseLong(request.getParameter("manufacturer.ID"));

        String description = request.getParameter("description");
        String imgLink = request.getParameter("linkToImg");
        Double price = Double.parseDouble(request.getParameter("price"));

        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setLinkToImg(imgLink);
        newProduct.setDescription(description);
        if(price <= 0)
            throw new IllegalArgumentException("Illegal price value: price = " + price.toString());
        newProduct.setPrice(price);
        /////dadadadad
        productService.addNewProduct(newProduct, manufacturerID, categoryID);

        model.addAttribute("productList", productService.getAllProducts());
        model.addAttribute("manufacturerList", manufacturerService.getAllManufacturers());
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("manufacturerID", manufacturerID);
        model.addAttribute("categoryID", categoryID);
        model.addAttribute("product", product);

        return "redirect:/productPage";
    }

    private long NextId() {return counter++;}
    private long NextManuId() {return counterMan++;}
    private long NextCatId() {return counterCat++;}

    @RequestMapping(value = "/products/{product_id}")
    public String goToProduct(@PathVariable("product_id") String product_ID, String ID, Model model) throws IOException{
        Long id = Long.parseLong(ID);
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @DeleteMapping("/")
    public String productDelete(HttpServletRequest request) {
        Long productID = Long.parseLong(request.getParameter("productID"));
        productService.deleteProduct(productService.getById(productID));
        return "redirect:/productPage/";
    }
}
