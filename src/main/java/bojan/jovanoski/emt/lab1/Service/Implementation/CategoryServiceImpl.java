package bojan.jovanoski.emt.lab1.Service.Implementation;

import bojan.jovanoski.emt.lab1.Models.Category;
import bojan.jovanoski.emt.lab1.Repositories.Persistance.PersistentCategoryRepository;
import bojan.jovanoski.emt.lab1.Service.CategoryService;
import bojan.jovanoski.emt.lab1.exceptions.CategoryAlreadyExistsException;
import bojan.jovanoski.emt.lab1.exceptions.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private PersistentCategoryRepository categoryRepository;
    //private CategoryRepository categoryRepository;

    public CategoryServiceImpl(PersistentCategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category addNewCategory(Category category) throws CategoryAlreadyExistsException{
        if(categoryRepository.getCategories().stream().anyMatch(v -> {
            return v.getName().equals(category.getName());
        }))
            throw new CategoryAlreadyExistsException();

        categoryRepository.addCategory(category.getName());
        return category;
    }

    public Category addNewCategory(String categoryName) throws CategoryAlreadyExistsException{
        Category category = new Category();
        category.setName(categoryName);

        if(categoryRepository.getCategories().stream().anyMatch(v -> {
            return v.equals(category);
        }))
            throw new CategoryAlreadyExistsException();

        categoryRepository.addCategory(categoryName);
        return category;
    }

    public List<Category> getCategories(){
        return categoryRepository.getCategories();
    }

    public Category update(Category category) throws CategoryNotFoundException {
        return null;
    }

    public void delete(Category category) throws CategoryNotFoundException{
        if(categoryRepository.getCategories().stream().noneMatch(v -> {
            return v.equals(category);
        })) throw new CategoryNotFoundException();

        categoryRepository.deleteCategory(category.getID());
    }

    public Category getById(Long categoryID) throws CategoryNotFoundException{
        Optional<Category> category = categoryRepository.getByID(categoryID);
        if(!category.isPresent()) throw new CategoryNotFoundException();
        return category.get();
    }

    public Category getByName(String name) throws CategoryNotFoundException{
        Optional<Category> category = categoryRepository.getByName(name);
        if(!category.isPresent()) throw new CategoryNotFoundException();
        return category.get();
    }
}
