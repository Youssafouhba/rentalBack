package ma.zyn.app.dao.facade.core.locataire;

import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionDao extends AbstractRepository<Transaction,Long>  {

    List<Transaction> findByTypeTransactionCode(String code);
    List<Transaction> findByTypeTransactionId(Long id);
    int deleteByTypeTransactionId(Long id);
    int deleteByTypeTransactionCode(String code);
    long countByTypeTransactionCode(String code);
    List<Transaction> findByModePaiementCode(String code);
    List<Transaction> findByModePaiementId(Long id);
    int deleteByModePaiementId(Long id);
    int deleteByModePaiementCode(String code);
    long countByModePaiementCode(String code);
    List<Transaction> findByTypePaiementCode(String code);
    List<Transaction> findByTypePaiementId(Long id);
    int deleteByTypePaiementId(Long id);
    int deleteByTypePaiementCode(String code);
    long countByTypePaiementCode(String code);
    List<Transaction> findByCompteSourceId(Long id);
    int deleteByCompteSourceId(Long id);
    long countByCompteSourceId(Long id);
    List<Transaction> findByCompteDestinationId(Long id);
    int deleteByCompteDestinationId(Long id);
    long countByCompteDestinationId(Long id);
    Transaction findByCode(String code);


}
