package ma.zyn.app.dao.facade.core.locataire;

import ma.zyn.app.bean.core.locataire.Reglement;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReglementDao extends AbstractRepository<Reglement,Long>  {

    List<Reglement> findByLocationId(Long id);
    int deleteByLocationId(Long id);
    long countByLocationCode(String code);


}
