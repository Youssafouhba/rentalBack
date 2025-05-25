package ma.zyn.app.dao.facade.core.locaux;

import ma.zyn.app.bean.core.locaux.TypeLocal;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TypeLocalDao extends AbstractRepository<TypeLocal,Long>  {
    TypeLocal findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW TypeLocal(item.id,item.label) FROM TypeLocal item")
    List<TypeLocal> findAllOptimized();

}
