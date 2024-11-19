import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class RegistroFrame extends JFrame {
    private JTextField txtUsuario, txtNombre, txtApellido, txtTelefono, txtCorreo;
    private JPasswordField txtContrasena, txtConfirmarContrasena;
    private RegistroDeUsuarios registro;

    public RegistroFrame(RegistroDeUsuarios registro) {
        this.registro = registro;

        setTitle("Registro de Usuario");
        setSize(400, 300);
        setLayout(new GridLayout(8, 2));

        txtUsuario = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        txtContrasena = new JPasswordField();
        txtConfirmarContrasena = new JPasswordField();

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarUsuario());

        add(new JLabel("Usuario:"));
        add(txtUsuario);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Apellido:"));
        add(txtApellido);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        add(new JLabel("Correo Electrónico:"));
        add(txtCorreo);
        add(new JLabel("Contraseña:"));
        add(txtContrasena);
        add(new JLabel("Confirmar Contraseña:"));
        add(txtConfirmarContrasena);
        add(btnRegistrar);
    }

    private void registrarUsuario() {
        String usuario = txtUsuario.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String contrasena = new String(txtContrasena.getPassword());
        String confirmarContrasena = new String(txtConfirmarContrasena.getPassword());

        if (usuario.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        if (!contrasena.equals(confirmarContrasena)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.");
            return;
        }

        try {
            if (registro.registrarUsuario(new Usuario(usuario, nombre, apellido, telefono, correo, contrasena))) {
                JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "El nombre de usuario ya está en uso.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
