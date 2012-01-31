package com.bmv.auditoria.ai.util;

/**
 * Define the navigation URL's to be followed by the application.
 * @author acruzh
 */
public enum Navigation {
    
    LOGIN_FAILED("/faces/loginfailed.xhtml"),
    MENU("/faces/menu.xhtml"),
    INDEX("/faces/index.xhtml");
    
    private final String url;
    
    private Navigation(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    
}
