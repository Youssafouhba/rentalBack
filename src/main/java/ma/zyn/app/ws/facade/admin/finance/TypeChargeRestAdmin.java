package ma.zyn.app.ws.facade.admin.finance;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.finance.TypeCharge;
import ma.zyn.app.dao.criteria.core.finance.TypeChargeCriteria;
import ma.zyn.app.service.facade.admin.finance.TypeChargeAdminService;
import ma.zyn.app.ws.converter.finance.TypeChargeConverter;
import ma.zyn.app.ws.dto.finance.TypeChargeDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/admin/typeCharge/")
public class TypeChargeRestAdmin {




    @Operation(summary = "Finds a list of all typeCharges")
    @GetMapping("")
    public ResponseEntity<List<TypeChargeDto>> findAll() throws Exception {
        ResponseEntity<List<TypeChargeDto>> res = null;
        List<TypeCharge> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypeChargeDto> dtos  = converter.toDto(new HashSet<>(list)).stream().toList();
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all typeCharges")
    @GetMapping("optimized")
    public ResponseEntity<List<TypeChargeDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<TypeChargeDto>> res = null;
        List<TypeCharge> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypeChargeDto> dtos  = converter.toDto(new HashSet<>(list)).stream().toList();
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a typeCharge by id")
    @GetMapping("id/{id}")
    public ResponseEntity<TypeChargeDto> findById(@PathVariable Long id) {
        TypeCharge t = service.findById(id);
        if (t != null) {
            TypeChargeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a typeCharge by label")
    @GetMapping("label/{label}")
    public ResponseEntity<TypeChargeDto> findByLabel(@PathVariable String label) {
	    TypeCharge t = service.findByReferenceEntity(new TypeCharge(label));
        if (t != null) {
            TypeChargeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  typeCharge")
    @PostMapping("")
    public ResponseEntity<TypeChargeDto> save(@RequestBody TypeChargeDto dto) throws Exception {
        if(dto!=null){
            TypeCharge myT = converter.toItem(dto);
            TypeCharge t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                TypeChargeDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  typeCharge")
    @PutMapping("")
    public ResponseEntity<TypeChargeDto> update(@RequestBody TypeChargeDto dto) throws Exception {
        ResponseEntity<TypeChargeDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            TypeCharge t = service.findById(dto.getId());
            converter.copy(dto,t);
            TypeCharge updated = service.update(t);
            TypeChargeDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of typeCharge")
    @PostMapping("multiple")
    public ResponseEntity<List<TypeChargeDto>> delete(@RequestBody List<TypeChargeDto> dtos) throws Exception {
        ResponseEntity<List<TypeChargeDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<TypeCharge> ts = converter.toItem(new HashSet<>(dtos)).stream().toList();
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified typeCharge")
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


    @Operation(summary = "Finds a typeCharge and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<TypeChargeDto> findWithAssociatedLists(@PathVariable Long id) {
        TypeCharge loaded =  service.findWithAssociatedLists(id);
        TypeChargeDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds typeCharges by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<TypeChargeDto>> findByCriteria(@RequestBody TypeChargeCriteria criteria) throws Exception {
        ResponseEntity<List<TypeChargeDto>> res = null;
        List<TypeCharge> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypeChargeDto> dtos  = converter.toDto(new HashSet<>(list)).stream().toList();
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated typeCharges by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody TypeChargeCriteria criteria) throws Exception {
        List<TypeCharge> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<TypeChargeDto> dtos = converter.toDto(new HashSet<>(list)).stream().toList();
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets typeCharge data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody TypeChargeCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<TypeChargeDto> findDtos(List<TypeCharge> list){
        List<TypeChargeDto> dtos = converter.toDto(new HashSet<>(list)).stream().toList();
        return dtos;
    }

    private ResponseEntity<TypeChargeDto> getDtoResponseEntity(TypeChargeDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public TypeChargeRestAdmin(TypeChargeAdminService service, TypeChargeConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final TypeChargeAdminService service;
    private final TypeChargeConverter converter;





}
