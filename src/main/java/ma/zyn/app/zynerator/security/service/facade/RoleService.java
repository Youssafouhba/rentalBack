package ma.zyn.app.zynerator.security.service.facade;

import ma.zyn.app.zynerator.security.bean.Role;
import ma.zyn.app.zynerator.security.dao.criteria.core.RoleCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface RoleService extends  IService<Role,RoleCriteria>  {
    Role findByAuthority(String authority);
    int deleteByAuthority(String authority);


    



}
