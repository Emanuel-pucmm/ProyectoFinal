package logical;

import java.io.Serializable;
import java.util.ArrayList;
import logico.SerieNacional;

public class Control implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<User> misUsers;
    private SerieNacional serieNacional;
    private static Control control;
    private static User loginUser;

    private Control() {
        misUsers = new ArrayList<>();
        serieNacional = new SerieNacional();
        // Usuario admin por defecto
        misUsers.add(new User("Administrador", "admin", "admin123"));
    }

    public static Control getInstance() {
        if (control == null) {
            control = new Control();
        }
        return control;
    }

    public boolean confirmLogin(String username, String password) {
        for (User user : misUsers) {
            if (user.getUserName().equals(username) && user.checkPassword(password)) {
                loginUser = user;
                return true;
            }
        }
        return false;
    }

    public void regUser(User user) {
        misUsers.add(user);
    }

    // Getters y Setters
    public ArrayList<User> getMisUsers() { return misUsers; }
    public void setMisUsers(ArrayList<User> misUsers) { this.misUsers = misUsers; }
    public static User getLoginUser() { return loginUser; }
    public static void setLoginUser(User loginUser) { Control.loginUser = loginUser; }
    public SerieNacional getSerieNacional() { return serieNacional; }
    public void setSerieNacional(SerieNacional serieNacional) { this.serieNacional = serieNacional; }

    public static void setControl(Control control) {
        Control.control = control;
    }
}