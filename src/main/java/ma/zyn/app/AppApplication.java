package ma.zyn.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ma.zyn.app.bean.core.finance.*;
import ma.zyn.app.bean.core.locataire.StatutLocal;
import ma.zyn.app.bean.core.locataire.TypeLocataire;
import ma.zyn.app.bean.core.locataire.TypeTransactiont;
import ma.zyn.app.bean.core.locaux.TypeLocal;
import ma.zyn.app.service.facade.admin.finance.*;
import ma.zyn.app.service.facade.admin.locataire.StatutLocalAdminService;
import ma.zyn.app.service.facade.admin.locataire.TypeLocataireAdminService;
import ma.zyn.app.service.facade.admin.locataire.TypeTransactiontAdminService;
import ma.zyn.app.service.facade.admin.locaux.TypeLocalAdminService;
import ma.zyn.app.service.impl.admin.finance.CompteChargeAdminServiceImpl;
import ma.zyn.app.zynerator.security.bean.*;
import ma.zyn.app.zynerator.security.common.AuthoritiesConstants;
import ma.zyn.app.zynerator.security.service.facade.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
//@EnableFeignClients
public class AppApplication {
    public static ConfigurableApplicationContext ctx;
    @Autowired
    private CompteAdminAdminService compteAdminAdminService;
    @Autowired
    private TypeTransactiontAdminService typeTransactiontService;
    @Autowired
    TypeChargeAdminService typeChargeService;
    @Autowired
    private CompteChargeAdminService compteChargeAdminService;

    //state: primary success info secondary warning danger contrast
    //_STATE(Pending=warning,Rejeted=danger,Validated=success)
    public static void main(String[] args) {
        ctx=SpringApplication.run(AppApplication.class, args);
    }


    @Bean
    ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static ConfigurableApplicationContext getCtx() {
        return ctx;
    }

    @Bean
    public CommandLineRunner demo(UserService userService, RoleService roleService, ModelPermissionService modelPermissionService, ActionPermissionService actionPermissionService, ModelPermissionUserService modelPermissionUserService ) {
    return (args) -> {
        if(true){

            createStatutLocal();
            createTypeLocataire();
            createTypeLocal();
            createModePaiement();
            createTypePaiement();
            createCompteAdmin();
            createTypeTransactiont();
            createTypeCharge();
            //createCompteCharge();
        /*
        List<ModelPermission> modelPermissions = new ArrayList<>();
        addPermission(modelPermissions);
        modelPermissions.forEach(e -> modelPermissionService.create(e));
        List<ActionPermission> actionPermissions = new ArrayList<>();
        addActionPermission(actionPermissions);
        actionPermissions.forEach(e -> actionPermissionService.create(e));
        */

		// User Admin
        User userForAdmin = new User("redouanabdalass@gmail.com");
		userForAdmin.setPassword("rentalmanagement52");
		// Role Admin
		Role roleForAdmin = new Role();
		roleForAdmin.setAuthority(AuthoritiesConstants.ADMIN);
		roleForAdmin.setCreatedAt(LocalDateTime.now());
		Role roleForAdminSaved = roleService.create(roleForAdmin);
		RoleUser roleUserForAdmin = new RoleUser();
		roleUserForAdmin.setRole(roleForAdminSaved);
		if (userForAdmin.getRoleUsers() == null)
			userForAdmin.setRoleUsers(new ArrayList<>());

		userForAdmin.getRoleUsers().add(roleUserForAdmin);


        userForAdmin.setModelPermissionUsers(modelPermissionUserService.initModelPermissionUser());

        userService.create(userForAdmin);

            }
        };
    }

    private void createCompteAdmin() {
        CompteAdmin compteAdmin = new CompteAdmin();
        compteAdmin.setCode("ADMIN");
        compteAdmin.setSolde(BigDecimal.valueOf(0));
        compteAdmin.setDateCreation(LocalDateTime.now());
        compteAdminAdminService.create(compteAdmin);
    }

    private void createTypeTransactiont(){
        TypeTransactiont itemWarning = new TypeTransactiont();
        itemWarning.setStyle("warning");
        itemWarning.setLabel("charge");
        itemWarning.setCode("charge");
        typeTransactiontService.create(itemWarning);
        TypeTransactiont itemPending = new TypeTransactiont();
        itemPending.setStyle("pending");
        itemPending.setLabel("reglement");
        itemPending.setCode("reglement");
        typeTransactiontService.create(itemPending);
        TypeTransactiont itemSuccess = new TypeTransactiont();
        itemSuccess.setStyle("success");
        itemSuccess.setLabel("avoir");
        itemSuccess.setCode("avoir");
        typeTransactiontService.create(itemSuccess);

    }
    private void createCompteCharge() {
        CompteCharge compteCharge = new CompteCharge();
        compteCharge.setCode("CHARGE");
        compteCharge.setLabel("Charge");
        compteCharge.setNom("Charge");
        compteCharge.setSolde(BigDecimal.valueOf(0));
        compteChargeAdminService.create(compteCharge);
    }
    private void createTypeCharge(){
        TypeCharge itemWarning = new TypeCharge();
        itemWarning.setStyle("warning");
        itemWarning.setLabel("ménage");
        itemWarning.setCode("ménage");
        typeChargeService.create(itemWarning);
        TypeCharge itemImpots = new TypeCharge();
        itemImpots.setStyle("warning");
        itemImpots.setLabel("Impots");
        itemImpots.setCode("impots");
        typeChargeService.create(itemImpots);
        TypeCharge itemMaintenance = new TypeCharge();
        itemMaintenance.setStyle("warning");
        itemMaintenance.setLabel("Maintenance");
        itemMaintenance.setCode("maintenance");
        typeChargeService.create(itemMaintenance);
        TypeCharge itemFraisBancaires = new TypeCharge();
        itemFraisBancaires.setStyle("warning");
        itemFraisBancaires.setLabel("Frais Bancaires");
        itemFraisBancaires.setCode("fraisBancaires");
        typeChargeService.create(itemFraisBancaires);
        TypeCharge itemDivers = new TypeCharge();
        itemDivers.setStyle("warning");
        itemDivers.setLabel("Divers");
        itemDivers.setCode("divers");
        typeChargeService.create(itemDivers);
        TypeCharge itemSuccess = new TypeCharge();
        itemSuccess.setStyle("success");
        itemSuccess.setLabel("telephone/internet");
        itemSuccess.setCode("internet");
        typeChargeService.create(itemSuccess);
        TypeCharge itemRadema = new TypeCharge();
        itemRadema.setStyle("success");
        itemRadema.setLabel("radema");
        itemRadema.setCode("radema");
        typeChargeService.create(itemRadema);

    }
    private void createTypeLocal(){
        TypeLocal itemWarning = new TypeLocal();
        itemWarning.setStyle("warning");
        itemWarning.setLabel("Bureau");
        itemWarning.setCode("APARTMENT");
        typeLocalService.create(itemWarning);
        TypeLocal itemSuccess = new TypeLocal();
        itemSuccess.setStyle("success");
        itemSuccess.setLabel("Commercial");
        itemSuccess.setCode("OFFICE");
        typeLocalService.create(itemSuccess);

    }
    private void createStatutLocal(){
            StatutLocal itemSuccess = new StatutLocal();
            itemSuccess.setStyle("success");
            itemSuccess.setLabel("loué");
            itemSuccess.setCode("loué");
            statutLocalService.create(itemSuccess);
            StatutLocal itemPending = new StatutLocal();
            itemPending.setStyle("Pending");
            itemPending.setLabel("suspendu");
            itemPending.setCode("suspendu");
            statutLocalService.create(itemPending);
            StatutLocal itemWarning = new StatutLocal();
            itemWarning.setStyle("warning");
            itemWarning.setLabel("disponible");
            itemWarning.setCode("disponible");
            statutLocalService.create(itemWarning);

    }
    private void createTypeLocataire(){
            TypeLocataire itemWarning = new TypeLocataire();
            itemWarning.setStyle("warning");
            itemWarning.setLabel("personnel");
            itemWarning.setCode("personnel");
            typeLocataireService.create(itemWarning);
            TypeLocataire itemSuccess = new TypeLocataire();
            itemSuccess.setStyle("success");
            itemSuccess.setLabel("societe");
            itemSuccess.setCode("societe");
            typeLocataireService.create(itemSuccess);

    }
    private void createModePaiement(){
            ModePaiement itemSuccess = new ModePaiement();
            itemSuccess.setStyle("success");
            itemSuccess.setLabel("Banque");
            itemSuccess.setCode("virement");
            modePaiementService.create(itemSuccess);
            ModePaiement itemPending = new ModePaiement();
            itemPending.setStyle("pending");
            itemPending.setLabel("espèce");
            itemPending.setCode("espèce");
            modePaiementService.create(itemPending);

    }
    private void createTypePaiement(){
            TypePaiement itemWarning = new TypePaiement();
            itemWarning.setStyle("warning");
            itemWarning.setLabel("Debit");
            itemWarning.setCode("Debit");
            typePaiementService.create(itemWarning);
            TypePaiement itemSuccess = new TypePaiement();
            itemSuccess.setStyle("success");
            itemSuccess.setLabel("Credit");
            itemSuccess.setCode("Credit");
            typePaiementService.create(itemSuccess);

    }

    private static String fakeString(String attributeName, int i) {
        return attributeName + i;
    }

    private static Long fakeLong(String attributeName, int i) {
        return  10L * i;
    }
    private static Integer fakeInteger(String attributeName, int i) {
        return  10 * i;
    }

    private static Double fakeDouble(String attributeName, int i) {
        return 10D * i;
    }

    private static BigDecimal fakeBigDecimal(String attributeName, int i) {
        return  BigDecimal.valueOf(i*1L*10);
    }

    private static Boolean fakeBoolean(String attributeName, int i) {
        return i % 2 == 0 ? true : false;
    }
    private static LocalDateTime fakeLocalDateTime(String attributeName, int i) {
        return LocalDateTime.now().plusDays(i);
    }


    private static void addPermission(List<ModelPermission> modelPermissions) {
        modelPermissions.add(new ModelPermission("StatutLocal"));
        modelPermissions.add(new ModelPermission("Locataire"));
        modelPermissions.add(new ModelPermission("TypeLocataire"));
        modelPermissions.add(new ModelPermission("Location"));
        modelPermissions.add(new ModelPermission("ModePaiement"));
        modelPermissions.add(new ModelPermission("Caisse"));
        modelPermissions.add(new ModelPermission("Transaction"));
        modelPermissions.add(new ModelPermission("Banque"));
        modelPermissions.add(new ModelPermission("TypePaiement"));
        modelPermissions.add(new ModelPermission("Local"));
        modelPermissions.add(new ModelPermission("Compte"));
        modelPermissions.add(new ModelPermission("Charge"));
        modelPermissions.add(new ModelPermission("CompteInstantanee"));
        modelPermissions.add(new ModelPermission("User"));
        modelPermissions.add(new ModelPermission("ModelPermission"));
        modelPermissions.add(new ModelPermission("ActionPermission"));
    }

    private static void addActionPermission(List<ActionPermission> actionPermissions) {
        actionPermissions.add(new ActionPermission("list"));
        actionPermissions.add(new ActionPermission("create"));
        actionPermissions.add(new ActionPermission("delete"));
        actionPermissions.add(new ActionPermission("edit"));
        actionPermissions.add(new ActionPermission("view"));
        actionPermissions.add(new ActionPermission("duplicate"));
    }

    @Autowired
    TypeLocalAdminService typeLocalService;
    @Autowired
    StatutLocalAdminService statutLocalService;
    @Autowired
    TypeLocataireAdminService typeLocataireService;
    @Autowired
    ModePaiementAdminService modePaiementService;
    @Autowired
    TypePaiementAdminService typePaiementService;
}


