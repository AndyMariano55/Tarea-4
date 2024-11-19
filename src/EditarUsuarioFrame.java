import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditarUsuarioFrame extends JFrame {
    private RegistroDeUsuarios registro;
    private Usuario usuario;
    private ListaUsuariosFrame listaUsuariosFrame;

    public EditarUsuarioFrame(RegistroDeUsuarios registro, Usuario usuario, ListaUsuariosFrame listaUsuariosFrame) {
        this.registro = registro;
        this.usuario = usuario;
        this.listaUsuariosFrame = listaUsuariosFrame;

        setTitle("Editar Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        JTextField txtNombre = new JTextField(usuario.getNombre());
        JTextField txtApellido = new JTextField(usuario.getApellido());
        JTextField txtTelefono = new JTextField(usuario.getTelefono());
        JTextField txtCorreo = new JTextField(usuario.getCorreoElectronico());
        JTextField txtContrasena = new JTextField(usuario.getContrasena());

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Apellido:"));
        add(txtApellido);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        add(new JLabel("Correo:"));
        add(txtCorreo);
        add(new JLabel("Contraseña:"));
        add(txtContrasena);

        JButton btnGuardar = new JButton("Guardar Cambios");
        add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                usuario.setNombre(txtNombre.getText());
                usuario.setApellido(txtApellido.getText());
                usuario.setTelefono(txtTelefono.getText());
                usuario.setCorreoElectronico(txtCorreo.getText());
                usuario.setContrasena(txtContrasena.getText());

                registro.actualizarUsuario(usuario);

                JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.");
                listaUsuariosFrame.dispose();
                new ListaUsuariosFrame(registro, listaUsuariosFrame.getLoginFrame()).setVisible(true);
                this.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el usuario: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}


