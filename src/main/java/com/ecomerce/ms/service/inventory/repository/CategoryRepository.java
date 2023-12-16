import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ecomerce.ms.service.inventory.entity.Category;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CategoryRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public void save(Category category) {
        if (entityManager.find(Category.class, category.getId()) == null) {
            entityManager.persist(category);
        } else {
            entityManager.merge(category);
        }
    } 
}
