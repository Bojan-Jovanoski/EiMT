package bojan.jovanoski.emt.lab1.dto;

import bojan.jovanoski.emt.lab1.Models.Product;

public class ProductDTO {
    private Long id;
    private String name;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
