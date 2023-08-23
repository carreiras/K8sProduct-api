package carreiras.com.github.k8s.productapi.service;

import org.springframework.stereotype.Service;

import carreiras.com.github.k8s.dto.product.ProductRequest;
import carreiras.com.github.k8s.productapi.entity.Category;
import carreiras.com.github.k8s.productapi.entity.Product;
import carreiras.com.github.k8s.productapi.exception.ResourceNotFoundException;
import carreiras.com.github.k8s.productapi.repository.CategoryRepository;
import carreiras.com.github.k8s.productapi.repository.ProductRepository;
import carreiras.com.github.k8s.productapi.utility.Convert;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final String CATEGORY_NOT_FOUND = "Categoria não encontrada.";
    private static final String PRODUCT_NOT_FOUND = "Produto não encontrado.";

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product create(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));

        Product product = Convert.convertProductRequestToProduct(productRequest, category);
        return productRepository.save(product);
    }

}
