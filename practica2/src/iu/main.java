package iu;

import model.batalla;
import model.historial;
import model.personaje;
import trabajadores.trabajadorBatalla;
import util.persistencia;
import javax.swing.Timer;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class main extends JFrame {
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID","Nombre","Arma","HP","ATK","DEF","AGI","SPD"},0) {
        public boolean isCellEditable(int r, int c){return false;}
    };
    private final JTable table = new JTable(tableModel);
    private final JTextArea logArea = new JTextArea(15, 50);
    private final JTextArea battleLog = new JTextArea(10,50);
    private final java.util.List<personaje> personajes = Collections.synchronizedList(new ArrayList<>());
    private final historial historial = new historial();
    private trabajadorBatalla currentWorker = null;

    // campos
    private final JTextField tfNombre = new JTextField(15);
    private final JTextField tfArma = new JTextField(15);
    private final JTextField tfHP = new JTextField(5);
    private final JTextField tfATK = new JTextField(5);
    private final JTextField tfSPD = new JTextField(5);
    private final JTextField tfAGI = new JTextField(5);
    private final JTextField tfDEF = new JTextField(5);

    public main() {
        super("ArenaUSAC - Práctica 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,700);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel main = new JPanel(new BorderLayout(8,8));
        JPanel left = new JPanel(new BorderLayout(6,6));
        JPanel right = new JPanel(new BorderLayout(6,6));

        // tabla personajes
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        left.add(new JScrollPane(table), BorderLayout.CENTER);

        // panel agregar / editar
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3);
        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Nombre:"), c);
        c.gridx = 1; form.add(tfNombre, c);
        c.gridx = 0; c.gridy++; form.add(new JLabel("Arma:"), c);
        c.gridx = 1; form.add(tfArma, c);
        c.gridx = 0; c.gridy++; form.add(new JLabel("HP (100-500):"), c);
        c.gridx = 1; form.add(tfHP, c);
        c.gridx = 0; c.gridy++; form.add(new JLabel("Ataque (10-100):"), c);
        c.gridx = 1; form.add(tfATK, c);
        c.gridx = 0; c.gridy++; form.add(new JLabel("Velocidad (1-10):"), c);
        c.gridx = 1; form.add(tfSPD, c);
        c.gridx = 0; c.gridy++; form.add(new JLabel("Agilidad (1-10):"), c);
        c.gridx = 1; form.add(tfAGI, c);
        c.gridx = 0; c.gridy++; form.add(new JLabel("Defensa (1-50):"), c);
        c.gridx = 1; form.add(tfDEF, c);

        JPanel btns = new JPanel();
        JButton btnAdd = new JButton("Agregar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar");
btns.add(btnBuscar);  btns.add(btnAdd); btns.add(btnEdit); btns.add(btnDelete);
        c.gridx = 0; c.gridy++; c.gridwidth = 2; form.add(btns, c);
        left.add(form, BorderLayout.SOUTH);
        
       


        // right: controls de batalla y logs
        JPanel controls = new JPanel();
        JComboBox<String> cbA = new JComboBox<>();
        JComboBox<String> cbB = new JComboBox<>();
        JButton btnStart = new JButton("Iniciar Batalla");
        JButton btnStop = new JButton("Detener Batalla");
        controls.add(new JLabel("Combatiente A:")); controls.add(cbA);
        controls.add(new JLabel("Combatiente B:")); controls.add(cbB);
        controls.add(btnStart); controls.add(btnStop);
        right.add(controls, BorderLayout.NORTH);

        // logs
        logArea.setEditable(false);
        battleLog.setEditable(false);
        right.add(new JScrollPane(battleLog), BorderLayout.CENTER);
        right.add(new JScrollPane(logArea), BorderLayout.SOUTH);

        main.add(left, BorderLayout.CENTER);
        main.add(right, BorderLayout.EAST);

        setContentPane(main);

        // listeners
        btnAdd.addActionListener(e -> onAdd(cbA, cbB));
        btnEdit.addActionListener(e -> onEdit());
        btnDelete.addActionListener(e -> onDelete(cbA, cbB));
        btnStart.addActionListener(e -> onStartBattle(cbA, cbB));
        btnStop.addActionListener(e -> onStopBattle());
        btnBuscar.addActionListener(e -> onBuscar());
        


        table.getSelectionModel().addListSelectionListener((ListSelectionEvent ev) -> {
            if (!ev.getValueIsAdjusting() && table.getSelectedRow() >= 0) fillFormFromSelected();
        });

        // menu para guardar/cargar
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("Archivo");
        JMenuItem miSave = new JMenuItem("Guardar...");
        JMenuItem miLoad = new JMenuItem("Cargar...");
        JMenuItem miHistorial = new JMenuItem("Ver Historial");
file.add(miHistorial);

miHistorial.addActionListener(e -> {
    StringBuilder sb = new StringBuilder();
    for (batalla b : historial.getAll()) {
        sb.append(b.toString()).append("\n\n");
    }
    JTextArea area = new JTextArea(sb.toString(), 20, 60);
    area.setEditable(false);
    JOptionPane.showMessageDialog(this, new JScrollPane(area), "Historial de Batallas", JOptionPane.INFORMATION_MESSAGE);
});
 JMenuItem miEstudiante = new JMenuItem("Datos del Estudiante");
file.add(miEstudiante);

miEstudiante.addActionListener(e -> JOptionPane.showMessageDialog(this,
        "Nombre: Ramiro Andres Castellanos Davila\n" +
        "Carné: 202320574\n" +
        "Sección: B\n" +
        "Curso: IPC1 - 2do Semestre 2025",
        "Datos del Estudiante", JOptionPane.INFORMATION_MESSAGE));
        file.add(miSave); file.add(miLoad);
        mb.add(file);
        setJMenuBar(mb);
       




        miSave.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                try { persistencia.save(f.getAbsolutePath(), personajes, historial); JOptionPane.showMessageDialog(this,"Guardado OK"); }
                catch (Exception ex) { JOptionPane.showMessageDialog(this,"Error guardando: "+ex.getMessage()); }
            }
        });

        miLoad.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                try {
                    Map<String,Object> map = persistencia.load(f.getAbsolutePath());
                    personajes.clear();
                    personajes.addAll((List<personaje>)map.get("personajes"));
                    // historial es nuevo referencia; si quieres mantener historial, adaptarlo
                    // aquí simplemente reemplazamos
                    // cast
                    historial h = (historial) map.get("historial");
                    // empty and add
                    // we can't reassign final historial, so copy
                    for (batalla b : h.getAll()) historial.add(b);
                    refreshTable();
                    refreshCombos(cbA, cbB);
                    JOptionPane.showMessageDialog(this,"Cargado OK");
                } catch (Exception ex) { JOptionPane.showMessageDialog(this,"Error cargando: "+ex.getMessage()); }
            }
        });
        
        miHistorial.addActionListener(e -> {
    StringBuilder sb = new StringBuilder();
    for (batalla b : historial.getAll()) {
        sb.append(b.toString()).append("\n\n");
    }

        // double click to quick-fill form
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) fillFormFromSelected();
            }
        });
        });
                }


    private void onAdd(JComboBox<String> cbA, JComboBox<String> cbB) {
        try {
            String nombre = tfNombre.getText().trim();
            String arma = tfArma.getText().trim();
            int hp = Integer.parseInt(tfHP.getText().trim());
            int atk = Integer.parseInt(tfATK.getText().trim());
            int spd = Integer.parseInt(tfSPD.getText().trim());
            int agi = Integer.parseInt(tfAGI.getText().trim());
            int def = Integer.parseInt(tfDEF.getText().trim());

            // validaciones
            if (nombre.isEmpty()) { JOptionPane.showMessageDialog(this,"Nombre vacío"); return; }
            if (existsName(nombre)) { JOptionPane.showMessageDialog(this,"Nombre ya existe (no sensible a mayúsculas)"); return; }
            if (hp < 100 || hp > 500) { JOptionPane.showMessageDialog(this,"HP fuera de rango"); return; }
            if (atk < 10 || atk > 100) { JOptionPane.showMessageDialog(this,"Ataque fuera de rango"); return; }
            if (spd < 1 || spd > 10) { JOptionPane.showMessageDialog(this,"Velocidad fuera de rango"); return; }
            if (agi < 1 || agi > 10) { JOptionPane.showMessageDialog(this,"Agilidad fuera de rango"); return; }
            if (def < 1 || def > 50) { JOptionPane.showMessageDialog(this,"Defensa fuera de rango"); return; }

            personaje p = new personaje(nombre, arma, hp, atk, spd, agi, def);
            personajes.add(p);
            refreshTable();
            refreshCombos(cbA, cbB);
            logArea.append("Agregado: " + p.toString() + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"Campos numéricos inválidos");
        }
    }

    private void onEdit() {
        int r = table.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this,"Selecciona un personaje para editar"); return; }
        int id = Integer.parseInt(tableModel.getValueAt(r,0).toString());
        personaje p = findById(id);
        if (p == null) return;
        try {
            String nombre = tfNombre.getText().trim();
            String arma = tfArma.getText().trim();
            int hp = Integer.parseInt(tfHP.getText().trim());
            int atk = Integer.parseInt(tfATK.getText().trim());
            int spd = Integer.parseInt(tfSPD.getText().trim());
            int agi = Integer.parseInt(tfAGI.getText().trim());
            int def = Integer.parseInt(tfDEF.getText().trim());

            if (nombre.isEmpty()) { JOptionPane.showMessageDialog(this,"Nombre vacío"); return; }
            // si cambia nombre, validar duplicado con los demás
            if (!p.getNombre().equalsIgnoreCase(nombre) && existsName(nombre)) { JOptionPane.showMessageDialog(this,"Nombre ya existe"); return; }
            if (hp < 100 || hp > 500) { JOptionPane.showMessageDialog(this,"HP fuera de rango"); return; }
            if (atk < 10 || atk > 100) { JOptionPane.showMessageDialog(this,"Ataque fuera de rango"); return; }
            if (spd < 1 || spd > 10) { JOptionPane.showMessageDialog(this,"Velocidad fuera de rango"); return; }
            if (agi < 1 || agi > 10) { JOptionPane.showMessageDialog(this,"Agilidad fuera de rango"); return; }
            if (def < 1 || def > 50) { JOptionPane.showMessageDialog(this,"Defensa fuera de rango"); return; }

            p.setNombre(nombre); p.setArma(arma); p.setHp(hp); p.setAtaque(atk); p.setVelocidad(spd); p.setAgilidad(agi); p.setDefensa(def);
            refreshTable();
            logArea.append("Editado: " + p.toString() + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"Campos numéricos inválidos");
        }
    }

    private void onDelete(JComboBox<String> cbA, JComboBox<String> cbB) {
        int r = table.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this,"Selecciona un personaje para eliminar"); return; }
        int id = Integer.parseInt(tableModel.getValueAt(r,0).toString());
        personaje p = findById(id);
        if (p == null) return;

        int conf = JOptionPane.showConfirmDialog(this,"¿Confirmar eliminación de "+p.getNombre()+"? Esto conservará su nombre en historial.","Confirmar",JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION) return;

        personajes.remove(p);
        refreshTable();
        refreshCombos(cbA, cbB);
        logArea.append("Eliminado: " + p.getNombre() + "\n");
    }

    private void onStartBattle(JComboBox<String> cbA, JComboBox<String> cbB) {
        int idxA = cbA.getSelectedIndex();
        int idxB = cbB.getSelectedIndex();
        if (idxA < 0 || idxB < 0 || idxA == idxB) { JOptionPane.showMessageDialog(this,"Selecciona dos personajes distintos"); return; }
        personaje a = personajes.get(idxA);
        personaje b = personajes.get(idxB);
        if (!a.estaVivo() || !b.estaVivo()) { JOptionPane.showMessageDialog(this,"Ambos personajes deben tener HP > 0"); return; }

        batalla bat = new batalla(a,b);
        historial.add(bat);
        battleLog.setText("");
        logArea.append("Iniciando batalla: " + a.getNombre() + " vs " + b.getNombre() + "\n");

        currentWorker = new trabajadorBatalla(a,b,bat);
        currentWorker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                if (currentWorker.getState() == SwingWorker.StateValue.DONE) {
                    try {
                        currentWorker.get(); // for exceptions
                    } catch (InterruptedException | ExecutionException ex) {
                        logArea.append("Error en batalla: " + ex.getMessage() + "\n");
                    }
                    refreshTable();
                }
            }
        });

     
        Timer t = new Timer(200, ev -> {
            List<String> logs = bat.getBitacora();
            StringBuilder sb = new StringBuilder();
            for (String s : logs) sb.append(s).append("\n");
            battleLog.setText(sb.toString());
        });
        t.start();

        currentWorker.execute();

        // stop the timer when done
        currentWorker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && currentWorker.getState() == SwingWorker.StateValue.DONE) {
                t.stop();
            }
        });
    }

    private void onStopBattle() {
        if (currentWorker != null && !currentWorker.isDone()) {
            currentWorker.cancel(true);
            logArea.append("Batalla detenida por el usuario.\n");
        }
    }

    private boolean existsName(String nombre) {
        for (personaje p : personajes) if (p.getNombre().equalsIgnoreCase(nombre.trim())) return true;
        return false;
    }

    private void fillFormFromSelected() {
        int r = table.getSelectedRow();
        if (r < 0) return;
        tfNombre.setText(tableModel.getValueAt(r,1).toString());
        tfArma.setText(tableModel.getValueAt(r,2).toString());
        tfHP.setText(tableModel.getValueAt(r,3).toString());
        tfATK.setText(tableModel.getValueAt(r,4).toString());
        tfDEF.setText(tableModel.getValueAt(r,5).toString());
        tfAGI.setText(tableModel.getValueAt(r,6).toString());
        tfSPD.setText(tableModel.getValueAt(r,7).toString());
    }

    private personaje findById(int id) {
        for (personaje p : personajes) if (p.getId() == id) return p;
        return null;
    }

    private void refreshTable() {
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);
            for (personaje p : personajes) {
                tableModel.addRow(new Object[]{
                        p.getId(), p.getNombre(), p.getArma(), p.getHp(), p.getAtaque(), p.getDefensa(), p.getAgilidad(), p.getVelocidad()
                });
            }
        });
    }

    private void refreshCombos(JComboBox<String> cbA, JComboBox<String> cbB) {
        cbA.removeAllItems(); cbB.removeAllItems();
        for (personaje p : personajes) {
            cbA.addItem(p.getNombre());
            cbB.addItem(p.getNombre());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            main app = new main();
            app.setVisible(true);
        });
    }
    private void onBuscar() {
    String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre a buscar:");
    if (nombre == null || nombre.isEmpty()) return;
    for (int i = 0; i < tableModel.getRowCount(); i++) {
        if (tableModel.getValueAt(i, 1).toString().equalsIgnoreCase(nombre.trim())) {
            table.setRowSelectionInterval(i, i);
            table.scrollRectToVisible(table.getCellRect(i, 0, true));
            return;
        }
    }
    JOptionPane.showMessageDialog(this, "Personaje no encontrado");
}

    
}
