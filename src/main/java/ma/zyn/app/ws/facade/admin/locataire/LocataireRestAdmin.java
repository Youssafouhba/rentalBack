package ma.zyn.app.ws.facade.admin.locataire;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.dao.criteria.core.locataire.LocataireCriteria;
import ma.zyn.app.service.facade.admin.locataire.LocataireAdminService;
import ma.zyn.app.ws.converter.locataire.LocataireConverter;
import ma.zyn.app.ws.dto.locataire.LocataireDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/locataire/")
public class LocataireRestAdmin {




    @Operation(summary = "Finds a list of all locataires")
    @GetMapping("")
    public ResponseEntity<List<LocataireDto>> findAll() throws Exception {
        ResponseEntity<List<LocataireDto>> res = null;
        List<Locataire> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
            converter.initObject(true);
        List<LocataireDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all locataires")
    @GetMapping("optimized")
    public ResponseEntity<List<LocataireDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<LocataireDto>> res = null;
        List<Locataire> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<LocataireDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a locataire by id")
    @GetMapping("id/{id}")
    public ResponseEntity<LocataireDto> findById(@PathVariable Long id) {
        Locataire t = service.findById(id);
        if (t != null) {
            converter.init(true);
            LocataireDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a locataire by code")
    @GetMapping("code/{code}")
    public ResponseEntity<LocataireDto> findByCode(@PathVariable String code) {
	    Locataire t = service.findByReferenceEntity(new Locataire(code));
        if (t != null) {
            converter.init(true);
            LocataireDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  locataire")
    @PostMapping("")
    public ResponseEntity<LocataireDto> save(@RequestBody LocataireDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Locataire myT = converter.toItem(dto);
            Locataire t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                LocataireDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  locataire")
    @PutMapping("")
    public ResponseEntity<LocataireDto> update(@RequestBody LocataireDto dto) throws Exception {
        ResponseEntity<LocataireDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Locataire t = service.findById(dto.getId());
            converter.copy(dto,t);
            Locataire updated = service.update(t);
            LocataireDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of locataire")
    @PostMapping("multiple")
    public ResponseEntity<List<LocataireDto>> delete(@RequestBody List<LocataireDto> dtos) throws Exception {
        ResponseEntity<List<LocataireDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Locataire> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified locataire")
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


    @Operation(summary = "Finds a locataire and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<LocataireDto> findWithAssociatedLists(@PathVariable Long id) {
        Locataire loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        LocataireDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds locataires by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<LocataireDto>> findByCriteria(@RequestBody LocataireCriteria criteria) throws Exception {
        ResponseEntity<List<LocataireDto>> res = null;
        List<Locataire> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<LocataireDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated locataires by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody LocataireCriteria criteria) throws Exception {
        List<Locataire> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initList(false);
        converter.initObject(true);
        List<LocataireDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets locataire data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody LocataireCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<LocataireDto> findDtos(List<Locataire> list){
        converter.initList(false);
        converter.initObject(true);
        List<LocataireDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<LocataireDto> getDtoResponseEntity(LocataireDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public LocataireRestAdmin(LocataireAdminService service, LocataireConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final LocataireAdminService service;
    private final LocataireConverter converter;





}
