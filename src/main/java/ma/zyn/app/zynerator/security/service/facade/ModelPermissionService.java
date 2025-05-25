package ma.zyn.app.zynerator.security.service.facade;

import ma.zyn.app.zynerator.security.bean.ModelPermission;
import ma.zyn.app.zynerator.security.dao.criteria.core.ModelPermissionCriteria;
import ma.zyn.app.zynerator.service.IService;
import java.util.List;



public interface ModelPermissionService extends  IService<ModelPermission,ModelPermissionCriteria>  {
    List<ModelPermission> findAllOptimized();

}
