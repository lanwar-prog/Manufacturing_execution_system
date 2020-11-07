package folderClasses;

import java.sql.SQLException;
import org.apache.tomcat.util.buf.Utf8Encoder;

public class Auth {

    private WorkDB database = new WorkDB();

    public boolean equalsPassword(String login, String passwordEnter) throws SQLException {
        if (database.getPassword(login).equals(passwordEnter)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkLogin(String login) {
        try {
            return database.getLogin(login);
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean equalsData(String login, String password) {
        //byte[] b = new byte[20];
        //password.hashCode();

        WorkDB database = new WorkDB();
        String temp = database.getPassword(login);
        if (temp.equals(password)) {
            return true;
        } else {
            return false;
        }
    }
 
    private boolean equalsPassword(String password) {
        //byte[] b = new byte[20];
        //int i = password.hashCode();

        String pass = database.getPassword(password);
        if (pass.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    //public byte[] GetHash(String password) {
    //    Utf8Encoder encoder = new Utf8Encoder();
    //    var MD5 = new MD5CryptoServiceProvider();
    //    byte[] hash = MD5.ComputeHash(encoder.GetBytes(password));
    //    return hash;
    //}
    public int getRole(String Login) {
        try {
            return database.getRole(Login);
        } catch (SQLException e) {
            return -1;
        }
    }
}
