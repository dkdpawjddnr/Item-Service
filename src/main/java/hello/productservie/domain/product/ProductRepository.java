package hello.productservie.domain.product;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // Conponentscan의 대상이됨.
public class ProductRepository {

    private static final Map<Long, Product> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    public Product save(Product product) {
        product.setId(++sequence);
        store.put(product.getId(), product);
        return product;
    }

    public Product findById(Long id){
        return store.get(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long productId, Product updateParam){
        Product findProduct = findById(productId);
        findProduct.setProductName(updateParam.getProductName());
        findProduct.setPrice(updateParam.getPrice());
        findProduct.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }
}
