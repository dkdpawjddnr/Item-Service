package hello.productservie.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ProductRepositoryTest {

    ProductRepository productRepository = new ProductRepository();

    @AfterEach
    void afterEach(){
        productRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Product product = new Product("itemA", 10000, 10);

        // when
        Product savaProduct = productRepository.save(product);

        // then
        Product findProduct = productRepository.findById(product.getId());
        assertThat(findProduct).isEqualTo(savaProduct);
    }

    @Test
    void findAll(){
        // given
        Product product1 = new Product("item1", 10000, 10);
        Product product2 = new Product("item2", 20000, 20);

        productRepository.save(product1);
        productRepository.save(product2);
        // when
        List<Product> result = productRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }
    
    @Test
    void updateProduct(){
        // given
        Product product = new Product("item1", 10000, 10);

        Product savedProduct = productRepository.save(product);
        Long productId = savedProduct.getId();
        // when
        Product updateParam = new Product("item2", 20000, 30);
        productRepository.update(productId, updateParam);

        //then
        Product findProduct = productRepository.findById(productId);

        assertThat(findProduct.getProductName()).isEqualTo(updateParam.getProductName());
        assertThat(findProduct.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findProduct.getQuantity()).isEqualTo(updateParam.getQuantity());

    }
}
