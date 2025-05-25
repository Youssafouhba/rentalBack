package ma.zyn.app.dao.facade.core.locataire;

import ma.zyn.app.bean.core.locataire.Avoir;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AvoirDao extends AbstractRepository<Avoir,Long>  {

    List<Avoir> findByLocationId(Long id);
    int deleteByLocationId(Long id);
    long countByLocationCode(String code);


    void deleteByLocataireId(Long id);

    List<Avoir> findByLocataireId(Long id);

    Avoir findByCode(String code);
}
