package ma.zyn.app.dao.facade.core.locataire;

import ma.zyn.app.bean.core.locataire.TypeTransactiont;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TypeTransactiontDao extends AbstractRepository<TypeTransactiont,Long>  {
    TypeTransactiont findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW TypeTransactiont(item.id,item.label) FROM TypeTransactiont item")
    List<TypeTransactiont> findAllOptimized();

}
