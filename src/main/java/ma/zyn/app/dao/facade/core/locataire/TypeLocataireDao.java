package ma.zyn.app.dao.facade.core.locataire;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.locataire.TypeLocataire;
import org.springframework.stereotype.Repository;
import ma.zyn.app.bean.core.locataire.TypeLocataire;
import java.util.List;


@Repository
public interface TypeLocataireDao extends AbstractRepository<TypeLocataire,Long>  {
    TypeLocataire findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW TypeLocataire(item.id,item.label) FROM TypeLocataire item")
    List<TypeLocataire> findAllOptimized();

}
