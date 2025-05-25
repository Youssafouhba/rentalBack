package  ma.zyn.app.ws.facade.admin.finance;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.ArrayList;

import ma.zyn.app.bean.core.finance.Caisse;
import ma.zyn.app.dao.criteria.core.finance.CaisseCriteria;
import ma.zyn.app.service.facade.admin.finance.CaisseAdminService;
import ma.zyn.app.ws.converter.finance.CaisseConverter;
import ma.zyn.app.ws.dto.finance.CaisseDto;
import ma.zyn.app.zynerator.controller.AbstractController;
import ma.zyn.app.zynerator.dto.AuditEntityDto;
import ma.zyn.app.zynerator.util.PaginatedList;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ma.zyn.app.zynerator.process.Result;


import org.springframework.web.multipart.MultipartFile;
import ma.zyn.app.zynerator.dto.FileTempDto;

@RestController
@RequestMapping("/api/admin/caisse/")
public class CaisseRestAdmin {




    @Operation(summary = "Finds a list of all caisses")
    @GetMapping("")
    public ResponseEntity<List<CaisseDto>> findAll() throws Exception {
        ResponseEntity<List<CaisseDto>> res = null;
        List<Caisse> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<CaisseDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all caisses")
    @GetMapping("optimized")
    public ResponseEntity<List<CaisseDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<CaisseDto>> res = null;
        List<Caisse> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<CaisseDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a caisse by id")
    @GetMapping("id/{id}")
    public ResponseEntity<CaisseDto> findById(@PathVariable Long id) {
        Caisse t = service.findById(id);
        if (t != null) {
            CaisseDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a caisse by libelle")
    @GetMapping("libelle/{libelle}")
    public ResponseEntity<CaisseDto> findByLibelle(@PathVariable String libelle) {
	    Caisse t = service.findByReferenceEntity(new Caisse(libelle));
        if (t != null) {
            CaisseDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  caisse")
    @PostMapping("")
    public ResponseEntity<CaisseDto> save(@RequestBody CaisseDto dto) throws Exception {
        if(dto!=null){
            Caisse myT = converter.toItem(dto);
            Caisse t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                CaisseDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  caisse")
    @PutMapping("")
    public ResponseEntity<CaisseDto> update(@RequestBody CaisseDto dto) throws Exception {
        ResponseEntity<CaisseDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Caisse t = service.findById(dto.getId());
            converter.copy(dto,t);
            Caisse updated = service.update(t);
            CaisseDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of caisse")
    @PostMapping("multiple")
    public ResponseEntity<List<CaisseDto>> delete(@RequestBody List<CaisseDto> dtos) throws Exception {
        ResponseEntity<List<CaisseDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<Caisse> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified caisse")
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


    @Operation(summary = "Finds a caisse and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<CaisseDto> findWithAssociatedLists(@PathVariable Long id) {
        Caisse loaded =  service.findWithAssociatedLists(id);
        CaisseDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds caisses by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<CaisseDto>> findByCriteria(@RequestBody CaisseCriteria criteria) throws Exception {
        ResponseEntity<List<CaisseDto>> res = null;
        List<Caisse> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<CaisseDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated caisses by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody CaisseCriteria criteria) throws Exception {
        List<Caisse> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<CaisseDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets caisse data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody CaisseCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<CaisseDto> findDtos(List<Caisse> list){
        List<CaisseDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<CaisseDto> getDtoResponseEntity(CaisseDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public CaisseRestAdmin(CaisseAdminService service, CaisseConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final CaisseAdminService service;
    private final CaisseConverter converter;





}
