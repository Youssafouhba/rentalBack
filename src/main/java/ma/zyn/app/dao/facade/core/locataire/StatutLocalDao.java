package ma.zyn.app.dao.facade.core.locataire;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.locataire.StatutLocal;
import org.springframework.stereotype.Repository;
import ma.zyn.app.bean.core.locataire.StatutLocal;
import java.util.List;


@Repository
public interface StatutLocalDao extends AbstractRepository<StatutLocal,Long>  {
    StatutLocal findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW StatutLocal(item.id,item.label) FROM StatutLocal item")
    List<StatutLocal> findAllOptimized();

}
