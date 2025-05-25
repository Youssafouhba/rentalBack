package ma.zyn.app.dao.facade.core.finance;

import ma.zyn.app.bean.core.finance.TypeCharge;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TypeChargeDao extends AbstractRepository<TypeCharge,Long>  {
    TypeCharge findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW TypeCharge(item.id,item.label) FROM TypeCharge item")
    List<TypeCharge> findAllOptimized();

}
