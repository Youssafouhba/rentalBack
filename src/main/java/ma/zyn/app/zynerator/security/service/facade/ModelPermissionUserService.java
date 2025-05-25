package ma.zyn.app.zynerator.security.service.facade;

import ma.zyn.app.zynerator.security.bean.ModelPermissionUser;
import ma.zyn.app.zynerator.security.dao.criteria.core.ModelPermissionUserCriteria;
import ma.zyn.app.zynerator.service.IService;

import java.util.List;



public interface ModelPermissionUserService extends  IService<ModelPermissionUser,ModelPermissionUserCriteria>  {

    List<ModelPermissionUser> initModelPermissionUser();
    List<ModelPermissionUser> initSecurityModelPermissionUser();
    List<ModelPermissionUser> findByActionPermissionId(Long id);
    int deleteByActionPermissionId(Long id);
    long countByActionPermissionReference(String reference);
    List<ModelPermissionUser> findByModelPermissionId(Long id);
    int deleteByModelPermissionId(Long id);
    long countByModelPermissionReference(String reference);
    List<ModelPermissionUser> findByUserId(Long id);
    Boolean findByUserUsernameAndModelPermissionReferenceAndActionPermissionReference( String username ,  String modelReference,  String actionReference);
    int deleteByUserId(Long id);
    long countByUserEmail(String email);

    List<ModelPermissionUser> findByUserUsername(String username);

}
