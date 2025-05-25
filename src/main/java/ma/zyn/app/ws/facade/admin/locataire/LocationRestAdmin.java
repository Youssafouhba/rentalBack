package ma.zyn.app.ws.facade.admin.locataire;

import io.swagger.v3.oas.annotations.Operation;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.dao.criteria.core.locataire.LocationCriteria;
import ma.zyn.app.service.facade.admin.locataire.LocationAdminService;
import ma.zyn.app.ws.converter.locataire.LocationConverter;
import ma.zyn.app.ws.dto.locataire.LocationDto;
import ma.zyn.app.zynerator.util.PaginatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/location/")
public class LocationRestAdmin {




    @Operation(summary = "Finds a list of all locations")
    @GetMapping("")
    public ResponseEntity<List<LocationDto>> findAll() throws Exception {
        ResponseEntity<List<LocationDto>> res = null;
        List<Location> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<LocationDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all locations")
    @GetMapping("optimized")
    public ResponseEntity<List<LocationDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<LocationDto>> res = null;
        List<Location> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<LocationDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a location by id")
    @GetMapping("id/{id}")
    public ResponseEntity<LocationDto> findById(@PathVariable Long id) {
        Location t = service.findById(id);
        if (t != null) {
            converter.init(true);
            LocationDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a location by code")
    @GetMapping("code/{code}")
    public ResponseEntity<LocationDto> findByCode(@PathVariable String code) {
	    Location t = service.findByReferenceEntity(new Location(code));
        if (t != null) {
            converter.init(true);
            LocationDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  location")
    @PostMapping("")
    public ResponseEntity<LocationDto> save(@RequestBody LocationDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Location myT = converter.toItem(dto);
            Location t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                LocationDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  location")
    @PutMapping("")
    public ResponseEntity<LocationDto> update(@RequestBody LocationDto dto) throws Exception {
        ResponseEntity<LocationDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Location t = service.findById(dto.getId());
            converter.copy(dto,t);
            Location updated = service.update(t);
            LocationDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of location")
    @PostMapping("multiple")
    public ResponseEntity<List<LocationDto>> delete(@RequestBody List<LocationDto> dtos) throws Exception {
        ResponseEntity<List<LocationDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Location> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified location")
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

    @Operation(summary = "find by locataire id")
    @GetMapping("locataire/id/{id}")
    public List<LocationDto> findByLocataireId(@PathVariable Long id){
        return findDtos(service.findByLocataireId(id));
    }
    @Operation(summary = "delete by locataire id")
    @DeleteMapping("locataire/id/{id}")
    public int deleteByLocataireId(@PathVariable Long id){
        return service.deleteByLocataireId(id);
    }
    @Operation(summary = "find by local id")
    @GetMapping("local/id/{id}")
    public List<LocationDto> findByLocalId(@PathVariable Long id){
        return findDtos(service.findByLocalId(id));
    }
    @Operation(summary = "delete by local id")
    @DeleteMapping("local/id/{id}")
    public int deleteByLocalId(@PathVariable Long id){
        return service.deleteByLocalId(id);
    }

    @Operation(summary = "Finds a location and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<LocationDto> findWithAssociatedLists(@PathVariable Long id) {
        Location loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        LocationDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds locations by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<LocationDto>> findByCriteria(@RequestBody LocationCriteria criteria) throws Exception {
        ResponseEntity<List<LocationDto>> res = null;
        List<Location> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<LocationDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated locations by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody LocationCriteria criteria) throws Exception {
        List<Location> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<LocationDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets location data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody LocationCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<LocationDto> findDtos(List<Location> list){
        converter.initObject(true);
        List<LocationDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<LocationDto> getDtoResponseEntity(LocationDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public LocationRestAdmin(LocationAdminService service, LocationConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final LocationAdminService service;
    private final LocationConverter converter;





}
