package ma.zyn.app.zynerator.security.dao.facade.core;

import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.zynerator.security.bean.ModelPermissionUser;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ModelPermissionUserDao extends AbstractRepository<ModelPermissionUser,Long>  {

    List<ModelPermissionUser> findByActionPermissionId(Long id);
    int deleteByActionPermissionId(Long id);
    long countByActionPermissionReference(String reference);
    List<ModelPermissionUser> findByModelPermissionId(Long id);
    int deleteByModelPermissionId(Long id);
    long countByModelPermissionReference(String reference);
    List<ModelPermissionUser> findByUserAppId(Long id);
    ModelPermissionUser findByUserAppUsernameAndModelPermissionReferenceAndActionPermissionReference( String username ,  String modelReference,  String actionReference);
    int deleteByUserAppId(Long id);
    long countByUserAppEmail(String email);
    List<ModelPermissionUser> findByUserAppUsername(String username);



}
