package ma.zyn.app.dao.facade.core.locataire;

import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocationDao extends AbstractRepository<Location,Long>  {
    Location findByCode(String code);
    int deleteByCode(String code);

    List<Location> findByLocataireId(Long id);
    int deleteByLocataireId(Long id);
    long countByLocataireCode(String code);
    List<Location> findByLocalId(Long id);
    int deleteByLocalId(Long id);
    long countByLocalCode(String code);

    @Query("SELECT NEW Location(item.id,item.code) FROM Location item")
    List<Location> findAllOptimized();

}
