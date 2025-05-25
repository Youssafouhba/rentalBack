package ma.zyn.app.ws.facade.admin.finance;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.finance.CompteInstantanee;
import ma.zyn.app.dao.criteria.core.finance.CompteInstantaneeCriteria;
import ma.zyn.app.service.facade.admin.finance.CompteInstantaneeAdminService;
import ma.zyn.app.ws.converter.finance.CompteInstantaneeConverter;
import ma.zyn.app.ws.dto.finance.CompteInstantaneeDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/CompteInstantanee/")
public class CompteInstantaneeRestAdmin {




    @Operation(summary = "Finds a list of all CompteInstantanees")
    @GetMapping("")
    public ResponseEntity<List<CompteInstantaneeDto>> findAll() throws Exception {
        ResponseEntity<List<CompteInstantaneeDto>> res = null;
        List<CompteInstantanee> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<CompteInstantaneeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }


    @Operation(summary = "Finds a CompteInstantanee by id")
    @GetMapping("id/{id}")
    public ResponseEntity<CompteInstantaneeDto> findById(@PathVariable Long id) {
        CompteInstantanee t = service.findById(id);
        if (t != null) {
            converter.init(true);
            CompteInstantaneeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Saves the specified  CompteInstantanee")
    @PostMapping("")
    public ResponseEntity<CompteInstantaneeDto> save(@RequestBody CompteInstantaneeDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            CompteInstantanee myT = converter.toItem(dto);
            CompteInstantanee t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                CompteInstantaneeDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  CompteInstantanee")
    @PutMapping("")
    public ResponseEntity<CompteInstantaneeDto> update(@RequestBody CompteInstantaneeDto dto) throws Exception {
        ResponseEntity<CompteInstantaneeDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            CompteInstantanee t = service.findById(dto.getId());
            converter.copy(dto,t);
            CompteInstantanee updated = service.update(t);
            CompteInstantaneeDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of CompteInstantanee")
    @PostMapping("multiple")
    public ResponseEntity<List<CompteInstantaneeDto>> delete(@RequestBody List<CompteInstantaneeDto> dtos) throws Exception {
        ResponseEntity<List<CompteInstantaneeDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<CompteInstantanee> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified CompteInstantanee")
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


    @Operation(summary = "delete by locataire id")
    @DeleteMapping("locataire/id/{id}")
    public int deleteByLocataireId(@PathVariable Long id){
        return service.deleteByLocataireId(id);
    }

    @Operation(summary = "Finds a CompteInstantanee and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<CompteInstantaneeDto> findWithAssociatedLists(@PathVariable Long id) {
        CompteInstantanee loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        CompteInstantaneeDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds CompteInstantanees by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<CompteInstantaneeDto>> findByCriteria(@RequestBody CompteInstantaneeCriteria criteria) throws Exception {
        ResponseEntity<List<CompteInstantaneeDto>> res = null;
        List<CompteInstantanee> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<CompteInstantaneeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated CompteInstantanees by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody CompteInstantaneeCriteria criteria) throws Exception {
        List<CompteInstantanee> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initList(false);
        converter.initObject(true);
        List<CompteInstantaneeDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets CompteInstantanee data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody CompteInstantaneeCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }

    public List<CompteInstantaneeDto> findDtos(List<CompteInstantanee> list){
        converter.initList(false);
        converter.initObject(true);
        List<CompteInstantaneeDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<CompteInstantaneeDto> getDtoResponseEntity(CompteInstantaneeDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




    public CompteInstantaneeRestAdmin(CompteInstantaneeAdminService service, CompteInstantaneeConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final CompteInstantaneeAdminService service;
    private final CompteInstantaneeConverter converter;





}
