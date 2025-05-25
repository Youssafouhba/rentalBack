package ma.zyn.app.dao.facade.core.finance;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.finance.Caisse;
import org.springframework.stereotype.Repository;
import ma.zyn.app.bean.core.finance.Caisse;
import java.util.List;


@Repository
public interface CaisseDao extends AbstractRepository<Caisse,Long>  {
    Caisse findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW Caisse(item.id,item.libelle) FROM Caisse item")
    List<Caisse> findAllOptimized();

}
