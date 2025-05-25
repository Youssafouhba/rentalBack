package ma.zyn.app.dao.facade.core.finance;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.finance.Charge;
import org.springframework.stereotype.Repository;
import ma.zyn.app.bean.core.finance.Charge;
import java.util.List;


@Repository
public interface ChargeDao extends AbstractRepository<Charge,Long>  {
    Charge findByCode(String code);
    int deleteByCode(String code);

    List<Charge> findByTypeChargeCode(String code);
    List<Charge> findByTypeChargeId(Long id);
    int deleteByTypeChargeId(Long id);
    int deleteByTypeChargeCode(String code);
    long countByTypeChargeCode(String code);
    List<Charge> findByLocalId(Long id);
    int deleteByLocalId(Long id);
    long countByLocalCode(String code);

    @Query("SELECT NEW Charge(item.id,item.label) FROM Charge item")
    List<Charge> findAllOptimized();

    void deleteByCompteChargeId(Long id);

    List<Charge> findByCompteChargeId(Long id);
}
