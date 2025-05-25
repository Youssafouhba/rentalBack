package ma.zyn.app.ws.facade.admin.locataire;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.locataire.Avoir;
import ma.zyn.app.dao.criteria.core.locataire.AvoirCriteria;
import ma.zyn.app.service.facade.admin.locataire.AvoirAdminService;
import ma.zyn.app.ws.converter.locataire.AvoirConverter;
import ma.zyn.app.ws.dto.locataire.AvoirDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/avoir/")
public class AvoirRestAdmin {




    @Operation(summary = "Finds a list of all avoirs")
    @GetMapping("")
    public ResponseEntity<List<AvoirDto>> findAll() throws Exception {
        ResponseEntity<List<AvoirDto>> res = null;
        List<Avoir> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<AvoirDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }


    @Operation(summary = "Finds a avoir by id")
    @GetMapping("id/{id}")
    public ResponseEntity<AvoirDto> findById(@PathVariable Long id) {
        Avoir t = service.findById(id);
        if (t != null) {
            converter.init(true);
            AvoirDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Saves the specified  avoir")
    @PostMapping("")
    public ResponseEntity<AvoirDto> save(@RequestBody AvoirDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Avoir myT = converter.toItem(dto);
            Avoir t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                AvoirDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  avoir")
    @PutMapping("")
    public ResponseEntity<AvoirDto> update(@RequestBody AvoirDto dto) throws Exception {
        ResponseEntity<AvoirDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Avoir t = service.findById(dto.getId());
            converter.copy(dto,t);
            Avoir updated = service.update(t);
            AvoirDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of avoir")
    @PostMapping("multiple")
    public ResponseEntity<List<AvoirDto>> delete(@RequestBody List<AvoirDto> dtos) throws Exception {
        ResponseEntity<List<AvoirDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Avoir> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified avoir")
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


    @Operation(summary = "Finds a avoir and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<AvoirDto> findWithAssociatedLists(@PathVariable Long id) {
        Avoir loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        AvoirDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds avoirs by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<AvoirDto>> findByCriteria(@RequestBody AvoirCriteria criteria) throws Exception {
        ResponseEntity<List<AvoirDto>> res = null;
        List<Avoir> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<AvoirDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated avoirs by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody AvoirCriteria criteria) throws Exception {
        List<Avoir> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<AvoirDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets avoir data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody AvoirCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<AvoirDto> findDtos(List<Avoir> list){
        converter.initObject(true);
        List<AvoirDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<AvoirDto> getDtoResponseEntity(AvoirDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public AvoirRestAdmin(AvoirAdminService service, AvoirConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final AvoirAdminService service;
    private final AvoirConverter converter;





}
