package ma.zyn.app.ws.facade.admin.finance;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.finance.CompteAdmin;
import ma.zyn.app.bean.core.finance.CompteInstantanee;
import ma.zyn.app.dao.criteria.core.finance.CompteAdminCriteria;
import ma.zyn.app.service.facade.admin.finance.CompteAdminAdminService;
import ma.zyn.app.ws.converter.finance.CompteAdminConverter;
import ma.zyn.app.ws.dto.finance.CompteAdminDto;
import ma.zyn.app.ws.dto.finance.CompteInstantaneeDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/compteAdmin/")
public class CompteAdminRestAdmin {




    @Operation(summary = "Finds a list of all compteAdmins")
    @GetMapping("")
    public ResponseEntity<List<CompteAdminDto>> findAll() throws Exception {
        ResponseEntity<List<CompteAdminDto>> res = null;
        List<CompteAdmin> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        List<CompteAdminDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }


    @Operation(summary = "Finds a compteAdmin by id")
    @GetMapping("id/{id}")
    public ResponseEntity<CompteAdminDto> findById(@PathVariable Long id) {
        CompteAdmin t = service.findById(id);
        if (t != null) {
            converter.init(true);
            CompteAdminDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }





    @Operation(summary = "Saves the specified  compteAdmin")
    @PostMapping("")
    public ResponseEntity<CompteAdminDto> save(@RequestBody CompteAdminDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            CompteAdmin myT = converter.toItem(dto);
            CompteAdmin t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                CompteAdminDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  compteAdmin")
    @PutMapping("")
    public ResponseEntity<CompteAdminDto> update(@RequestBody CompteAdminDto dto) throws Exception {
        ResponseEntity<CompteAdminDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            CompteAdmin t = service.findById(dto.getId());
            converter.copy(dto,t);
            CompteAdmin updated = service.update(t);
            CompteAdminDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of compteAdmin")
    @PostMapping("multiple")
    public ResponseEntity<List<CompteAdminDto>> delete(@RequestBody List<CompteAdminDto> dtos) throws Exception {
        ResponseEntity<List<CompteAdminDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<CompteAdmin> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified compteAdmin")
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


    @Operation(summary = "Finds a compteAdmin and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<CompteAdminDto> findWithAssociatedLists(@PathVariable Long id) {
        CompteAdmin loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        CompteAdminDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds compteAdmins by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<CompteAdminDto>> findByCriteria(@RequestBody CompteAdminCriteria criteria) throws Exception {
        ResponseEntity<List<CompteAdminDto>> res = null;
        List<CompteAdmin> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);

        List<CompteAdminDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated compteAdmins by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody CompteAdminCriteria criteria) throws Exception {
        List<CompteAdmin> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initList(false);
        List<CompteAdminDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets compteAdmin data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody CompteAdminCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<CompteAdminDto> findDtos(List<CompteAdmin> list){
        converter.initList(false);
        List<CompteAdminDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<CompteAdminDto> getDtoResponseEntity(CompteAdminDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public CompteAdminRestAdmin(CompteAdminAdminService service, CompteAdminConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final CompteAdminAdminService service;
    private final CompteAdminConverter converter;





}
