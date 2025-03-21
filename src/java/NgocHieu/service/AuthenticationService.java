package NgocHieu.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import DAL.UserDAO;
import Model.CartItem;
import jakarta.servlet.http.HttpServlet;
import java.sql.SQLException;
import java.text.ParseException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import Model.User;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService extends HttpServlet{
    
    private static String SIGN_KEY = EnvConfig.get("SIGN_KEY");
    
    public String getUserRoleFromToken(String token) throws ParseException, JOSEException{
        if(introspect(token)){
            SignedJWT signedJwt = SignedJWT.parse(token);
            return (String) signedJwt.getJWTClaimsSet().getClaim("role");
        }
        return null;
    }
    // Encode cart to jwt
    public static String generateCartToken(List<CartItem> cartItems) throws JOSEException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .claim("cart", cartItems.stream()
                        .map(item -> item.getProduct_id() + "-" + item.getProductprice_id() + "-" + item.getProductquantity_id() + "-" + item.getQuantity())
                        .collect(Collectors.joining("|"))) // Mã hóa danh sách sản phẩm
                .expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30)) // Hết hạn sau 30 ngày
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(new MACSigner(SIGN_KEY));
        return signedJWT.serialize();
    }

    // Decode JWT to cart
    public static List<CartItem> decodeCartToken(String token) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        if (!signedJWT.verify(new MACVerifier(SIGN_KEY))) {
            throw new JOSEException("JWT không hợp lệ!");
        }

        String cartData = signedJWT.getJWTClaimsSet().getStringClaim("cart");
        return cartData == null || cartData.isEmpty() ? 
                new java.util.ArrayList<>() : 
                java.util.Arrays.stream(cartData.split("\\|"))
                        .map(item -> {
                            String[] parts = item.split("-");
                            return new CartItem(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                        })
                        .collect(Collectors.toList());
    }
    
    
    public boolean introspect(String token)
            throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());

        if(token != null){
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verified = signedJWT.verify(verifier);
            return verified && expityTime.after(new Date());
        }
        return false;
    }

    public String loginAuthentication(User rawUser) throws SQLException{
        UserDAO dao = new UserDAO();
        User user = dao.getUserByUsername(rawUser.getUsername());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(rawUser.getPassword(), user.getPassword());
        
        if(!authenticated){
            System.out.println("Can not authenticated");
            return null;
        }
            
        
        var token = generateToken(user);

        return token;
    }

    private String generateToken(User user) throws SQLException{
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("NgocHieu.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(3, ChronoUnit.HOURS).toEpochMilli()))
                .claim("role",user.getRoleById())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader,payload);
        
        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

}
