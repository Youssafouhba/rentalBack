package ma.zyn.app.zynerator.specification;


import ma.zyn.app.zynerator.bean.BaseEntity;
import ma.zyn.app.zynerator.criteria.BaseCriteria;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSpecification<Criteria extends BaseCriteria, T extends BaseEntity> extends SpecificationHelper<Criteria, T> implements Specification<T> {

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        attachSearchElement(root, query, builder, predicates);
        if (criteria != null) {
            constructPredicates();
            addOrderAndFilter();
        }
        return getResult();
    }

    public abstract void constructPredicates();

    public AbstractSpecification(Criteria criteria) {
        this.criteria = criteria;
    }

    public AbstractSpecification(Criteria criteria, boolean distinct) {
        this.criteria = criteria;
        this.distinct = distinct;
    }


}
