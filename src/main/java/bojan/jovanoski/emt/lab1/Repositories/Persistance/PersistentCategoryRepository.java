package bojan.jovanoski.emt.lab1.Repositories.Persistance;

import bojan.jovanoski.emt.lab1.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PersistentCategoryRepository extends JpaRepository<Category,Long> {

    /**Get list of all categories */
    @Query("select category from Category category")
    List<Category> getCategories();

    /**Delete category by ID */
    @Query("delete from Category category where category.ID=:ID")
    void deleteCategory(@Param("ID") Long ID);

    /**Get category by ID */
    @Query("select category from Category category where category.ID=:ID")
    Optional<Category> getByID(@Param("ID") Long ID);

    /**Get category by NAME */
    @Query("select category from Category category where category.name=:name")
    Optional<Category> getByName(@Param("name") String name);

    /**Update categoryName */
    @Query("update Category category set category.name=:name where category.ID=:ID")
    void updateCategoryName(@Param("name") String name, @Param("ID") Long ID);

    /**Insert category*/
    @Transactional
    @Modifying
    @Query(value = "insert into category (category_name) values (:name)", nativeQuery = true)
    void addCategory(@Param("name") String name);


}
