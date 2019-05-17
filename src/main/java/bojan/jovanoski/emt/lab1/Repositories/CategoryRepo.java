package bojan.jovanoski.emt.lab1.Repositories;

import bojan.jovanoski.emt.lab1.Models.Category;
import bojan.jovanoski.emt.lab1.exceptions.CategoryAlreadyExistsException;
import bojan.jovanoski.emt.lab1.exceptions.CategoryNotFoundException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepo {
    private long counterID;
    private List<Category> categories;
    @PostConstruct
    public void init(){
        counterID = 1l;
        categories = new ArrayList<>();

        Category c1 = new Category();
        c1.setID(getNextCatId());
        c1.setName("Shoes");
        categories.add(c1);

        Category c2 = new Category();
        c2.setID(getNextCatId());
        c2.setName("Jackets");
        categories.add(c2);
    }

    public List<Category> getCategories(){
        return categories;
    }

    public void addCategory(Category category){
        if(categories.stream().anyMatch(category1 -> {
            return category1.getName().equals(category.getName());
        }))
            throw new CategoryAlreadyExistsException();

        category.setID(getNextCatId());
        categories.add(category);
    }

    public void deleteCategory(Category category){
        if(categories.stream().noneMatch(category1 -> {
            return category1.getID()==category.getID();
        }))
            throw new CategoryNotFoundException();

        categories.remove(category);
    }

    public Optional<Category> findById(Long ID){
        return categories.stream()
                .filter(category -> {
                    return category.getID()==ID;
                }).findAny();
    }

    public Optional<Category> findByName(String name){
        return categories.stream()
                .filter(category -> {
                    return category.getName().equals(name);
                }).findAny();
    }

    private long getNextCatId() {return counterID++;}

}