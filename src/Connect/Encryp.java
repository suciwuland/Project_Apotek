/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connect;

/**
 *
 * @author ASUS
 */
import java.math.BigInteger;
import java.security.MessageDigest;
public class Encryp {
    public static String MD5(String input){
        try{
            MessageDigest md =MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number =new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while(hashtext.length()<32){
                hashtext ="0"+hashtext;
            }
            return hashtext;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
