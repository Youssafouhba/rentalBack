package ma.zyn.app.ws.facade.admin.locataire;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.locataire.TypeTransactiont;
import ma.zyn.app.dao.criteria.core.locataire.TypeTransactiontCriteria;
import ma.zyn.app.service.facade.admin.locataire.TypeTransactiontAdminService;
import ma.zyn.app.ws.converter.locataire.TypeTransactiontConverter;
import ma.zyn.app.ws.dto.locataire.TypeTransactiontDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/typeTransactiont/")
public class TypeTransactiontRestAdmin {




    @Operation(summary = "Finds a list of all typeTransactionts")
    @GetMapping("")
    public ResponseEntity<List<TypeTransactiontDto>> findAll() throws Exception {
        ResponseEntity<List<TypeTransactiontDto>> res = null;
        List<TypeTransactiont> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypeTransactiontDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all typeTransactionts")
    @GetMapping("optimized")
    public ResponseEntity<List<TypeTransactiontDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<TypeTransactiontDto>> res = null;
        List<TypeTransactiont> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypeTransactiontDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a typeTransactiont by id")
    @GetMapping("id/{id}")
    public ResponseEntity<TypeTransactiontDto> findById(@PathVariable Long id) {
        TypeTransactiont t = service.findById(id);
        if (t != null) {
            TypeTransactiontDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a typeTransactiont by label")
    @GetMapping("label/{label}")
    public ResponseEntity<TypeTransactiontDto> findByLabel(@PathVariable String label) {
	    TypeTransactiont t = service.findByReferenceEntity(new TypeTransactiont(label));
        if (t != null) {
            TypeTransactiontDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  typeTransactiont")
    @PostMapping("")
    public ResponseEntity<TypeTransactiontDto> save(@RequestBody TypeTransactiontDto dto) throws Exception {
        if(dto!=null){
            TypeTransactiont myT = converter.toItem(dto);
            TypeTransactiont t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                TypeTransactiontDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  typeTransactiont")
    @PutMapping("")
    public ResponseEntity<TypeTransactiontDto> update(@RequestBody TypeTransactiontDto dto) throws Exception {
        ResponseEntity<TypeTransactiontDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            TypeTransactiont t = service.findById(dto.getId());
            converter.copy(dto,t);
            TypeTransactiont updated = service.update(t);
            TypeTransactiontDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of typeTransactiont")
    @PostMapping("multiple")
    public ResponseEntity<List<TypeTransactiontDto>> delete(@RequestBody List<TypeTransactiontDto> dtos) throws Exception {
        ResponseEntity<List<TypeTransactiontDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<TypeTransactiont> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified typeTransactiont")
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


    @Operation(summary = "Finds a typeTransactiont and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<TypeTransactiontDto> findWithAssociatedLists(@PathVariable Long id) {
        TypeTransactiont loaded =  service.findWithAssociatedLists(id);
        TypeTransactiontDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds typeTransactionts by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<TypeTransactiontDto>> findByCriteria(@RequestBody TypeTransactiontCriteria criteria) throws Exception {
        ResponseEntity<List<TypeTransactiontDto>> res = null;
        List<TypeTransactiont> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypeTransactiontDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated typeTransactionts by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody TypeTransactiontCriteria criteria) throws Exception {
        List<TypeTransactiont> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<TypeTransactiontDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets typeTransactiont data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody TypeTransactiontCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<TypeTransactiontDto> findDtos(List<TypeTransactiont> list){
        List<TypeTransactiontDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<TypeTransactiontDto> getDtoResponseEntity(TypeTransactiontDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public TypeTransactiontRestAdmin(TypeTransactiontAdminService service, TypeTransactiontConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final TypeTransactiontAdminService service;
    private final TypeTransactiontConverter converter;





}
