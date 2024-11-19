public class Main {
        public static void main(String[] args) {
                RegistroDeUsuarios registro = new RegistroDeUsuarios();
                LoginFrame loginFrame = new LoginFrame(registro);
                loginFrame.setVisible(true);
        }
}

