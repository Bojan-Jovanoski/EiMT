package bojan.jovanoski.emt.lab1.Repositories;

import bojan.jovanoski.emt.lab1.Models.Product;
import bojan.jovanoski.emt.lab1.exceptions.ProductAlreadyExistsException;
import bojan.jovanoski.emt.lab1.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepo {
    private long counterID;
    private List<Product> productList;

    @PostConstruct
    public void init(){
        counterID = 1l;
        productList = new ArrayList<>();
    }

    public void addProduct(Product product){
        if(productList.stream().anyMatch(product1 -> {
            return product1.equals(product);
        }))
            throw new ProductAlreadyExistsException();
        product.setId(getNextId());
        productList.add(product);
    }

    public void removeProduct(Product product){
        if(productList.stream().noneMatch(product1 -> {
            return product1.equals(product);
        }))
            throw new ProductNotFoundException();
        productList.remove(product);
    }

    public List<Product> getProductList(){
        return productList;
    }

    public Optional<Product> findById(Long ID){
        return productList.stream()
                .filter(product -> {
                    return product.getId()==ID;
                }).findAny();
    }

    public Optional<Product> findByName(String name){
        return productList.stream()
                .filter(product -> {
                    return product.getName().equals(name);
                }).findAny();
    }

    private long getNextId() {return counterID++;}
}
