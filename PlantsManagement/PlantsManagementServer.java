package PlantsManagement;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PlantsManagementServer{
    Map<Integer, Plant> newPlants;
    Map<Integer, Plant> modifyingPlants;
    Map<Integer, Plant> plantsShowing;
    public PlantsManagementServer(PlantsData plantsData) {
        newPlants = new HashMap<>();
        modifyingPlants = new HashMap<>();
        plantsShowing = new HashMap<>();
    }
    public boolean duplicateItem(int id) {
        return true;
    }
    public boolean addNewItem() {
        return true;
    }
    public boolean removeItem(int id) {
        return true;
    }
    private int getNewId() {
        return 0;
    }

    public boolean notSaved() {
        return true;
    }

    public void save() {
    }
}
