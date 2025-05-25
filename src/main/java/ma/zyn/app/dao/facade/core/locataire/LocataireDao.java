package ma.zyn.app.dao.facade.core.locataire;

import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocataireDao extends AbstractRepository<Locataire,Long>  {
    Locataire findByCode(String code);
    int deleteByCode(String code);

    List<Locataire> findByTypeLocataireCode(String code);
    List<Locataire> findByTypeLocataireId(Long id);
    int deleteByTypeLocataireId(Long id);
    int deleteByTypeLocataireCode(String code);
    long countByTypeLocataireCode(String code);
    List<Locataire> findByCompteLocataireId(Long id);
    int deleteByCompteLocataireId(Long id);
    long countByCompteLocataireId(Long id);
    Locataire findLocataireByNomAndPrenomAndTelephone(String nom, String prenom, String telephone);
    @Query("SELECT NEW Locataire(item.id,item.code) FROM Locataire item")
    List<Locataire> findAllOptimized();

}
