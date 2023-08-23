package carreiras.com.github.k8s.productapi.service;

import java.util.List;

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

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
    }

    public List<Product> findByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));

        return productRepository.findByCategoryId(category.getId());
    }

    public Product findByIdentifier(String identifier) {
        return productRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
    }

    public Product update(Long id, ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));

        findByIdentifier(productRequest.getIdentifier());

        return productRepository.findById(id)
                .map(f -> {
                    Product product = Convert.convertProductRequestToProduct(productRequest, category);
                    product.setId(id);
                    product.setIdentifier(f.getIdentifier());
                    productRepository.save(product);
                    return product;
                })
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
    }

    public void delete(Long id) {
        productRepository.findById(id)
                .map(m -> {
                    productRepository.delete(m);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
    }
}
