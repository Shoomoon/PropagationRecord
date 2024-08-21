package PlantsManagement;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.SQLException;

public class Client extends JFrame implements ActionListener {
    private static final File BLOBS = new File("/Users/xiaomingqiu/Documents/Gardening/PlantsManagement/savedProjects");
    private static final File LATEST_PROJECT = new File(BLOBS, "latestProject.txt");
    private final JFrame clientWindow;
    private JPanel importPanel;
    private JPanel editPanel;
    private JPanel showPanel;
    private JMenuBar menuBar;
    private PlantsManagementServer plantsManagementServer;
    public Client(){
        clientWindow = new JFrame();
    }
    public void run() throws SQLException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        // init panels
        initImportPanel();
        initEditPanel();
        initShowPanel();
        initMenu();
        // init client
        clientWindow.setTitle("PlantsManagement");
        clientWindow.setBounds(300, 50, 400, 400);
        clientWindow.setLayout(new GridLayout(3, 1));
        clientWindow.add(importPanel, 0, 0);
        clientWindow.add(editPanel,0, 1);
        clientWindow.add(showPanel,0, 2);
        clientWindow.setVisible(true);
        clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // import data
        initProject();
    }

    private void initProject() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (LATEST_PROJECT.exists()) {
            loadLatestProject();
        } else {
            importFile();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Import": {
                try {
                    importFile();
                } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (InstantiationException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
            case "NewPlant": {
                break;
            }
            case "SaveAllPlants": {
                break;
            }
        }
    }

    private void importFile() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (plantsManagementServer != null && plantsManagementServer.notSaved()) {
            int userSaveOption = JOptionPane.showConfirmDialog(importPanel, "Data not saved! Do you want to save them?", "Warning!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
            if (userSaveOption == JOptionPane.OK_OPTION) {
                plantsManagementServer.save();
                JOptionPane.showMessageDialog(importPanel, "Data saved!");
            } else {
                JOptionPane.showMessageDialog(importPanel, "Data NOT saved!");
            }
        }

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser chooseSavedProjects = new JFileChooser(BLOBS);
        chooseSavedProjects.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Plant management project file (*.txt)", "txt");
        chooseSavedProjects.setFileFilter(filter);
        chooseSavedProjects.setDialogTitle("Choose the plant management project that you want to load:");
        boolean fileChoosed = false;
        if (chooseSavedProjects.showOpenDialog(importPanel) == JFileChooser.APPROVE_OPTION) {
            writeContents(LATEST_PROJECT, chooseSavedProjects.getSelectedFile());
            fileChoosed = true;
        }

        if (!LATEST_PROJECT.exists()) {
            JOptionPane.showMessageDialog(importPanel, "Please import project first!");
            return;
        } else if (!fileChoosed) {
            JOptionPane.showMessageDialog(importPanel, "No project selected!");
            return;
        }
        loadLatestProject();
    }

    private void writeContents(File target, Object... contents) {
        try {
            if (target.isDirectory()) {
                throw new IllegalArgumentException("cannot overwrite directory");
            }
            BufferedOutputStream str =
                    new BufferedOutputStream(Files.newOutputStream(target.toPath()));
            for (Object obj : contents) {
                if (obj instanceof byte[]) {
                    str.write((byte[]) obj);
                } else {
                    str.write(((String) obj).getBytes(StandardCharsets.UTF_8));
                }
            }
            str.close();
        } catch (IOException | ClassCastException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }
    }

    private <T extends Serializable> T readObject(File file, Class<T> expectedClass) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            T result = expectedClass.cast(in.readObject());
            in.close();
            return result;
        } catch (IOException | ClassCastException
                 | ClassNotFoundException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }
    }

    private void initImportPanel() {
        importPanel = new JPanel();
        JButton importButton = new JButton("Import project");
        importButton.setActionCommand("Import");
        //importButton.setBackground(Color.GREEN);
        importPanel.add(importButton, BorderLayout.CENTER);
        importButton.addActionListener(this);
    }
    private void initEditPanel() {
        editPanel = new JPanel();
        TitledBorder editPanelBorder = BorderFactory.createTitledBorder("Edit");
        editPanel.setBorder(editPanelBorder);
        // add new plant with no information
        JButton newItem = new JButton("Add new Plant");
        newItem.setActionCommand("NewPlant");
        editPanel.add(newItem);
        newItem.addActionListener(this);
        // save all plants in the edit panel
        JButton saveItems = new JButton("Save All");
        saveItems.setActionCommand("SaveAllPlants");
        editPanel.add(saveItems);
        saveItems.addActionListener(this);

    }
    private void initShowPanel() {
        showPanel = new JPanel();
        TitledBorder border = BorderFactory.createTitledBorder("Show");
        showPanel.setBorder(border);
        // filter out the specified plants to show
        JButton filterButton = new JButton("Filter");
        filterButton.setActionCommand("Filter");
        showPanel.add(filterButton);
        filterButton.addActionListener(this);
        // sort the plants on the show panel
        JButton sortButton = new JButton("Sort");
        sortButton.setActionCommand("Sort");
        showPanel.add(sortButton);
        sortButton.addActionListener(this);

    }
    private void initMenu() {

    }

    private void loadLatestProject() {
        PlantsData plantsData = readObject(LATEST_PROJECT, PlantsData.class);
        plantsManagementServer = new PlantsManagementServer(plantsData);
    }

    private void showSpecifiedPanel(Panel showPanel) {
        clientWindow.setContentPane(showPanel);
        clientWindow.revalidate();
    }

}
