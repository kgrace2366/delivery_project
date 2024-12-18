package com.delivery_project.repository.jpa;

import com.delivery_project.entity.Category;

import com.delivery_project.repository.implement.CategoryRepositoryCustom;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>,
    CategoryRepositoryCustom {

    boolean existsByName(String name);
}
