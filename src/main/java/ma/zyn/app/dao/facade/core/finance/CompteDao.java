package ma.zyn.app.dao.facade.core.finance;

import ma.zyn.app.bean.core.finance.Banque;
import ma.zyn.app.bean.core.finance.Caisse;
import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.bean.core.finance.CompteCharge;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface CompteDao extends AbstractRepository<Compte,Long>  {

    List<Compte> findByBanqueId(Long id);
    int deleteByBanqueId(Long id);
    long countByBanqueCode(String code);

    List<Compte> findComptesByBanqueNotNull();

    List<Compte> findComptesByCaisseNotNull();

    Compte findByCode(String code);

    Compte findByCaisse(Caisse caisse);

    Compte findByBanque(Banque banque);

    List<Compte> findComptsByCompteInstantaneeNotNull();

    Compte findByCompteCharge(CompteCharge compteCharge);

    List<Compte> findByCompteChargeId(Long id);

    List<Compte> findComptesByCompteChargeNotNull();

    // Dans CompteRepository
    @Query("SELECT c FROM Compte c " +
            "LEFT JOIN FETCH c.transactionsDestination " +
            "LEFT JOIN FETCH c.transactionsSource " +
            "WHERE c.id = :id")
    Compte findByIdWithTransactions(@Param("id") Long id);


}
