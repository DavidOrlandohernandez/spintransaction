package com.spin.transaction.specification;

import com.spin.transaction.entity.Transaction;
import com.spin.transaction.numbregeneratorservice.TransactionStatus;
import com.spin.transaction.numbregeneratorservice.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
public class TransactionSpecification {

    public static Specification<Transaction> filter(
            String accountId,
            TransactionStatus status,
            TransactionType type
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (accountId != null) {
                predicates.add(cb.equal(root.get("accountId"), accountId));
            }

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
