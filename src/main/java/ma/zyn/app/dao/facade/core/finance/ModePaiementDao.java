package ma.zyn.app.dao.facade.core.finance;

import ma.zyn.app.bean.core.finance.ModePaiement;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ModePaiementDao extends AbstractRepository<ModePaiement,Long>  {
    ModePaiement findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW ModePaiement(item.id,item.label) FROM ModePaiement item")
    List<ModePaiement> findAllOptimized();

}
