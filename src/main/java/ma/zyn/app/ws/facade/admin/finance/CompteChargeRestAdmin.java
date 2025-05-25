package ma.zyn.app.ws.facade.admin.finance;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.finance.CompteCharge;
import ma.zyn.app.dao.criteria.core.finance.CompteChargeCriteria;
import ma.zyn.app.service.facade.admin.finance.CompteChargeAdminService;
import ma.zyn.app.ws.converter.finance.CompteChargeConverter;
import ma.zyn.app.ws.dto.finance.CompteChargeDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/compteCharge/")
public class CompteChargeRestAdmin {




    @Operation(summary = "Finds a list of all compteCharges")
    @GetMapping("")
    public ResponseEntity<List<CompteChargeDto>> findAll() throws Exception {
        ResponseEntity<List<CompteChargeDto>> res = null;
        List<CompteCharge> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        List<CompteChargeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all compteCharges")
    @GetMapping("optimized")
    public ResponseEntity<List<CompteChargeDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<CompteChargeDto>> res = null;
        List<CompteCharge> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        List<CompteChargeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a compteCharge by id")
    @GetMapping("id/{id}")
    public ResponseEntity<CompteChargeDto> findById(@PathVariable Long id) {
        CompteCharge t = service.findById(id);
        if (t != null) {
            converter.init(true);
            CompteChargeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a compteCharge by label")
    @GetMapping("label/{label}")
    public ResponseEntity<CompteChargeDto> findByLabel(@PathVariable String label) {
	    CompteCharge t = service.findByReferenceEntity(new CompteCharge(label));
        if (t != null) {
            converter.init(true);
            CompteChargeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  compteCharge")
    @PostMapping("")
    public ResponseEntity<CompteChargeDto> save(@RequestBody CompteChargeDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            CompteCharge myT = converter.toItem(dto);
            CompteCharge t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                CompteChargeDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  compteCharge")
    @PutMapping("")
    public ResponseEntity<CompteChargeDto> update(@RequestBody CompteChargeDto dto) throws Exception {
        ResponseEntity<CompteChargeDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            CompteCharge t = service.findById(dto.getId());
            converter.copy(dto,t);
            CompteCharge updated = service.update(t);
            CompteChargeDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of compteCharge")
    @PostMapping("multiple")
    public ResponseEntity<List<CompteChargeDto>> delete(@RequestBody List<CompteChargeDto> dtos) throws Exception {
        ResponseEntity<List<CompteChargeDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<CompteCharge> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified compteCharge")
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


    @Operation(summary = "Finds a compteCharge and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<CompteChargeDto> findWithAssociatedLists(@PathVariable Long id) {
        CompteCharge loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        CompteChargeDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds compteCharges by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<CompteChargeDto>> findByCriteria(@RequestBody CompteChargeCriteria criteria) throws Exception {
        ResponseEntity<List<CompteChargeDto>> res = null;
        List<CompteCharge> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.setCharges(true);
        List<CompteChargeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated compteCharges by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody CompteChargeCriteria criteria) throws Exception {
        List<CompteCharge> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initList(true);
        List<CompteChargeDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets compteCharge data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody CompteChargeCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<CompteChargeDto> findDtos(List<CompteCharge> list){
        converter.initList(false);
        List<CompteChargeDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<CompteChargeDto> getDtoResponseEntity(CompteChargeDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public CompteChargeRestAdmin(CompteChargeAdminService service, CompteChargeConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final CompteChargeAdminService service;
    private final CompteChargeConverter converter;





}
