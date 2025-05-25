package ma.zyn.app.dao.facade.core.locaux;

import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocalDao extends AbstractRepository<Local,Long>  {
    Local findByCode(String code);
    int deleteByCode(String code);

    List<Local> findByStatutLocalCode(String code);
    List<Local> findByStatutLocalId(Long id);
    int deleteByStatutLocalId(Long id);
    int deleteByStatutLocalCode(String code);
    long countByStatutLocalCode(String code);
    List<Local> findByTypeLocalCode(String code);
    List<Local> findByTypeLocalId(Long id);
    int deleteByTypeLocalId(Long id);
    int deleteByTypeLocalCode(String code);
    long countByTypeLocalCode(String code);

    @Query("SELECT NEW Local(item.id,item.code) FROM Local item")
    List<Local> findAllOptimized();

}
