package ma.zyn.app.ws.facade.admin.finance;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.finance.TypePaiement;
import ma.zyn.app.dao.criteria.core.finance.TypePaiementCriteria;
import ma.zyn.app.service.facade.admin.finance.TypePaiementAdminService;
import ma.zyn.app.ws.converter.finance.TypePaiementConverter;
import ma.zyn.app.ws.dto.finance.TypePaiementDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/typePaiement/")
public class TypePaiementRestAdmin {




    @Operation(summary = "Finds a list of all typePaiements")
    @GetMapping("")
    public ResponseEntity<List<TypePaiementDto>> findAll() throws Exception {
        ResponseEntity<List<TypePaiementDto>> res = null;
        List<TypePaiement> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypePaiementDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all typePaiements")
    @GetMapping("optimized")
    public ResponseEntity<List<TypePaiementDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<TypePaiementDto>> res = null;
        List<TypePaiement> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypePaiementDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a typePaiement by id")
    @GetMapping("id/{id}")
    public ResponseEntity<TypePaiementDto> findById(@PathVariable Long id) {
        TypePaiement t = service.findById(id);
        if (t != null) {
            TypePaiementDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a typePaiement by label")
    @GetMapping("label/{label}")
    public ResponseEntity<TypePaiementDto> findByLabel(@PathVariable String label) {
	    TypePaiement t = service.findByReferenceEntity(new TypePaiement(label));
        if (t != null) {
            TypePaiementDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  typePaiement")
    @PostMapping("")
    public ResponseEntity<TypePaiementDto> save(@RequestBody TypePaiementDto dto) throws Exception {
        if(dto!=null){
            TypePaiement myT = converter.toItem(dto);
            TypePaiement t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                TypePaiementDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  typePaiement")
    @PutMapping("")
    public ResponseEntity<TypePaiementDto> update(@RequestBody TypePaiementDto dto) throws Exception {
        ResponseEntity<TypePaiementDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            TypePaiement t = service.findById(dto.getId());
            converter.copy(dto,t);
            TypePaiement updated = service.update(t);
            TypePaiementDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of typePaiement")
    @PostMapping("multiple")
    public ResponseEntity<List<TypePaiementDto>> delete(@RequestBody List<TypePaiementDto> dtos) throws Exception {
        ResponseEntity<List<TypePaiementDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<TypePaiement> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified typePaiement")
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


    @Operation(summary = "Finds a typePaiement and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<TypePaiementDto> findWithAssociatedLists(@PathVariable Long id) {
        TypePaiement loaded =  service.findWithAssociatedLists(id);
        TypePaiementDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds typePaiements by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<TypePaiementDto>> findByCriteria(@RequestBody TypePaiementCriteria criteria) throws Exception {
        ResponseEntity<List<TypePaiementDto>> res = null;
        List<TypePaiement> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypePaiementDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated typePaiements by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody TypePaiementCriteria criteria) throws Exception {
        List<TypePaiement> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<TypePaiementDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets typePaiement data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody TypePaiementCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<TypePaiementDto> findDtos(List<TypePaiement> list){
        List<TypePaiementDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<TypePaiementDto> getDtoResponseEntity(TypePaiementDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public TypePaiementRestAdmin(TypePaiementAdminService service, TypePaiementConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final TypePaiementAdminService service;
    private final TypePaiementConverter converter;





}
