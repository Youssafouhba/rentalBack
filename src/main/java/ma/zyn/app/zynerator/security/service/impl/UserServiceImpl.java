package ma.zyn.app.zynerator.security.service.impl;


import ma.zyn.app.zynerator.security.bean.ModelPermissionUser;
import ma.zyn.app.zynerator.security.bean.RoleUser;
import ma.zyn.app.zynerator.security.bean.User;
import ma.zyn.app.zynerator.security.dao.criteria.core.UserCriteria;
import ma.zyn.app.zynerator.security.dao.facade.core.UserDao;
import ma.zyn.app.zynerator.security.dao.specification.core.UserSpecification;
import ma.zyn.app.zynerator.security.service.facade.*;
import ma.zyn.app.zynerator.service.AbstractServiceImpl;
import ma.zyn.app.zynerator.util.ListUtil;
import ma.zyn.app.zynerator.util.StringUtil;

import ma.zyn.app.zynerator.security.common.AuthoritiesConstants;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import org.springframework.beans.BeanUtils;


@Service
public class UserServiceImpl extends AbstractServiceImpl<User, UserCriteria, UserDao> implements UserService {



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public User create(User t) {
        return createWithEnable(t, true);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public User createAndDisable(User t) {
        return createWithEnable(t, false);
    }

    private User createWithEnable(User t, boolean enable) {
        User foundedUserByUsername = findByUsername(t.getUsername());
        User foundedUserByEmail = dao.findByEmail(t.getEmail());
        if (foundedUserByUsername != null || foundedUserByEmail != null) return null;
        else {
            if (t.getPassword() == null || t.getPassword().isEmpty()) {
                t.setPassword(bCryptPasswordEncoder.encode(t.getUsername()));
            } else {
                t.setPassword(bCryptPasswordEncoder.encode(t.getPassword()));
            }
            t.setAccountNonExpired(true);
            t.setAccountNonLocked(true);
            t.setCredentialsNonExpired(true);
            t.setEnabled(enable);
            t.setPasswordChanged(false);
            t.setCreatedAt(LocalDateTime.now());
            /*if (t.getModelPermissionUsers() == null)
                t.setModelPermissionUsers(new ArrayList<>());

            t.setModelPermissionUsers(modelPermissionUserService.initModelPermissionUser());
            */
            User saved = new User();
            String roleAsString = t.getRoleUsers().get(0).getRole().getAuthority();
			
						
            if (roleAsString.equals(AuthoritiesConstants.ADMIN)) {
                saved = super.create(t);
            } 	 
 
 
 
 
 
 
 
 
 
 
 
 
			
		
            /*if (t.getModelPermissionUsers() != null) {
                User finalSaved = saved;
                t.getModelPermissionUsers().forEach(e -> {
                    e.setUserApp(finalSaved);
                    modelPermissionUserService.create(e);
                });
            }*/
            if (t.getRoleUsers() != null) {
                User finalSaved = saved;
                t.getRoleUsers().forEach(element -> {
                    if (element.getRole() != null && element.getRole().getId() == null && StringUtil.isNotEmpty(element.getRole().getAuthority())) {
                        element.setRole(roleService.findByAuthority(element.getRole().getAuthority()));
                    }
                    element.setUserApp(finalSaved);
                    roleUserService.create(element);
                });
            }
            return saved;
        }

    }



    public User findWithAssociatedLists(Long id) {
        User result = dao.findById(id).orElse(null);
        if (result != null && result.getId() != null) {
            result.setModelPermissionUsers(modelPermissionUserService.findByUserId(id));
            result.setRoleUsers(roleUserService.findByUserId(id));
        }
        return result;
    }

    @Transactional
    public void deleteAssociatedLists(Long id) {
        modelPermissionUserService.deleteByUserId(id);
        roleUserService.deleteByUserId(id);
    }


    public void updateWithAssociatedLists(User user) {
        if (user != null && user.getId() != null) {
            List<List<ModelPermissionUser>> resultModelPermissionUsers = modelPermissionUserService.getToBeSavedAndToBeDeleted(modelPermissionUserService.findByUserId(user.getId()), user.getModelPermissionUsers());
            modelPermissionUserService.delete(resultModelPermissionUsers.get(1));
            ListUtil.emptyIfNull(resultModelPermissionUsers.get(0)).forEach(e -> e.setUserApp(user));
            modelPermissionUserService.update(resultModelPermissionUsers.get(0), true);
            List<List<RoleUser>> resultRoleUsers = roleUserService.getToBeSavedAndToBeDeleted(roleUserService.findByUserId(user.getId()), user.getRoleUsers());
            roleUserService.delete(resultRoleUsers.get(1));
            ListUtil.emptyIfNull(resultRoleUsers.get(0)).forEach(e -> e.setUserApp(user));
            roleUserService.update(resultRoleUsers.get(0), true);
        }
    }


    public User findByReferenceEntity(User t) {
        return dao.findByEmail(t.getEmail());
    }

    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }


    public User findByLinkValidationCode(String linkValidationCode) {
        return dao.findByLinkValidationCode(linkValidationCode);
    }

    @Override
    public User findByUsername(String username) {
        if (username == null)
            return null;
        return dao.findByUsername(username);
    }

    public List<User> findAllOptimized() {
        return dao.findAllOptimized();
    }

    public String generateCode(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    @Override
    public String cryptPassword(String value) {
        return value == null ? null : bCryptPasswordEncoder.encode(value);
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        User user = dao.findByUsername(username);
        if (user != null) {
            user.setPassword(cryptPassword(newPassword));
            user.setPasswordChanged(true);
            dao.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User findByUsernameWithRoles(String username) {
        if (username == null)
            return null;
        return dao.findByUsername(username);
    }

    @Override
    @Transactional
    public int deleteByUsername(String username) {
        return dao.deleteByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsernameWithRoles(username);
    }

    public void configure() {
        super.configure(User.class, UserSpecification.class);
    }


    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    private ModelPermissionService modelPermissionService;
    @Autowired
    private ActionPermissionService actionPermissionService;
    @Autowired
    private ModelPermissionUserService modelPermissionUserService;
    @Autowired
    private RoleService roleService;
    @Lazy
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;


    private static final String CHARACTERS = "0123456789";


            public UserServiceImpl(UserDao dao) {
        super(dao);
    }

}
