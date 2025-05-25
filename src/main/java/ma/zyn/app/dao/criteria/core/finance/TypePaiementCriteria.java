package ma.zyn.app.dao.criteria.core.finance;



import ma.zyn.app.zynerator.criteria.BaseCriteria;

public class TypePaiementCriteria extends  BaseCriteria  {

    private String indexation;
    private String indexationMin;
    private String indexationMax;
    private String code;
    private String codeLike;
    private String label;
    private String labelLike;
    private String style;
    private String styleLike;



    public String getIndexation(){
        return this.indexation;
    }
    public void setIndexation(String indexation){
        this.indexation = indexation;
    }   
    public String getIndexationMin(){
        return this.indexationMin;
    }
    public void setIndexationMin(String indexationMin){
        this.indexationMin = indexationMin;
    }
    public String getIndexationMax(){
        return this.indexationMax;
    }
    public void setIndexationMax(String indexationMax){
        this.indexationMax = indexationMax;
    }
      
    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCodeLike(){
        return this.codeLike;
    }
    public void setCodeLike(String codeLike){
        this.codeLike = codeLike;
    }

    public String getLabel(){
        return this.label;
    }
    public void setLabel(String label){
        this.label = label;
    }
    public String getLabelLike(){
        return this.labelLike;
    }
    public void setLabelLike(String labelLike){
        this.labelLike = labelLike;
    }

    public String getStyle(){
        return this.style;
    }
    public void setStyle(String style){
        this.style = style;
    }
    public String getStyleLike(){
        return this.styleLike;
    }
    public void setStyleLike(String styleLike){
        this.styleLike = styleLike;
    }


}
