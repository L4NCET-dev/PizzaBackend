package org.example.pizzabackend.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.example.pizzabackend.dto.UserFilter;
import org.example.pizzabackend.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> withFilter(UserFilter filter) {

        return ((root, query, criteriaBuilder) ->  {
            List<Predicate> predicates = new ArrayList<>();
            return  null;
        });
    }
}
