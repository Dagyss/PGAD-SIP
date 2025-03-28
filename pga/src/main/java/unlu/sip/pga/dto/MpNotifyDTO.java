package unlu.sip.pga.dto;

import java.time.OffsetDateTime;

public class MpNotifyDTO {
    
    private Long id;
    private Boolean liveMode;
    private String type;
    private OffsetDateTime dateCreated;
    private Long userID;
    private String apiVersion;
    private String action;
    private DataMpDTO data;

    public MpNotifyDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getLiveMode() {
        return liveMode;
    }

    public void setLiveMode(Boolean liveMode) {
        this.liveMode = liveMode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public DataMpDTO getData() {
        return data;
    }

    public void setData(DataMpDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MpNotifyDTO [id=" + id + ", liveMode=" + liveMode + ", type=" + type + ", dateCreated=" + dateCreated
                + ", userID=" + userID + ", apiVersion=" + apiVersion + ", action=" + action + ", data="
                + data.toString() + "]";
    }
}
