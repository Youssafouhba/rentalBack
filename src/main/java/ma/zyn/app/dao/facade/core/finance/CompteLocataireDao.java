package ma.zyn.app.dao.facade.core.finance;

import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompteLocataireDao extends AbstractRepository<CompteLocataire,Long>  {

    CompteLocataire findByLocataireId(Long id);
    int deleteByLocataireId(Long id);
    long countByLocataireCode(String code);


}
