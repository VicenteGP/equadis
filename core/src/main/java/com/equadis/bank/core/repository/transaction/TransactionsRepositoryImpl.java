package com.equadis.bank.core.repository.transaction;

import com.equadis.bank.core.dto.transaction.TransactionSearchFilter;
import com.equadis.bank.core.dto.transaction.TransactionSearchResponse;
import com.equadis.bank.core.model.transaction.Transaction;
import com.equadis.bank.core.utils.mappers.TransactionMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionsRepositoryImpl implements TransactionsRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Override
    public Page<TransactionSearchResponse> findBySearchCriteria(TransactionSearchFilter filter, Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> root = criteriaQuery.from(Transaction.class);

        List<Predicate> searchCriteria = new ArrayList<>();

        if (Objects.nonNull(filter.type())) {
            searchCriteria.add(criteriaBuilder.equal(root.get("type"), filter.type()));
        }

        if (Objects.nonNull(filter.category())) {
            searchCriteria.add(criteriaBuilder.equal(root.get("category"), filter.category()));
        }

        if (Objects.nonNull(filter.from()) && Objects.nonNull(filter.to())) {
            searchCriteria.add(criteriaBuilder.between(root.get("timestamp"), filter.from(), filter.to()));
        }

        else if (Objects.nonNull(filter.from())) {
            searchCriteria.add(criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), filter.from()));
        }

        else if (Objects.nonNull(filter.to())) {
            searchCriteria.add(criteriaBuilder.lessThanOrEqualTo(root.get("timestamp"), filter.to()));
        }

        Predicate[] predicates = searchCriteria.toArray(new Predicate[searchCriteria.size()]);

        criteriaQuery.select(root).where(predicates);

        List<TransactionSearchResponse> transactions = entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList()
                .stream()
                .map(TransactionMapper::toTransactionSearchResponse)
                .toList();

        CriteriaQuery<Long> criteriaQueryTotal = criteriaBuilder.createQuery(Long.class);

        criteriaQueryTotal.select(criteriaBuilder.count(root)).where(predicates);

        long total = entityManager.createQuery(criteriaQueryTotal).getSingleResult();

        return new PageImpl<>(transactions, pageable, total);
    }
}
