package ma.zyn.app.zynerator.security.service.facade;

import ma.zyn.app.zynerator.security.bean.ActionPermission;
import ma.zyn.app.zynerator.security.dao.criteria.core.ActionPermissionCriteria;
import ma.zyn.app.zynerator.service.IService;
import java.util.List;


public interface ActionPermissionService extends  IService<ActionPermission,ActionPermissionCriteria>  {

    List<ActionPermission> findAllOptimized();

}
