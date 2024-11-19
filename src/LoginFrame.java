import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private RegistroDeUsuarios registro;

    public LoginFrame(RegistroDeUsuarios registro) {
        this.registro = registro;

        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistrar = new JButton("Registrar Usuario");

        add(new JLabel("Usuario:"));
        add(txtUsuario);
        add(new JLabel("Contraseña:"));
        add(txtContrasena);
        add(btnLogin);
        add(btnRegistrar);

        btnLogin.addActionListener(e -> {
            try {
                login();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnRegistrar.addActionListener(e -> abrirRegistro());
    }

    private void login() throws SQLException {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar su usuario y contraseña. Si no está registrado, debe registrarse.");
            return;
        }

        Usuario u = registro.buscarUsuario(usuario);

        if (u != null && u.getContrasena().equals(contrasena)) {
            ListaUsuariosFrame listaFrame = new ListaUsuariosFrame(registro, this);
            listaFrame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
        }
    }

    private void abrirRegistro() {
        RegistroFrame registroFrame = new RegistroFrame(registro);
        registroFrame.setVisible(true);
    }
}