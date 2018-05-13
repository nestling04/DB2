
import java.sql.*;
  public class Abkezel_demo {

    private Connection conn = null;
//=======================================
  public void DrReg(){
    try {
       Class.forName("com.mysql.jdbc.Driver");
       System.out.println("Sikeres driver regisztrálás\n");
    } catch(Exception ex) { 
       System.err.println(ex.getMessage());}
  }
//=======================================
  public void Kapcs(){
    String url = "jdbc:mysql://localhost:3306/bolt";
    try {
       conn = DriverManager.getConnection(url,"root", "");
       System.out.println("Sikeres kapcsolódás\n");
    } catch(Exception ex) { 
       System.err.println(ex.getMessage());}
  }
//======================================= 
  public void LeKapcs(){
    if (conn != null) {
       try {
          conn.close();
          System.out.println("Sikeres lekapcsolódás");
       } catch(Exception ex) { System.err.println(ex.getMessage());}
    }
  }

    public static void main(String args[]){

	Abkezel_demo abk = new Abkezel_demo();
	abk.DrReg();
	abk.Kapcs();

	abk.LeKapcs();
    }
} //Abkezel_demo vége