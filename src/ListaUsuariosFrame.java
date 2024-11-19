import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class ListaUsuariosFrame extends JFrame {
    private RegistroDeUsuarios registro;
    private LoginFrame loginFrame;
    private JTable table;

    public ListaUsuariosFrame(RegistroDeUsuarios registro, LoginFrame loginFrame) {
        this.registro = registro;
        this.loginFrame = loginFrame;

        setTitle("Usuarios Registrados");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = { "Usuario", "Nombre", "Teléfono", "Correo" };
        try {
            updateTable(columnNames);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar la lista de usuarios.");
        }

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        JButton btnEliminarUsuario = new JButton("Eliminar Usuario");
        JButton btnEditarUsuario = new JButton("Editar Usuario");

        JPanel panel = new JPanel();
        panel.add(btnEditarUsuario);
        panel.add(btnEliminarUsuario);
        panel.add(btnCerrarSesion);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        btnCerrarSesion.addActionListener(this::cerrarSesion);
        btnEliminarUsuario.addActionListener(this::eliminarUsuario);
        btnEditarUsuario.addActionListener(this::editarUsuario);
    }

    private void updateTable(String[] columnNames) throws SQLException {
        Object[][] data = new Object[registro.obtenerUsuarios().size()][4];
        int i = 0;
        for (Usuario usuario : registro.obtenerUsuarios()) {
            data[i][0] = usuario.getNombreUsuario();
            data[i][1] = usuario.getNombre() + " " + usuario.getApellido();
            data[i][2] = usuario.getTelefono();
            data[i][3] = usuario.getCorreoElectronico();
            i++;
        }

        table = new JTable(data, columnNames);
    }

    private void cerrarSesion(ActionEvent e) {
        loginFrame.setVisible(true);
        this.dispose();
    }

    private void eliminarUsuario(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String usuario = (String) table.getValueAt(selectedRow, 0);
            try {
                if (registro.eliminarUsuario(usuario)) {
                    JOptionPane.showMessageDialog(this, "Usuario eliminado.");
                    this.dispose();
                    new ListaUsuariosFrame(registro, loginFrame).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el usuario.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el usuario: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar.");
        }
    }

    private void editarUsuario(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String usuario = (String) table.getValueAt(selectedRow, 0);
            try {
                Usuario u = registro.buscarUsuario(usuario);
                if (u != null) {

                    EditarUsuarioFrame editarUsuarioFrame = new EditarUsuarioFrame(registro, u, this);
                    editarUsuarioFrame.setVisible(true);
                    this.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al buscar el usuario: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para editar.");
        }
    }

    public LoginFrame getLoginFrame() {
        return loginFrame;
    }
}
