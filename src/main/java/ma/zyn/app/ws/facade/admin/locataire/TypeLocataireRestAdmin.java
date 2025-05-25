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

import ma.zyn.app.bean.core.locataire.TypeLocataire;
import ma.zyn.app.dao.criteria.core.locataire.TypeLocataireCriteria;
import ma.zyn.app.service.facade.admin.locataire.TypeLocataireAdminService;
import ma.zyn.app.ws.converter.locataire.TypeLocataireConverter;
import ma.zyn.app.ws.dto.locataire.TypeLocataireDto;
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
@RequestMapping("/api/admin/typeLocataire/")
public class TypeLocataireRestAdmin {




    @Operation(summary = "Finds a list of all typeLocataires")
    @GetMapping("")
    public ResponseEntity<List<TypeLocataireDto>> findAll() throws Exception {
        ResponseEntity<List<TypeLocataireDto>> res = null;
        List<TypeLocataire> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypeLocataireDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all typeLocataires")
    @GetMapping("optimized")
    public ResponseEntity<List<TypeLocataireDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<TypeLocataireDto>> res = null;
        List<TypeLocataire> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypeLocataireDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a typeLocataire by id")
    @GetMapping("id/{id}")
    public ResponseEntity<TypeLocataireDto> findById(@PathVariable Long id) {
        TypeLocataire t = service.findById(id);
        if (t != null) {
            TypeLocataireDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a typeLocataire by label")
    @GetMapping("label/{label}")
    public ResponseEntity<TypeLocataireDto> findByLabel(@PathVariable String label) {
	    TypeLocataire t = service.findByReferenceEntity(new TypeLocataire(label));
        if (t != null) {
            TypeLocataireDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  typeLocataire")
    @PostMapping("")
    public ResponseEntity<TypeLocataireDto> save(@RequestBody TypeLocataireDto dto) throws Exception {
        if(dto!=null){
            TypeLocataire myT = converter.toItem(dto);
            TypeLocataire t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                TypeLocataireDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  typeLocataire")
    @PutMapping("")
    public ResponseEntity<TypeLocataireDto> update(@RequestBody TypeLocataireDto dto) throws Exception {
        ResponseEntity<TypeLocataireDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            TypeLocataire t = service.findById(dto.getId());
            converter.copy(dto,t);
            TypeLocataire updated = service.update(t);
            TypeLocataireDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of typeLocataire")
    @PostMapping("multiple")
    public ResponseEntity<List<TypeLocataireDto>> delete(@RequestBody List<TypeLocataireDto> dtos) throws Exception {
        ResponseEntity<List<TypeLocataireDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<TypeLocataire> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified typeLocataire")
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


    @Operation(summary = "Finds a typeLocataire and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<TypeLocataireDto> findWithAssociatedLists(@PathVariable Long id) {
        TypeLocataire loaded =  service.findWithAssociatedLists(id);
        TypeLocataireDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds typeLocataires by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<TypeLocataireDto>> findByCriteria(@RequestBody TypeLocataireCriteria criteria) throws Exception {
        ResponseEntity<List<TypeLocataireDto>> res = null;
        List<TypeLocataire> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<TypeLocataireDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated typeLocataires by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody TypeLocataireCriteria criteria) throws Exception {
        List<TypeLocataire> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<TypeLocataireDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets typeLocataire data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody TypeLocataireCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<TypeLocataireDto> findDtos(List<TypeLocataire> list){
        List<TypeLocataireDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<TypeLocataireDto> getDtoResponseEntity(TypeLocataireDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public TypeLocataireRestAdmin(TypeLocataireAdminService service, TypeLocataireConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final TypeLocataireAdminService service;
    private final TypeLocataireConverter converter;





}
