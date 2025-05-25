package ma.zyn.app.ws.facade.admin.locataire;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.dao.criteria.core.locataire.TransactionCriteria;
import ma.zyn.app.service.facade.admin.locataire.TransactionAdminService;
import ma.zyn.app.ws.converter.locataire.TransactionConverter;
import ma.zyn.app.ws.dto.locataire.TransactionDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/transaction/")
public class TransactionRestAdmin {




    @Operation(summary = "Finds a list of all transactions")
    @GetMapping("")
    public ResponseEntity<List<TransactionDto>> findAll() throws Exception {
        ResponseEntity<List<TransactionDto>> res = null;
        List<Transaction> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<TransactionDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }


    @Operation(summary = "Finds a transaction by id")
    @GetMapping("id/{id}")
    public ResponseEntity<TransactionDto> findById(@PathVariable Long id) {
        Transaction t = service.findById(id);
        if (t != null) {
            converter.init(true);
            TransactionDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Saves the specified  transaction")
    @PostMapping("")
    public ResponseEntity<TransactionDto> save(@RequestBody TransactionDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Transaction myT = converter.toItem(dto);
            Transaction t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                TransactionDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  transaction")
    @PutMapping("")
    public ResponseEntity<TransactionDto> update(@RequestBody TransactionDto dto) throws Exception {
        ResponseEntity<TransactionDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Transaction t = service.findById(dto.getId());
            converter.copy(dto,t);
            Transaction updated = service.update(t);
            TransactionDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of transaction")
    @PostMapping("multiple")
    public ResponseEntity<List<TransactionDto>> delete(@RequestBody List<TransactionDto> dtos) throws Exception {
        ResponseEntity<List<TransactionDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Transaction> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified transaction")
    @DeleteMapping("id/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable Long id) throws Exception {
        ResponseEntity<Long> res;
        HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        if (id != null) {
            boolean resultDelete = service.deleteById(id);
            if (resultDelete) {
                status = HttpStatus.OK;
            }
        }
        res = new ResponseEntity<>(id, status);
        return res;
    }

    @Operation(summary = "find by typeTransaction code")
    @GetMapping("typeTransaction/code/{code}")
    public List<TransactionDto> findByTypeTransactionCode(@PathVariable String code){
        return findDtos(service.findByTypeTransactionCode(code));
    }
    @Operation(summary = "delete by typeTransaction code")
    @DeleteMapping("typeTransaction/code/{code}")
    public int deleteByTypeTransactionCode(@PathVariable String code){
        return service.deleteByTypeTransactionCode(code);
    }
    @Operation(summary = "find by modePaiement code")
    @GetMapping("modePaiement/code/{code}")
    public List<TransactionDto> findByModePaiementCode(@PathVariable String code){
        return findDtos(service.findByModePaiementCode(code));
    }
    @Operation(summary = "delete by modePaiement code")
    @DeleteMapping("modePaiement/code/{code}")
    public int deleteByModePaiementCode(@PathVariable String code){
        return service.deleteByModePaiementCode(code);
    }
    @Operation(summary = "find by typePaiement code")
    @GetMapping("typePaiement/code/{code}")
    public List<TransactionDto> findByTypePaiementCode(@PathVariable String code){
        return findDtos(service.findByTypePaiementCode(code));
    }
    @Operation(summary = "delete by typePaiement code")
    @DeleteMapping("typePaiement/code/{code}")
    public int deleteByTypePaiementCode(@PathVariable String code){
        return service.deleteByTypePaiementCode(code);
    }

    @Operation(summary = "Finds a transaction and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<TransactionDto> findWithAssociatedLists(@PathVariable Long id) {
        Transaction loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        TransactionDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds transactions by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<TransactionDto>> findByCriteria(@RequestBody TransactionCriteria criteria) throws Exception {
        ResponseEntity<List<TransactionDto>> res = null;
        List<Transaction> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<TransactionDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated transactions by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody TransactionCriteria criteria) throws Exception {
        List<Transaction> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<TransactionDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets transaction data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody TransactionCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<TransactionDto> findDtos(List<Transaction> list){
        converter.initObject(true);
        List<TransactionDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<TransactionDto> getDtoResponseEntity(TransactionDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public TransactionRestAdmin(TransactionAdminService service, TransactionConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final TransactionAdminService service;
    private final TransactionConverter converter;





}
