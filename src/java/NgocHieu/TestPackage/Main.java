/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NgocHieu.TestPackage;

/**
 *
 * @author admin
 */
import NgocHieu.TestPackage.SmsApi.SmsApi;
import java.io.IOException;
import java.io.UnsupportedEncodingException;



public class Main {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) {
		SmsApi api  = new SmsApi("1I22jzxlVXF4NTAskNBxbranvg7ak826");
		try {
			String result = api.sendSMS("0397761602", "hello", 2, "test");
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
