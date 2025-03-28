package unlu.sip.pga.dto;

public class DataMpDTO {

    private String id;

    public DataMpDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DataMp [id=" + id + "]";
    }
}
