package  ma.zyn.app.ws.facade.admin.locataire;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.ArrayList;

import ma.zyn.app.bean.core.locataire.StatutLocal;
import ma.zyn.app.dao.criteria.core.locataire.StatutLocalCriteria;
import ma.zyn.app.service.facade.admin.locataire.StatutLocalAdminService;
import ma.zyn.app.ws.converter.locataire.StatutLocalConverter;
import ma.zyn.app.ws.dto.locataire.StatutLocalDto;
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
@RequestMapping("/api/admin/statutLocal/")
public class StatutLocalRestAdmin {




    @Operation(summary = "Finds a list of all statutLocals")
    @GetMapping("")
    public ResponseEntity<List<StatutLocalDto>> findAll() throws Exception {
        ResponseEntity<List<StatutLocalDto>> res = null;
        List<StatutLocal> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<StatutLocalDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all statutLocals")
    @GetMapping("optimized")
    public ResponseEntity<List<StatutLocalDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<StatutLocalDto>> res = null;
        List<StatutLocal> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<StatutLocalDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a statutLocal by id")
    @GetMapping("id/{id}")
    public ResponseEntity<StatutLocalDto> findById(@PathVariable Long id) {
        StatutLocal t = service.findById(id);
        if (t != null) {
            StatutLocalDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a statutLocal by label")
    @GetMapping("label/{label}")
    public ResponseEntity<StatutLocalDto> findByLabel(@PathVariable String label) {
	    StatutLocal t = service.findByReferenceEntity(new StatutLocal(label));
        if (t != null) {
            StatutLocalDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  statutLocal")
    @PostMapping("")
    public ResponseEntity<StatutLocalDto> save(@RequestBody StatutLocalDto dto) throws Exception {
        if(dto!=null){
            StatutLocal myT = converter.toItem(dto);
            StatutLocal t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                StatutLocalDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  statutLocal")
    @PutMapping("")
    public ResponseEntity<StatutLocalDto> update(@RequestBody StatutLocalDto dto) throws Exception {
        ResponseEntity<StatutLocalDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            StatutLocal t = service.findById(dto.getId());
            converter.copy(dto,t);
            StatutLocal updated = service.update(t);
            StatutLocalDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of statutLocal")
    @PostMapping("multiple")
    public ResponseEntity<List<StatutLocalDto>> delete(@RequestBody List<StatutLocalDto> dtos) throws Exception {
        ResponseEntity<List<StatutLocalDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<StatutLocal> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified statutLocal")
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


    @Operation(summary = "Finds a statutLocal and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<StatutLocalDto> findWithAssociatedLists(@PathVariable Long id) {
        StatutLocal loaded =  service.findWithAssociatedLists(id);
        StatutLocalDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds statutLocals by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<StatutLocalDto>> findByCriteria(@RequestBody StatutLocalCriteria criteria) throws Exception {
        ResponseEntity<List<StatutLocalDto>> res = null;
        List<StatutLocal> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<StatutLocalDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated statutLocals by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody StatutLocalCriteria criteria) throws Exception {
        List<StatutLocal> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<StatutLocalDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets statutLocal data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody StatutLocalCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<StatutLocalDto> findDtos(List<StatutLocal> list){
        List<StatutLocalDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<StatutLocalDto> getDtoResponseEntity(StatutLocalDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public StatutLocalRestAdmin(StatutLocalAdminService service, StatutLocalConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final StatutLocalAdminService service;
    private final StatutLocalConverter converter;





}
