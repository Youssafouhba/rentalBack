package ma.zyn.app.ws.facade.admin.finance;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.dao.criteria.core.finance.CompteLocataireCriteria;
import ma.zyn.app.service.facade.admin.finance.CompteLocataireAdminService;
import ma.zyn.app.ws.converter.finance.CompteLocataireConverter;
import ma.zyn.app.ws.dto.finance.CompteLocataireDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/compteLocataire/")
public class CompteLocataireRestAdmin {




    @Operation(summary = "Finds a list of all compteLocataires")
    @GetMapping("")
    public ResponseEntity<List<CompteLocataireDto>> findAll() throws Exception {
        ResponseEntity<List<CompteLocataireDto>> res = null;
        List<CompteLocataire> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<CompteLocataireDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }


    @Operation(summary = "Finds a compteLocataire by id")
    @GetMapping("id/{id}")
    public ResponseEntity<CompteLocataireDto> findById(@PathVariable Long id) {
        CompteLocataire t = service.findById(id);
        if (t != null) {
            converter.init(true);
            CompteLocataireDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Saves the specified  compteLocataire")
    @PostMapping("")
    public ResponseEntity<CompteLocataireDto> save(@RequestBody CompteLocataireDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            CompteLocataire myT = converter.toItem(dto);
            CompteLocataire t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                CompteLocataireDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  compteLocataire")
    @PutMapping("")
    public ResponseEntity<CompteLocataireDto> update(@RequestBody CompteLocataireDto dto) throws Exception {
        ResponseEntity<CompteLocataireDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            CompteLocataire t = service.findById(dto.getId());
            converter.copy(dto,t);
            CompteLocataire updated = service.update(t);
            CompteLocataireDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of compteLocataire")
    @PostMapping("multiple")
    public ResponseEntity<List<CompteLocataireDto>> delete(@RequestBody List<CompteLocataireDto> dtos) throws Exception {
        ResponseEntity<List<CompteLocataireDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<CompteLocataire> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified compteLocataire")
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

    @Operation(summary = "Finds a compteLocataire and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<CompteLocataireDto> findWithAssociatedLists(@PathVariable Long id) {
        CompteLocataire loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        CompteLocataireDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds compteLocataires by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<CompteLocataireDto>> findByCriteria(@RequestBody CompteLocataireCriteria criteria) throws Exception {
        ResponseEntity<List<CompteLocataireDto>> res = null;
        List<CompteLocataire> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<CompteLocataireDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated compteLocataires by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody CompteLocataireCriteria criteria) throws Exception {
        List<CompteLocataire> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initList(false);
        converter.initObject(true);
        List<CompteLocataireDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets compteLocataire data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody CompteLocataireCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }

    public List<CompteLocataireDto> findDtos(List<CompteLocataire> list){
        converter.initList(false);
        converter.initObject(true);
        List<CompteLocataireDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<CompteLocataireDto> getDtoResponseEntity(CompteLocataireDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




    public CompteLocataireRestAdmin(CompteLocataireAdminService service, CompteLocataireConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final CompteLocataireAdminService service;
    private final CompteLocataireConverter converter;





}
