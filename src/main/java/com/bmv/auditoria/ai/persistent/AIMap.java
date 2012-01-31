package com.bmv.auditoria.ai.persistent;

import com.bmv.auditoria.ai.persistent.auto._AIMap;

public class AIMap extends _AIMap {

    private static AIMap instance;

    private AIMap() {}

    public static AIMap getInstance() {
        if(instance == null) {
            instance = new AIMap();
        }

        return instance;
    }
}
