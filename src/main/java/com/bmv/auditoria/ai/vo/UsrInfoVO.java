package com.bmv.auditoria.ai.vo;

/**
 *
 * @author acruz
 */
public class UsrInfoVO {

    private String userName;
    private String role;
    private String displayName;
    private boolean auditor;
    private boolean local;
    //private int idPerfil;

    public UsrInfoVO(String userName, String role, String displayName, 
            boolean auditor, boolean local) {
        this.userName = userName;
        this.role = role;
        this.displayName = displayName;
        this.auditor = auditor;
        this.local = local;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isAuditor() {
        return auditor;
    }

    public void setAuditor(boolean auditor) {
        this.auditor = auditor;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }    

}
