package carreiras.com.github.k8s.productapi.service;

import static carreiras.com.github.k8s.productapi.utility.Convert.convertProductRequestToProduct;
import static carreiras.com.github.k8s.productapi.utility.Convert.convertProductToProductResponse;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import carreiras.com.github.k8s.dto.product.ProductRequest;
import carreiras.com.github.k8s.dto.product.ProductResponse;
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

    public ProductResponse create(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));

        Product product = convertProductRequestToProduct(productRequest, category);
        Product savedProduct = productRepository.save(product);

        return convertProductToProductResponse(savedProduct);
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(Convert::convertProductToProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse findById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));

        return convertProductToProductResponse(product);
    }

    public List<ProductResponse> findByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));

        List<Product> products = productRepository.findByCategoryId(category.getId());

        return products.stream()
                .map(Convert::convertProductToProductResponse)
                .collect(Collectors.toList());

    }

    public ProductResponse findByIdentifier(String identifier) {
        Product product = productRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));

        return convertProductToProductResponse(product);
    }

    public ProductResponse update(Long id, ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));

        findByIdentifier(productRequest.getIdentifier());

        Product updatedProduct = productRepository.findById(id)
                .map(f -> {
                    Product product = convertProductRequestToProduct(productRequest, category);
                    product.setId(id);
                    product.setIdentifier(f.getIdentifier());
                    productRepository.save(product);
                    return product;
                })
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));

        return convertProductToProductResponse(updatedProduct);
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
