package ma.zyn.app.dao.facade.core.finance;

import ma.zyn.app.bean.core.finance.CompteInstantanee;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteInstantaneeDao extends AbstractRepository<CompteInstantanee,Long> {
    CompteInstantanee findByLocataireId(Long id);
    int deleteByLocataireId(Long id);
    long countByLocataireCode(String code);


    CompteInstantanee findByLocataireAndLocation(Locataire locataire, Location location);

    CompteInstantanee findByCode(String code);
}
