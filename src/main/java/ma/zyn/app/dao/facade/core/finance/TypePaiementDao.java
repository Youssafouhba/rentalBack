package ma.zyn.app.dao.facade.core.finance;

import ma.zyn.app.bean.core.finance.TypePaiement;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TypePaiementDao extends AbstractRepository<TypePaiement,Long>  {
    TypePaiement findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW TypePaiement(item.id,item.label) FROM TypePaiement item")
    List<TypePaiement> findAllOptimized();

}
