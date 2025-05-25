package ma.zyn.app.ws.facade.admin.locaux;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.dao.criteria.core.locaux.LocalCriteria;
import ma.zyn.app.service.facade.admin.locaux.LocalAdminService;
import ma.zyn.app.ws.converter.locaux.LocalConverter;
import ma.zyn.app.ws.dto.locaux.LocalDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/local/")
public class LocalRestAdmin {




    @Operation(summary = "Finds a list of all locals")
    @GetMapping("")
    public ResponseEntity<List<LocalDto>> findAll() throws Exception {
        ResponseEntity<List<LocalDto>> res = null;
        List<Local> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
            converter.initObject(true);
        List<LocalDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all locals")
    @GetMapping("optimized")
    public ResponseEntity<List<LocalDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<LocalDto>> res = null;
        List<Local> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<LocalDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a local by id")
    @GetMapping("id/{id}")
    public ResponseEntity<LocalDto> findById(@PathVariable Long id) {
        Local t = service.findById(id);
        if (t != null) {
            converter.init(true);
            LocalDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a local by code")
    @GetMapping("code/{code}")
    public ResponseEntity<LocalDto> findByCode(@PathVariable String code) {
	    Local t = service.findByReferenceEntity(new Local(code));
        if (t != null) {
            converter.init(true);
            LocalDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  local")
    @PostMapping("")
    public ResponseEntity<LocalDto> save(@RequestBody LocalDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Local myT = converter.toItem(dto);
            Local t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                LocalDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  local")
    @PutMapping("")
    public ResponseEntity<LocalDto> update(@RequestBody LocalDto dto) throws Exception {
        ResponseEntity<LocalDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Local t = service.findById(dto.getId());
            converter.copy(dto,t);
            Local updated = service.update(t);
            LocalDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of local")
    @PostMapping("multiple")
    public ResponseEntity<List<LocalDto>> delete(@RequestBody List<LocalDto> dtos) throws Exception {
        ResponseEntity<List<LocalDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Local> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified local")
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

    @Operation(summary = "find by statutLocal code")
    @GetMapping("statutLocal/code/{code}")
    public List<LocalDto> findByStatutLocalCode(@PathVariable String code){
        return findDtos(service.findByStatutLocalCode(code));
    }
    @Operation(summary = "delete by statutLocal code")
    @DeleteMapping("statutLocal/code/{code}")
    public int deleteByStatutLocalCode(@PathVariable String code){
        return service.deleteByStatutLocalCode(code);
    }
    @Operation(summary = "find by typeLocal code")
    @GetMapping("typeLocal/code/{code}")
    public List<LocalDto> findByTypeLocalCode(@PathVariable String code){
        return findDtos(service.findByTypeLocalCode(code));
    }
    @Operation(summary = "delete by typeLocal code")
    @DeleteMapping("typeLocal/code/{code}")
    public int deleteByTypeLocalCode(@PathVariable String code){
        return service.deleteByTypeLocalCode(code);
    }

    @Operation(summary = "Finds a local and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<LocalDto> findWithAssociatedLists(@PathVariable Long id) {
        Local loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        LocalDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds locals by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<LocalDto>> findByCriteria(@RequestBody LocalCriteria criteria) throws Exception {
        ResponseEntity<List<LocalDto>> res = null;
        List<Local> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<LocalDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated locals by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody LocalCriteria criteria) throws Exception {
        List<Local> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initList(true);
        converter.initObject(true);
        List<LocalDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets local data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody LocalCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<LocalDto> findDtos(List<Local> list){
        converter.initList(false);
        converter.initObject(true);
        List<LocalDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<LocalDto> getDtoResponseEntity(LocalDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public LocalRestAdmin(LocalAdminService service, LocalConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final LocalAdminService service;
    private final LocalConverter converter;





}
