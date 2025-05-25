package ma.zyn.app.dao.facade.core.finance;

import ma.zyn.app.bean.core.finance.CompteCharge;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompteChargeDao extends AbstractRepository<CompteCharge,Long>  {
    CompteCharge findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW CompteCharge(item.id,item.label) FROM CompteCharge item")
    List<CompteCharge> findAllOptimized();

}
