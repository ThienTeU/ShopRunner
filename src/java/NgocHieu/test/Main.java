package NgocHieu.test;

import DAL.UserDAO;
import Model.User;
import NgocHieu.service.AuthenticationService;
import NgocHieu.service.EnvConfig;
import com.nimbusds.jose.JOSEException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws SQLException, JOSEException, ParseException {
        UserDAO dao = new UserDAO();
        AuthenticationService auth = new AuthenticationService();
        User rawUser = new User("admin@gmail.com","123456");
        User user = dao.getUserByEmail("admin@gmail.com");
        String key = EnvConfig.get("SIGN_KEY");
        String token = auth.loginAuthentication(rawUser);
        System.out.println(key);
        System.out.println(token);
        System.out.println("Role:"+user.getRoleById());
        System.out.println(auth.introspect(token));
    }
}
