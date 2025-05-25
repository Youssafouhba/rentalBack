package ma.zyn.app.ws.facade.admin.finance;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.bean.core.finance.CompteInstantanee;
import ma.zyn.app.dao.criteria.core.finance.CompteCriteria;
import ma.zyn.app.service.facade.admin.finance.CompteAdminService;
import ma.zyn.app.ws.converter.finance.CompteConverter;
import ma.zyn.app.ws.dto.finance.CompteDto;
import ma.zyn.app.ws.dto.finance.CompteInstantaneeDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/compte/")
public class CompteRestAdmin {



    @Operation(summary = "Finds Compte Instantanee by id")
    @GetMapping("compte-instantanee")
    public ResponseEntity<List<CompteDto>> findByCompteInstantane() {
        List<Compte> list = service.findByCompteInstantaneNotNull();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.setCompteInstantanee(true);
        List<CompteDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        ResponseEntity<List<CompteDto>> res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds Compte by id")
    @GetMapping("compteCharge")
    public ResponseEntity<List<CompteDto>> findByCompteCharge() {
        List<Compte> list = service.findComptsByCompteChargeNotNull();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.setCompteCharge(true);
        List<CompteDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        ResponseEntity<List<CompteDto>> res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds Compte Instantanee by id")
    @GetMapping("banque")
    public ResponseEntity<List<CompteDto>> findByBanque() {
        List<Compte> list = service.findComptsByBanqueNotNull();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.setBanque(true);
        List<CompteDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        ResponseEntity<List<CompteDto>> res = new ResponseEntity<>(dtos, status);
        return res;
    }


    @Operation(summary = "Finds Compte Instantanee by id")
    @GetMapping("caisse")
    public ResponseEntity<List<CompteDto>> findByCaisse() {
        List<Compte> list = service.findComptsByCaisseNotNull();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.setCaisse(true);
        List<CompteDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        ResponseEntity<List<CompteDto>> res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a list of all comptes")
    @GetMapping("")
    public ResponseEntity<List<CompteDto>> findAll() throws Exception {
        ResponseEntity<List<CompteDto>> res = null;
        List<Compte> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
            converter.initObject(true);
        List<CompteDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }


    @Operation(summary = "Finds a compte by id")
    @GetMapping("id/{id}")
    public ResponseEntity<CompteDto> findById(@PathVariable Long id) {
        Compte t = service.findById(id);
        if (t != null) {
            converter.init(true);
            CompteDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Saves the specified  compte")
    @PostMapping("")
    public ResponseEntity<CompteDto> save(@RequestBody CompteDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Compte myT = converter.toItem(dto);
            Compte t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                CompteDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  compte")
    @PutMapping("")
    public ResponseEntity<CompteDto> update(@RequestBody CompteDto dto) throws Exception {
        ResponseEntity<CompteDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Compte t = service.findById(dto.getId());
            converter.copy(dto,t);
            Compte updated = service.update(t);
            CompteDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of compte")
    @PostMapping("multiple")
    public ResponseEntity<List<CompteDto>> delete(@RequestBody List<CompteDto> dtos) throws Exception {
        ResponseEntity<List<CompteDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Compte> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified compte")
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

    @Operation(summary = "find by banque id")
    @GetMapping("banque/id/{id}")
    public List<CompteDto> findByBanqueId(@PathVariable Long id){
        return findDtos(service.findByBanqueId(id));
    }
    @Operation(summary = "delete by banque id")
    @DeleteMapping("banque/id/{id}")
    public int deleteByBanqueId(@PathVariable Long id){
        return service.deleteByBanqueId(id);
    }

    @Operation(summary = "Finds a compte and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<CompteDto> findWithAssociatedLists(@PathVariable Long id) {
        Compte loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        CompteDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds comptes by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<CompteDto>> findByCriteria(@RequestBody CompteCriteria criteria) throws Exception {
        ResponseEntity<List<CompteDto>> res = null;
        List<Compte> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<CompteDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated comptes by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody CompteCriteria criteria) throws Exception {
        List<Compte> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initList(false);
        converter.initObject(true);
        List<CompteDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets compte data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody CompteCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<CompteDto> findDtos(List<Compte> list){
        converter.initList(false);
        converter.initObject(true);
        List<CompteDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<CompteDto> getDtoResponseEntity(CompteDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public CompteRestAdmin(CompteAdminService service, CompteConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final CompteAdminService service;
    private final CompteConverter converter;





}
