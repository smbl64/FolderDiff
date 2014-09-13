/*
 * Created by JFormDesigner on Wed Sep 10 10:38:25 IRDT 2014
 */

package com.banisaeid.folderdiff.ui;

import javax.swing.border.*;

import com.banisaeid.folderdiff.*;
import com.banisaeid.folderdiff.model.FileInfo;
import com.banisaeid.folderdiff.model.FileType;
import com.banisaeid.folderdiff.model.Snapshot;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame implements Logger{
    private final SimpleDateFormat logDateFormat = new SimpleDateFormat("[HH:mm:ss] ");
    private Controller controller  = new Controller(this);

    public MainFrame() {
        initComponents();
        setTitle("Folder Diff v" + CommonProps.VERSION);
    }

    private void browseInputFolderButtonActionPerformed(ActionEvent e) {
        String dir = selectFolder();
        if (dir == null) {
            return;
        }
        inputFolderTextField.setText(dir);
    }

    private String selectFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogTitle("Select source folder");

        if (fileChooser.showDialog(this, "Select Folder") != JFileChooser.APPROVE_OPTION){
            return null;
        }

        return fileChooser.getSelectedFile().getAbsolutePath();
    }

    private void browseOutputButtonActionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Snapshot (*.snapshot)", "snapshot"));
        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION){
            return;
        }

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".snapshot")){
            filePath += ".snapshot";
        }

        snapshotOutputTextField.setText(filePath);
    }

    private void showInputError(Component componentWithError, String message, String title){
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
        if (componentWithError != null){
            componentWithError.requestFocus();
        }
    }

    private void generateSnapshotButtonActionPerformed(ActionEvent e) {
        if (inputFolderTextField.getText().equals("")){
            showInputError(inputFolderTextField, "Please select input folder to take snapshot.", "Error");
            return;
        }

        if (snapshotOutputTextField.getText().equals("")){
            showInputError(snapshotOutputTextField, "Please select where to save the generated snapshot.", "Error");
            return;
        }

        controller.takeSnapshot(inputFolderTextField.getText(), snapshotOutputTextField.getText());
    }

    private void browsePatchFolderButtonActionPerformed(ActionEvent e) {
        String dir = selectFolder();
        if (dir == null) {
            return;
        }

        patchFolderTextField.setText(dir);
    }

    private void browseSourceFolderButtonActionPerformed(ActionEvent e) {
        String dir = selectFolder();
        if (dir == null) {
            return;
        }

        sourceFolderTextField.setText(dir);
    }

    private void browseSnapshotInputButtonActionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Snapshot (*.snapshot)", "snapshot"));
        fileChooser.setSelectedFile(new File(snapshotInputTextField.getText()));
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION){
            return;
        }

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".snapshot")){
            filePath += ".snapshot";
        }


        snapshotInputTextField.setText(filePath);
    }

    private void generatePatchButtonActionPerformed(ActionEvent e) {
        if (snapshotInputTextField.getText().equals("")){
            showInputError(snapshotInputTextField, "Please select the snapshot file.", "Error");
            return;
        }

        if (sourceFolderTextField.getText().equals("")){
            showInputError(sourceFolderTextField, "Please select source folder for comparison with snapshot.", "Error");
            return;
        }

        if (patchFolderTextField.getText().equals("")){
            showInputError(patchFolderTextField, "Please select patch folder to generate the patch.", "Error");
            return;
        }

        controller.generatePatch(snapshotInputTextField.getText(),
                sourceFolderTextField.getText(),
                patchFolderTextField.getText());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        inputFolderTextField = new JTextField();
        browseInputFolderButton = new JButton();
        label1 = new JLabel();
        label2 = new JLabel();
        snapshotOutputTextField = new JTextField();
        browseOutputButton = new JButton();
        generateSnapshotButton = new JButton();
        panel2 = new JPanel();
        label3 = new JLabel();
        snapshotInputTextField = new JTextField();
        browseSnapshotInputButton = new JButton();
        label4 = new JLabel();
        sourceFolderTextField = new JTextField();
        browseSourceFolderButton = new JButton();
        label5 = new JLabel();
        patchFolderTextField = new JTextField();
        browsePatchFolderButton = new JButton();
        generatePatchButton = new JButton();
        panel3 = new JPanel();
        scrollPane1 = new JScrollPane();
        logTextArea = new JTextArea();

        //======== this ========
        setTitle("Folder Diff");
        Container contentPane = getContentPane();

        //======== tabbedPane1 ========
        {

            //======== panel1 ========
            {

                //---- browseInputFolderButton ----
                browseInputFolderButton.setText("...");
                browseInputFolderButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        browseInputFolderButtonActionPerformed(e);
                    }
                });

                //---- label1 ----
                label1.setText("Folder:");

                //---- label2 ----
                label2.setText("Output:");

                //---- browseOutputButton ----
                browseOutputButton.setText("...");
                browseOutputButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        browseOutputButtonActionPerformed(e);
                    }
                });

                //---- generateSnapshotButton ----
                generateSnapshotButton.setText("Generate");
                generateSnapshotButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        generateSnapshotButtonActionPerformed(e);
                    }
                });

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel1Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 260, Short.MAX_VALUE)
                                    .addComponent(generateSnapshotButton))
                                .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                    .addComponent(label2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(snapshotOutputTextField, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE))
                                .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(label1)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(inputFolderTextField, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel1Layout.createParallelGroup()
                                .addComponent(browseInputFolderButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addComponent(browseOutputButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(85, Short.MAX_VALUE))
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1)
                                .addComponent(browseInputFolderButton)
                                .addComponent(inputFolderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(5, 5, 5)
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(snapshotOutputTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(browseOutputButton))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(generateSnapshotButton)
                            .addGap(60, 60, 60))
                );
            }
            tabbedPane1.addTab("Snapshot", panel1);

            //======== panel2 ========
            {

                //---- label3 ----
                label3.setText("Snapshot:");

                //---- browseSnapshotInputButton ----
                browseSnapshotInputButton.setText("...");
                browseSnapshotInputButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        browseSnapshotInputButtonActionPerformed(e);
                    }
                });

                //---- label4 ----
                label4.setText("Source Folder:");

                //---- browseSourceFolderButton ----
                browseSourceFolderButton.setText("...");
                browseSourceFolderButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        browseSourceFolderButtonActionPerformed(e);
                    }
                });

                //---- label5 ----
                label5.setText("Patch Folder:");

                //---- browsePatchFolderButton ----
                browsePatchFolderButton.setText("...");
                browsePatchFolderButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        browsePatchFolderButtonActionPerformed(e);
                    }
                });

                //---- generatePatchButton ----
                generatePatchButton.setText("Generate Patch");
                generatePatchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        generatePatchButtonActionPerformed(e);
                    }
                });

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(panel2Layout.createParallelGroup()
                                .addComponent(label5, GroupLayout.Alignment.TRAILING)
                                .addComponent(label4, GroupLayout.Alignment.TRAILING)
                                .addComponent(label3, GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel2Layout.createParallelGroup()
                                .addComponent(generatePatchButton, GroupLayout.Alignment.TRAILING)
                                .addComponent(patchFolderTextField, GroupLayout.Alignment.TRAILING)
                                .addComponent(sourceFolderTextField, GroupLayout.Alignment.TRAILING)
                                .addComponent(snapshotInputTextField, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel2Layout.createParallelGroup()
                                .addComponent(browseSnapshotInputButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addComponent(browseSourceFolderButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addComponent(browsePatchFolderButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                            .addGap(53, 53, 53))
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(snapshotInputTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label3)
                                .addComponent(browseSnapshotInputButton))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(sourceFolderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label4)
                                .addComponent(browseSourceFolderButton))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label5)
                                .addComponent(patchFolderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(browsePatchFolderButton))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(generatePatchButton)
                            .addGap(30, 30, 30))
                );
            }
            tabbedPane1.addTab("Patch", panel2);
        }

        //======== panel3 ========
        {
            panel3.setBorder(new TitledBorder("Log"));

            //======== scrollPane1 ========
            {

                //---- logTextArea ----
                logTextArea.setEditable(false);
                logTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
                scrollPane1.setViewportView(logTextArea);
            }

            GroupLayout panel3Layout = new GroupLayout(panel3);
            panel3.setLayout(panel3Layout);
            panel3Layout.setHorizontalGroup(
                panel3Layout.createParallelGroup()
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPane1)
                        .addContainerGap())
            );
            panel3Layout.setVerticalGroup(
                panel3Layout.createParallelGroup()
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                        .addContainerGap())
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(tabbedPane1)
                        .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(14, 14, 14))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabbedPane1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(10, 10, 10))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    @Override
    public void log(String format, Object... args){
        String date = logDateFormat.format(new Date());
        String msg = String.format(format, args);
        logTextArea.append(date + msg + "\n");
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextField inputFolderTextField;
    private JButton browseInputFolderButton;
    private JLabel label1;
    private JLabel label2;
    private JTextField snapshotOutputTextField;
    private JButton browseOutputButton;
    private JButton generateSnapshotButton;
    private JPanel panel2;
    private JLabel label3;
    private JTextField snapshotInputTextField;
    private JButton browseSnapshotInputButton;
    private JLabel label4;
    private JTextField sourceFolderTextField;
    private JButton browseSourceFolderButton;
    private JLabel label5;
    private JTextField patchFolderTextField;
    private JButton browsePatchFolderButton;
    private JButton generatePatchButton;
    private JPanel panel3;
    private JScrollPane scrollPane1;
    private JTextArea logTextArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
