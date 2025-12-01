package org.example.pizzabackend.repository;

import org.example.pizzabackend.entity.PizzaVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaVariantRepository extends JpaRepository<PizzaVariant, Long> {

    List<PizzaVariant> findAllByPizzaId(Long pizzaId);

    Optional<PizzaVariant> findByPizzaIdAndSizeId(Long pizzaId, Long sizeId);
}
