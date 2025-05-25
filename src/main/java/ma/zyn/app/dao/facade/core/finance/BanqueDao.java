package ma.zyn.app.dao.facade.core.finance;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.finance.Banque;
import org.springframework.stereotype.Repository;
import ma.zyn.app.bean.core.finance.Banque;
import java.util.List;


@Repository
public interface BanqueDao extends AbstractRepository<Banque,Long>  {
    Banque findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW Banque(item.id,item.label) FROM Banque item")
    List<Banque> findAllOptimized();

}
