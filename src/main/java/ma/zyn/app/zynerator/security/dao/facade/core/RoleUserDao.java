package ma.zyn.app.zynerator.security.dao.facade.core;

import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.zynerator.security.bean.RoleUser;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleUserDao extends AbstractRepository<RoleUser,Long>  {

    List<RoleUser> findByRoleId(Long id);
    int deleteByRoleId(Long id);
    long countByRoleAuthority(String authority);
    List<RoleUser> findByUserAppId(Long id);
    int deleteByUserAppId(Long id);
    long countByUserAppEmail(String email);


}
