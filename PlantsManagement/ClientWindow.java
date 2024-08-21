package PlantsManagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientWindow {
    private JPanel root;
    private JPanel edit;
    private JButton addNewPlantButton;
    private JButton saveAllPlantsButton;
    private JButton filterButton;
    private JButton sortButton;
    private JPanel editButtons;
    private JPanel editing;
    private JPanel show;
    private JPanel showButtons;
    private JPanel showing;
    private JScrollBar editingScrollBar;
    private JScrollBar showingScrollBar;
    private JList editingList;
    private JList showingList;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItemImport;

    public ClientWindow() {
        menu = new JMenu("Menu");
        menuItemImport = new JMenuItem("Import");
        menuItemImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        menu.add(menuItemImport);
        menuBar.add(menu);

        addNewPlantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        saveAllPlantsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientWindow");
        ClientWindow cw = new ClientWindow();
        frame.setContentPane(cw.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setJMenuBar(cw.menuBar);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
