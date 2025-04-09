package logical;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tipo;
    private String userName;
    private String pass;

    public User(String tipo, String userName, String pass) {
        this.tipo = "Administrador";
        this.userName = userName;
        this.pass = hashPassword(pass);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }

    public boolean checkPassword(String inputPassword) {
        return this.pass.equals(hashPassword(inputPassword));
    }

    // Getters y Setters
    public String getTipo() { return tipo; }
    public String getUserName() { return userName; }
    public String getPass() { return pass; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPass(String pass) { this.pass = hashPassword(pass); }
}