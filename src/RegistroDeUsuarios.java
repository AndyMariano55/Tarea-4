import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroDeUsuarios {
    private Connection connection;

    public RegistroDeUsuarios() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener todos los usuarios registrados
    public List<Usuario> obtenerUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nombreUsuario = rs.getString("nombre_usuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String telefono = rs.getString("telefono");
                String correoElectronico = rs.getString("correo_electronico");
                String contrasena = rs.getString("contrasena");

                usuarios.add(new Usuario(nombreUsuario, nombre, apellido, telefono, correoElectronico, contrasena));
            }
        }

        return usuarios;
    }

    public boolean eliminarUsuario(String nombreUsuario) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE nombre_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        }
    }

    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, telefono = ?, correo_electronico = ?, contrasena = ? WHERE nombre_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getCorreoElectronico());
            stmt.setString(5, usuario.getContrasena());
            stmt.setString(6, usuario.getNombreUsuario());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        }
    }

    public Usuario buscarUsuario(String nombreUsuario) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String telefono = rs.getString("telefono");
                    String correoElectronico = rs.getString("correo_electronico");
                    String contrasena = rs.getString("contrasena");

                    return new Usuario(nombreUsuario, nombre, apellido, telefono, correoElectronico, contrasena);
                }
            }
        }
        return null;
    }
    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre_usuario, nombre, apellido, telefono, correo_electronico, contrasena) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido());
            stmt.setString(4, usuario.getTelefono());
            stmt.setString(5, usuario.getCorreoElectronico());
            stmt.setString(6, usuario.getContrasena());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        }
    }

}