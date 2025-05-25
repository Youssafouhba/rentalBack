package ma.zyn.app.ws.facade.admin.locataire;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.locataire.Reglement;
import ma.zyn.app.dao.criteria.core.locataire.ReglementCriteria;
import ma.zyn.app.service.facade.admin.locataire.ReglementAdminService;
import ma.zyn.app.ws.converter.locataire.ReglementConverter;
import ma.zyn.app.ws.dto.locataire.ReglementDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reglement/")
public class ReglementRestAdmin {




    @Operation(summary = "Finds a list of all reglements")
    @GetMapping("")
    public ResponseEntity<List<ReglementDto>> findAll() throws Exception {
        ResponseEntity<List<ReglementDto>> res = null;
        List<Reglement> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<ReglementDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }


    @Operation(summary = "Finds a reglement by id")
    @GetMapping("id/{id}")
    public ResponseEntity<ReglementDto> findById(@PathVariable Long id) {
        Reglement t = service.findById(id);
        if (t != null) {
            converter.init(true);
            ReglementDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Saves the specified  reglement")
    @PostMapping("")
    public ResponseEntity<ReglementDto> save(@RequestBody ReglementDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Reglement myT = converter.toItem(dto);
            Reglement t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                ReglementDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  reglement")
    @PutMapping("")
    public ResponseEntity<ReglementDto> update(@RequestBody ReglementDto dto) throws Exception {
        ResponseEntity<ReglementDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Reglement t = service.findById(dto.getId());
            converter.copy(dto,t);
            Reglement updated = service.update(t);
            ReglementDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of reglement")
    @PostMapping("multiple")
    public ResponseEntity<List<ReglementDto>> delete(@RequestBody List<ReglementDto> dtos) throws Exception {
        ResponseEntity<List<ReglementDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Reglement> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified reglement")
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


    @Operation(summary = "Finds a reglement and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<ReglementDto> findWithAssociatedLists(@PathVariable Long id) {
        Reglement loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        ReglementDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds reglements by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<ReglementDto>> findByCriteria(@RequestBody ReglementCriteria criteria) throws Exception {
        ResponseEntity<List<ReglementDto>> res = null;
        List<Reglement> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<ReglementDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated reglements by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody ReglementCriteria criteria) throws Exception {
        List<Reglement> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<ReglementDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets reglement data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody ReglementCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<ReglementDto> findDtos(List<Reglement> list){
        converter.initObject(true);
        List<ReglementDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<ReglementDto> getDtoResponseEntity(ReglementDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public ReglementRestAdmin(ReglementAdminService service, ReglementConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final ReglementAdminService service;
    private final ReglementConverter converter;





}
