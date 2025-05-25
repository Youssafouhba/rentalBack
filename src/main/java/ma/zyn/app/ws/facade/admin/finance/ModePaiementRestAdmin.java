package ma.zyn.app.ws.facade.admin.finance;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.finance.ModePaiement;
import ma.zyn.app.dao.criteria.core.finance.ModePaiementCriteria;
import ma.zyn.app.service.facade.admin.finance.ModePaiementAdminService;
import ma.zyn.app.ws.converter.finance.ModePaiementConverter;
import ma.zyn.app.ws.dto.locataire.ModePaiementDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/modePaiement/")
public class ModePaiementRestAdmin {




    @Operation(summary = "Finds a list of all modePaiements")
    @GetMapping("")
    public ResponseEntity<List<ModePaiementDto>> findAll() throws Exception {
        ResponseEntity<List<ModePaiementDto>> res = null;
        List<ModePaiement> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<ModePaiementDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all modePaiements")
    @GetMapping("optimized")
    public ResponseEntity<List<ModePaiementDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<ModePaiementDto>> res = null;
        List<ModePaiement> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<ModePaiementDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a modePaiement by id")
    @GetMapping("id/{id}")
    public ResponseEntity<ModePaiementDto> findById(@PathVariable Long id) {
        ModePaiement t = service.findById(id);
        if (t != null) {
            ModePaiementDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a modePaiement by label")
    @GetMapping("label/{label}")
    public ResponseEntity<ModePaiementDto> findByLabel(@PathVariable String label) {
	    ModePaiement t = service.findByReferenceEntity(new ModePaiement(label));
        if (t != null) {
            ModePaiementDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  modePaiement")
    @PostMapping("")
    public ResponseEntity<ModePaiementDto> save(@RequestBody ModePaiementDto dto) throws Exception {
        if(dto!=null){
            ModePaiement myT = converter.toItem(dto);
            ModePaiement t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                ModePaiementDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  modePaiement")
    @PutMapping("")
    public ResponseEntity<ModePaiementDto> update(@RequestBody ModePaiementDto dto) throws Exception {
        ResponseEntity<ModePaiementDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            ModePaiement t = service.findById(dto.getId());
            converter.copy(dto,t);
            ModePaiement updated = service.update(t);
            ModePaiementDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of modePaiement")
    @PostMapping("multiple")
    public ResponseEntity<List<ModePaiementDto>> delete(@RequestBody List<ModePaiementDto> dtos) throws Exception {
        ResponseEntity<List<ModePaiementDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<ModePaiement> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified modePaiement")
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


    @Operation(summary = "Finds a modePaiement and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<ModePaiementDto> findWithAssociatedLists(@PathVariable Long id) {
        ModePaiement loaded =  service.findWithAssociatedLists(id);
        ModePaiementDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds modePaiements by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<ModePaiementDto>> findByCriteria(@RequestBody ModePaiementCriteria criteria) throws Exception {
        ResponseEntity<List<ModePaiementDto>> res = null;
        List<ModePaiement> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<ModePaiementDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated modePaiements by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody ModePaiementCriteria criteria) throws Exception {
        List<ModePaiement> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<ModePaiementDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets modePaiement data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody ModePaiementCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<ModePaiementDto> findDtos(List<ModePaiement> list){
        List<ModePaiementDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<ModePaiementDto> getDtoResponseEntity(ModePaiementDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public ModePaiementRestAdmin(ModePaiementAdminService service, ModePaiementConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final ModePaiementAdminService service;
    private final ModePaiementConverter converter;





}
