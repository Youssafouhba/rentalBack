package ma.zyn.app.dao.criteria.core.locataire;


import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.time.LocalDateTime;
import java.util.List;

public class ReglementCriteria extends  BaseCriteria  {

    private String montant;
    private String montantMin;
    private String montantMax;
    private LocalDateTime date;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String motif;
    private String motifLike;

    private LocationCriteria location ;
    private List<LocationCriteria> locations ;


    public String getMontant(){
        return this.montant;
    }
    public void setMontant(String montant){
        this.montant = montant;
    }   
    public String getMontantMin(){
        return this.montantMin;
    }
    public void setMontantMin(String montantMin){
        this.montantMin = montantMin;
    }
    public String getMontantMax(){
        return this.montantMax;
    }
    public void setMontantMax(String montantMax){
        this.montantMax = montantMax;
    }
      
    public LocalDateTime getDate(){
        return this.date;
    }
    public void setDate(LocalDateTime date){
        this.date = date;
    }
    public LocalDateTime getDateFrom(){
        return this.dateFrom;
    }
    public void setDateFrom(LocalDateTime dateFrom){
        this.dateFrom = dateFrom;
    }
    public LocalDateTime getDateTo(){
        return this.dateTo;
    }
    public void setDateTo(LocalDateTime dateTo){
        this.dateTo = dateTo;
    }
    public String getMotif(){
        return this.motif;
    }
    public void setMotif(String motif){
        this.motif = motif;
    }
    public String getMotifLike(){
        return this.motifLike;
    }
    public void setMotifLike(String motifLike){
        this.motifLike = motifLike;
    }


    public LocationCriteria getLocation(){
        return this.location;
    }

    public void setLocation(LocationCriteria location){
        this.location = location;
    }
    public List<LocationCriteria> getLocations(){
        return this.locations;
    }

    public void setLocations(List<LocationCriteria> locations){
        this.locations = locations;
    }
}
