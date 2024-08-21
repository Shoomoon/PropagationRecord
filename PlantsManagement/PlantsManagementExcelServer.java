package PlantsManagement;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class PlantsManagementExcelServer implements Serializable {
    private final File dataFile;
    // private final PlantRecords plantRecords;
    static String DATAPATH = "";
    public PlantsManagementExcelServer() throws IOException {
        this.dataFile = new File(DATAPATH);;
        if (!dataFile.exists()) {
             this.init();
        } else {
            // plantRecords = new PlantsManagementExcelServer();
        }
    }
    public void init() {
        this.save();
    }

    private void save() {
    }

}
