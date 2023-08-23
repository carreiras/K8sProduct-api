package carreiras.com.github.k8s.productapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import carreiras.com.github.k8s.productapi.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
