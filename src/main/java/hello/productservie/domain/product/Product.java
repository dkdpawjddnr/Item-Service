package hello.productservie.domain.product;

import lombok.Data;

@Data
public class Product {

    private Long id;
    private String productName;
    private Integer price;
    private Integer quantity;
    public Product(){
    }

    public Product(String productName, Integer price, Integer quantity){
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
}
