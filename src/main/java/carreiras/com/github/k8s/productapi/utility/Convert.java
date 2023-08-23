package carreiras.com.github.k8s.productapi.utility;

import carreiras.com.github.k8s.dto.product.ProductRequest;
import carreiras.com.github.k8s.productapi.entity.Category;
import carreiras.com.github.k8s.productapi.entity.Product;

public class Convert {
    

        public static Product convertProductRequestToProduct(ProductRequest productDto, Category category) {
        return Product.builder()
            .name(productDto.getName())
            .price(productDto.getPrice())
            .description(productDto.getDescription())
            .identifier(productDto.getIdentifier())
            .category(category)
            .build();
    }

}
