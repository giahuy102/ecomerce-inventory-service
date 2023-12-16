import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ecomerce.ms.service.inventory.entity.Product;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProductRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public void save(Product product) {
        if (entityManager.find(Product.class, product.getId()) == null) {
            entityManager.persist(product);
        } else {
            entityManager.merge(product);
        }
    } 
}
