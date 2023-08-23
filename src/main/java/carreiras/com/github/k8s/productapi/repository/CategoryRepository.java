package carreiras.com.github.k8s.productapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import carreiras.com.github.k8s.productapi.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
