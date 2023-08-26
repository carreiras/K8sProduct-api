package carreiras.com.github.k8s.productapi.utility;

import carreiras.com.github.k8s.dto.product.CategoryResponse;
import carreiras.com.github.k8s.dto.product.ProductRequest;
import carreiras.com.github.k8s.dto.product.ProductResponse;
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

    public static ProductResponse convertProductToProductResponse(Product product) {
        CategoryResponse categoryResponse = convertCategoryToCategoryResponse(product.getCategory());

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .identifier(product.getIdentifier())
                .category(categoryResponse)
                .build();
    }

    private static CategoryResponse convertCategoryToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
