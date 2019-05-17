package bojan.jovanoski.emt.lab1.Service;

import bojan.jovanoski.emt.lab1.Models.Category;
import bojan.jovanoski.emt.lab1.exceptions.CategoryAlreadyExistsException;
import bojan.jovanoski.emt.lab1.exceptions.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {
    Category addNewCategory(Category category) throws CategoryAlreadyExistsException;
    Category addNewCategory(String categoryName) throws CategoryAlreadyExistsException;
    List<Category> getCategories();
    Category update(Category category) throws CategoryNotFoundException;
    void delete(Category category) throws CategoryNotFoundException;
    Category getById(Long categoryID) throws CategoryNotFoundException;
    Category getByName(String name) throws CategoryNotFoundException;

}