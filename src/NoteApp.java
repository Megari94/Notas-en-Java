import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteApp extends JFrame {
    private JTextField noteTitleField;
    private JTextArea noteContentArea;
    private JTable notesTable;
    private DefaultTableModel tableModel;
    private ArrayList<Note> notes;

    public NoteApp() {
        notes = new ArrayList<Note>();
        setTitle("Note App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Panel principal

        JPanel panel = new JPanel(new BorderLayout());
        getContentPane().add(panel);

        //Panel para ingresar notas
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        panel.add(inputPanel, BorderLayout.NORTH);

        inputPanel.add(new JLabel("Titulo:"));
        noteTitleField = new JTextField();
        inputPanel.add(noteTitleField);

        inputPanel.add(new JLabel("Contenido:"));
        noteContentArea = new JTextArea(3, 20);
        inputPanel.add(new JScrollPane(noteContentArea));

        //Botones de accion

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Agregar Nota");
        JButton updateButton = new JButton("Modificar Nota");
        JButton deleteButton = new JButton("Borrar Nota");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        //Tabla para mostrar notas

        String[] columnNames = {"Titulo", "Contenido"};
        tableModel = new DefaultTableModel(columnNames, 0);
        notesTable = new JTable(tableModel);
        panel.add(new JScrollPane(notesTable), BorderLayout.SOUTH);

        //Manejador de eventos

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             addNote();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateNote();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteNote();
            }
        });
    }

    private void addNote(){
        String title = noteTitleField.getText();
        String content = noteContentArea.getText();
        if(!title.isEmpty() && !content.isEmpty()){
            Note note = new Note(title, content);
            notes.add(note);
            tableModel.addRow(new Object[]{title, content});
            clearFields();
        }else{
            JOptionPane.showMessageDialog(this, "El titulo y el cuerpo del texto no pueden estar vacios");
        }
    }

    private void updateNote(){
        int selectedRow= notesTable.getSelectedRow();
        if(selectedRow>=0){
            String title = noteTitleField.getText();
            String content = noteContentArea.getText();
            if(!title.isEmpty() && !content.isEmpty()){
                Note note = notes.get(selectedRow);
                note.setTitle(title);
                note.setContent(content);

                tableModel.setValueAt(title, selectedRow, 0);
                tableModel.setValueAt(content, selectedRow, 1);
                clearFields();
            }else{
                JOptionPane.showMessageDialog(this, "El titulo y el cuerpo del texto no pueden estar vacios");
            }
        }else{
            JOptionPane.showMessageDialog(this, "No has seleccionado ninguna nota.");
        }
    }
        private void deleteNote(){
            int selectedRow = notesTable.getSelectedRow();
            if(selectedRow>=0) {
                notes.remove(selectedRow);
                tableModel.removeRow(selectedRow);
            }else{
                JOptionPane.showMessageDialog(this, "No has seleccionado ninguna nota.");
            }
        }

        private void clearFields(){
            noteTitleField.setText("");
            noteTitleField.setText("");
        }
    }

class Note{
    private String title;
    private String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}