
package gui;

import javax.swing.table.DefaultTableModel;
public class EditProblem extends javax.swing.JFrame {
    private String studentId;
    private java.util.Map<String, Integer> buildingIds = new java.util.HashMap<>();
private java.util.Map<String, Integer> roomTypeIds = new java.util.HashMap<>();
private java.util.Map<String, Integer> problemTypeIds = new java.util.HashMap<>();

private EntityDAO entityDAO = new EntityDAO();

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(EditProblem.class.getName());

    public EditProblem() {
    initComponents();
    loadDropdowns();
    setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
}

  public EditProblem(String studentId) {
    initComponents();
    this.studentId = studentId;

    if (this.studentId != null) {
        this.studentId = this.studentId.trim();
    }

    loadDropdowns();
    loadProblems();

    setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
}

private void loadProblems() {

    DefaultTableModel model =
            (DefaultTableModel) problemTable.getModel();

    model.setRowCount(0);

    if (studentId == null || studentId.trim().isEmpty()) {
        System.out.println("ERROR: Student ID was not passed to EditProblem.");
        return;
    }

    String currentStudentId = studentId.trim();

    java.util.ArrayList<Data.ProblemData> problems =
            ProblemDAO.getAllProblems();

    int number = 1;

    if (problems == null) {
        System.out.println("ERROR: ProblemDAO returned null.");
        return;
    }

    for (Data.ProblemData p : problems) {

        if (p == null || p.studentId == null || p.status == null) {
            continue;
        }

        boolean belongsToStudent =
                currentStudentId.equalsIgnoreCase(p.studentId.trim());

        boolean isPending =
                p.status.trim().equalsIgnoreCase("Pending");

        if (belongsToStudent && isPending) {
            model.addRow(new Object[]{
                number++,
                p.id,
                p.problem,
                p.building,
                p.room,
                p.status
            });
        }
    }

    problemTable.revalidate();
    problemTable.repaint();

    System.out.println("Student ID: " + currentStudentId);
    System.out.println("Pending problems displayed: " + model.getRowCount());
}



private void loadDropdowns() {

    buildingIds.clear();
    roomTypeIds.clear();
    problemTypeIds.clear();

    // Clear the default items
    buildingComboBox.removeAllItems();
    roomTypeComboBox.removeAllItems();
    problemComboBox.removeAllItems();

    // Load buildings from the database
    for (entity.Building b : entityDAO.getAllBuildings()) {
        String name = b.getBuildingName();

        buildingComboBox.addItem(name);
        buildingIds.put(name, b.getBuildingId());
    }

    // Load room types from the database
    for (entity.RoomType r : entityDAO.getAllRoomTypes()) {
        String name = r.getRoomTypeName();

        roomTypeComboBox.addItem(name);
        roomTypeIds.put(name, r.getRoomTypeId());
    }

    // Load problem types based on the selected room type
    updateProblemChoices();

    // Hide the Other Problem field initially
    updateOtherProblemField();
}


    private void updateProblemChoices() {
    Object selected = roomTypeComboBox.getSelectedItem();

    problemComboBox.removeAllItems();
    problemTypeIds.clear();

    if (selected == null) {
        return;
    }

    String roomType = selected.toString().trim();

  java.util.List<entity.ProblemType> allTypes =
        entityDAO.getAllProblemTypes();

    for (entity.ProblemType p : allTypes) {
        String name = p.getProblemTypeName().trim();
        boolean allowed = false;

        if (roomType.equalsIgnoreCase("Classroom")) {
            allowed = name.equalsIgnoreCase("TV")
                    || name.equalsIgnoreCase("TABLE")
                    || name.equalsIgnoreCase("CHAIR")
                    || name.equalsIgnoreCase("BOARD")
                    || name.equalsIgnoreCase("WHITEBOARD")
                    || name.equalsIgnoreCase("OTHERS");

        } else if (roomType.equalsIgnoreCase("Computer Laboratory")) {
            allowed = name.equalsIgnoreCase("PC")
                    || name.equalsIgnoreCase("TABLE")
                    || name.equalsIgnoreCase("CHAIR")
                    || name.equalsIgnoreCase("WHITEBOARD")
                    || name.equalsIgnoreCase("TV")
                    || name.equalsIgnoreCase("OTHERS");

        } else if (roomType.equalsIgnoreCase("Comfort Room")) {
            allowed = name.equalsIgnoreCase("TOILET")
                    || name.equalsIgnoreCase("FAUCET")
                    || name.equalsIgnoreCase("SINK")
                    || name.equalsIgnoreCase("URINAL")
                    || name.equalsIgnoreCase("DOOR")
                    || name.equalsIgnoreCase("LIGHT")
                    || name.equalsIgnoreCase("OTHERS");
        }

        if (allowed) {
            problemComboBox.addItem(name);
            problemTypeIds.put(
                    name.toUpperCase(),
                    p.getProblemTypeId()
            );
        }
    }

    updateOtherProblemField();
}

private void updateOtherProblemField() {
    Object selected = problemComboBox.getSelectedItem();
    boolean isOthers = selected != null
            && "OTHERS".equalsIgnoreCase(selected.toString());

    otherProblemField.setVisible(isOthers);

    if (!isOthers) {
        otherProblemField.setText("");
    }
}

private String findNameById(
        java.util.Map<String, Integer> map, int id) {

    for (java.util.Map.Entry<String, Integer> entry
            : map.entrySet()) {

        if (entry.getValue() == id) {
            return entry.getKey();
        }
    }

    return null;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        roomField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        buildingComboBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        otherProblemField = new javax.swing.JTextField();
        problemComboBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        phoneNumberField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lastNameField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        firstNameField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        roomTypeComboBox = new javax.swing.JComboBox<>();
        updateButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        locationDescriptionField = new javax.swing.JTextArea();
        problemIdValueLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        problemTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));

        backButton.setBackground(new java.awt.Color(255, 0, 0));
        backButton.setForeground(new java.awt.Color(255, 255, 255));
        backButton.setText("BACK");
        backButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        backButton.addActionListener(this::backButtonActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ONLY PENDING PROBLEMS CAN BE EDITED");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("EDIT PROBLEM ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(152, 152, 152)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel2.setForeground(new java.awt.Color(102, 102, 102));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("EXACTLOCATION & DESCRIPTION:");

        roomField.setForeground(new java.awt.Color(102, 102, 102));
        roomField.setText("e.g Room 403/ Fourth floor ");
        roomField.addActionListener(this::roomFieldActionPerformed);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("ROOM/Floor");

        buildingComboBox.setForeground(new java.awt.Color(102, 102, 102));
        buildingComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PH BUILDING", "MS BUILDING", "MW BUILDING", "MN BUILDING" }));
        buildingComboBox.addActionListener(this::buildingComboBoxActionPerformed);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Building");

        otherProblemField.setText("Enter other problem");
        otherProblemField.addActionListener(this::otherProblemFieldActionPerformed);

        problemComboBox.setForeground(new java.awt.Color(102, 102, 102));
        problemComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PC", "TABLE", "CHAIR", "BOARD", "WHITEBOARD", "OTHERS" }));
        problemComboBox.addActionListener(this::problemComboBoxActionPerformed);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Problem");

        phoneNumberField.setForeground(new java.awt.Color(102, 102, 102));
        phoneNumberField.setText("09XXXXXXXXX");

        jLabel11.setBackground(new java.awt.Color(102, 102, 102));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Phone Number");

        lastNameField.setForeground(new java.awt.Color(102, 102, 102));
        lastNameField.setText("Enter last name");
        lastNameField.addActionListener(this::lastNameFieldActionPerformed);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Last Name");

        firstNameField.setForeground(new java.awt.Color(102, 102, 102));
        firstNameField.setText("Enter first name");
        firstNameField.addActionListener(this::firstNameFieldActionPerformed);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("First Name");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("PROBLEM ID: ");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel1.setText("SELECT PROBLEM TO EDIT");

        roomTypeComboBox.setForeground(new java.awt.Color(102, 102, 102));
        roomTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        roomTypeComboBox.addActionListener(this::roomTypeComboBoxActionPerformed);

        updateButton.setBackground(new java.awt.Color(51, 255, 51));
        updateButton.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        updateButton.setText("UPDATE");
        updateButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        updateButton.addActionListener(this::updateButtonActionPerformed);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Room Type");

        locationDescriptionField.setColumns(20);
        locationDescriptionField.setForeground(new java.awt.Color(102, 102, 102));
        locationDescriptionField.setRows(5);
        locationDescriptionField.setText("Describe the exact location and what is wrong.");
        jScrollPane2.setViewportView(locationDescriptionField);

        problemIdValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        problemIdValueLabel.setText("id");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(phoneNumberField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(otherProblemField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buildingComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roomField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(updateButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(roomTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(problemComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel11)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(178, 178, 178)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(problemIdValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(problemIdValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phoneNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(problemComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(otherProblemField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buildingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(7, 7, 7)
                .addComponent(roomField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130))
        );

        problemTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        problemTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        problemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Problem ID", "PROBLEM", "BUILDING", "ROOM", "status"
            }
        ));
        problemTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                problemTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                problemTableMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(problemTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 862, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(290, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buildingComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildingComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buildingComboBoxActionPerformed

    private void roomFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roomFieldActionPerformed

    private void problemTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_problemTableMouseClicked
      int row = problemTable.getSelectedRow();

    if (row == -1) {
        return;
    }

    try {
        int selectedProblemId = Integer.parseInt(
                problemTable.getValueAt(row, 1).toString()
        );

        Data.ProblemData p =
                ProblemDAO.getProblemById(selectedProblemId);

        // Check if the problem exists
        if (p == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Problem not found."
            );
            return;
        }

        // Check ownership and status
        if (p.status == null
                || !p.status.trim().equalsIgnoreCase("Pending")
                || studentId == null
                || p.studentId == null
              || !studentId.trim().equalsIgnoreCase(p.studentId.trim())) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Only your own Pending problems can be edited."
            );
            return;
        }

        // Display Problem ID
        problemIdValueLabel.setText(String.valueOf(p.id));

        // Display student information
        firstNameField.setText(
                p.firstName == null ? "" : p.firstName
        );

        lastNameField.setText(
                p.lastName == null ? "" : p.lastName
        );

        phoneNumberField.setText(
                p.phoneNumber == null ? "" : p.phoneNumber
        );

        // Display building
        buildingComboBox.setSelectedItem(p.building);

        // Select room type
        String roomTypeName =
                findNameById(roomTypeIds, p.roomTypeId);

        if (roomTypeName != null) {
            roomTypeComboBox.setSelectedItem(roomTypeName);
        }

        // Select problem type after updating room type choices
        if (p.problem != null) {
            problemComboBox.setSelectedItem(p.problem);
        }

        // Display other problem description
        otherProblemField.setText(
                p.otherProblem == null ? "" : p.otherProblem
        );

        // Display room and location description
        roomField.setText(
                p.room == null ? "" : p.room
        );

        locationDescriptionField.setText(
                p.locationDescription == null
                        ? "" : p.locationDescription
        );

        // Update visibility of the Other Problem field
        updateOtherProblemField();

    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Invalid Problem ID."
        );
    }
    }//GEN-LAST:event_problemTableMouseClicked

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed

     StudentDashboard dashboard =
            new StudentDashboard(studentId, "");

    dashboard.setVisible(true);
    this.dispose();    }//GEN-LAST:event_backButtonActionPerformed

    private void problemComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_problemComboBoxActionPerformed
        Object selected = problemComboBox.getSelectedItem();

    boolean isOthers = selected != null
            && "OTHERS".equalsIgnoreCase(selected.toString());

    otherProblemField.setVisible(isOthers);

    if (!isOthers) {
        otherProblemField.setText("");
    }

    getContentPane().revalidate();
    getContentPane().repaint();
    }//GEN-LAST:event_problemComboBoxActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
       try {
     int problemId = Integer.parseInt(
        problemIdValueLabel.getText().trim()
);

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();

        String building = (String) buildingComboBox.getSelectedItem();
        String roomType = (String) roomTypeComboBox.getSelectedItem();
        String problem = (String) problemComboBox.getSelectedItem();

        String room = roomField.getText().trim();
        String locationDescription =
                locationDescriptionField.getText().trim();

        String otherProblem = otherProblemField.getText().trim();

        if (building == null || roomType == null || problem == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this, "Please select all required options."
            );
            return;
        }

        Integer buildingId = buildingIds.get(building);
        Integer roomTypeId = roomTypeIds.get(roomType);
        Integer problemTypeId =
        problemTypeIds.get(problem.trim().toUpperCase());

        if (buildingId == null || roomTypeId == null
        || problemTypeId == null) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            "Building ID: " + buildingId
            + "\nRoom Type ID: " + roomTypeId
            + "\nProblem Type ID: " + problemTypeId
    );
    return;
}

        if ("OTHERS".equalsIgnoreCase(problem)
                && otherProblem.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this, "Please describe the other problem."
            );
            return;
        }

        Data.ProblemData p = new Data.ProblemData(
                problemId,
                studentId,
                firstName,
                lastName,
                phoneNumber,
                problem,
                building,
                room,
                locationDescription
        );

        p.buildingId = buildingId;
        p.roomTypeId = roomTypeId;
        p.problemTypeId = problemTypeId;
        p.otherProblem = "OTHERS".equalsIgnoreCase(problem)
                ? otherProblem : "";
        p.status = "Pending";

        if (ProblemDAO.updateProblem(p)) {
            javax.swing.JOptionPane.showMessageDialog(
                    this, "Problem updated successfully!"
            );
            loadProblems();
        } else {
            javax.swing.JOptionPane.showMessageDialog(
                    this, "Update failed. Only your own Pending problems can be edited."
            );
        }

    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(
                this, "Please select a problem from the table first."
        );
    }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void otherProblemFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherProblemFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_otherProblemFieldActionPerformed

    private void firstNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameFieldActionPerformed

    private void lastNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameFieldActionPerformed

    private void roomTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomTypeComboBoxActionPerformed
     updateProblemChoices();
    }//GEN-LAST:event_roomTypeComboBoxActionPerformed

    private void problemTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_problemTableMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_problemTableMouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new EditProblem().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JComboBox<String> buildingComboBox;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JTextArea locationDescriptionField;
    private javax.swing.JTextField otherProblemField;
    private javax.swing.JTextField phoneNumberField;
    private javax.swing.JComboBox<String> problemComboBox;
    private javax.swing.JLabel problemIdValueLabel;
    private javax.swing.JTable problemTable;
    private javax.swing.JTextField roomField;
    private javax.swing.JComboBox<String> roomTypeComboBox;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
