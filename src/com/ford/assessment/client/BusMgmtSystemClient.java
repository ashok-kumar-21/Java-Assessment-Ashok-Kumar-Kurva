package com.ford.assessment.client;

import com.ford.assessment.dbmanager.BusMgmtDbManager;

public class BusMgmtSystemClient {
    public static void main(String[] args) {
        BusMgmtDbManager busMgmtDbManager = new BusMgmtDbManager();
        busMgmtDbManager.displayMainMenu();
    }
}
