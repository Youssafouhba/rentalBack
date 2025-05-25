package ma.zyn.app.ws.facade.admin.locaux;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.locaux.TypeLocal;
import ma.zyn.app.dao.criteria.core.locaux.TypeLocalCriteria;
import ma.zyn.app.service.facade.admin.locaux.TypeLocalAdminService;
import ma.zyn.app.ws.converter.locaux.TypeLocalConverter;
import ma.zyn.app.ws.dto.locaux.TypeLocalDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/typeLocal/")
public class TypeLocalRestAdmin {




    @Operation(summary = "Finds a list of all typeLocals")
    @GetMapping("")
    public ResponseEntity<List<TypeLocalDto>> findAll() throws Exception {
        ResponseEntity<List<TypeLocalDto>> res = null;
        List<TypeLocal> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypeLocalDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all typeLocals")
    @GetMapping("optimized")
    public ResponseEntity<List<TypeLocalDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<TypeLocalDto>> res = null;
        List<TypeLocal> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypeLocalDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a typeLocal by id")
    @GetMapping("id/{id}")
    public ResponseEntity<TypeLocalDto> findById(@PathVariable Long id) {
        TypeLocal t = service.findById(id);
        if (t != null) {
            TypeLocalDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a typeLocal by label")
    @GetMapping("label/{label}")
    public ResponseEntity<TypeLocalDto> findByLabel(@PathVariable String label) {
	    TypeLocal t = service.findByReferenceEntity(new TypeLocal(label));
        if (t != null) {
            TypeLocalDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  typeLocal")
    @PostMapping("")
    public ResponseEntity<TypeLocalDto> save(@RequestBody TypeLocalDto dto) throws Exception {
        if(dto!=null){
            TypeLocal myT = converter.toItem(dto);
            TypeLocal t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                TypeLocalDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  typeLocal")
    @PutMapping("")
    public ResponseEntity<TypeLocalDto> update(@RequestBody TypeLocalDto dto) throws Exception {
        ResponseEntity<TypeLocalDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            TypeLocal t = service.findById(dto.getId());
            converter.copy(dto,t);
            TypeLocal updated = service.update(t);
            TypeLocalDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of typeLocal")
    @PostMapping("multiple")
    public ResponseEntity<List<TypeLocalDto>> delete(@RequestBody List<TypeLocalDto> dtos) throws Exception {
        ResponseEntity<List<TypeLocalDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<TypeLocal> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified typeLocal")
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


    @Operation(summary = "Finds a typeLocal and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<TypeLocalDto> findWithAssociatedLists(@PathVariable Long id) {
        TypeLocal loaded =  service.findWithAssociatedLists(id);
        TypeLocalDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds typeLocals by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<TypeLocalDto>> findByCriteria(@RequestBody TypeLocalCriteria criteria) throws Exception {
        ResponseEntity<List<TypeLocalDto>> res = null;
        List<TypeLocal> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypeLocalDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated typeLocals by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody TypeLocalCriteria criteria) throws Exception {
        List<TypeLocal> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<TypeLocalDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets typeLocal data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody TypeLocalCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<TypeLocalDto> findDtos(List<TypeLocal> list){
        List<TypeLocalDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<TypeLocalDto> getDtoResponseEntity(TypeLocalDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public TypeLocalRestAdmin(TypeLocalAdminService service, TypeLocalConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final TypeLocalAdminService service;
    private final TypeLocalConverter converter;





}
