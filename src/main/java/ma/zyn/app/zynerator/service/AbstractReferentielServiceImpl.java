package ma.zyn.app.zynerator.service;

import ma.zyn.app.zynerator.bean.BaseEntity;
import ma.zyn.app.zynerator.criteria.BaseCriteria;
import ma.zyn.app.zynerator.repository.AbstractRepository;

public abstract class AbstractReferentielServiceImpl<T extends BaseEntity, CRITERIA extends BaseCriteria, REPO extends AbstractRepository<T, Long>> extends AbstractServiceImpl<T, CRITERIA, REPO> {

    public AbstractReferentielServiceImpl(REPO dao) {
        super(dao);
    }

}
