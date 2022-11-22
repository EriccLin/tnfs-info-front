package com.tnfs.infoApplication.util;

import javafx.scene.layout.VBox;
import lombok.*;

public enum Menu {
    home("首頁", "F0Home"),
    caseCreate("新增案件", null),
    caseCreateTypeA("新增一般案件", "F11CaseCreateTypeA"),
    caseCreateTypeB("新增一般案件", "F11CaseCreateTypeA"),
    caseManagement("案件管理", "CaseManagement"),
    caseSearch("案件搜尋", "CaseSearch"),
    evidenceManagement("證物管理", "EvidenceManagement"),
    caseAudit("案件審核", null),
    memberManagement("人員管理", "memberManagement");

    private String title;
    private String fxml;

    Menu(String title, String fxmlName) {
        this.title = title;
        this.fxml = fxmlName;
    }

    Menu(String title) {
        this.title = title;
        this.fxml = null;
    }

    public String getTitle() {
        return title;
    }

    public String getFxml() {
        return fxml == null ? null : fxml+".fxml";
    }
}
