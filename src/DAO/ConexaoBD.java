package DAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
public class ConexaoBD {
    
    public Connection conexaoMySql(){
        
        Connection conexao = null;
        try{ 
      
                String url = "jdbc:mysql://localhost:3306/ESTACIONAMENTO?user=root&password=505147ATm!"; 
                System.out.println("OK");
                conexao = DriverManager.getConnection(url); 
            }  
        catch (SQLException erro){ 
                JOptionPane.showMessageDialog(null,"ConexaoBD: " + erro.getMessage()); 
            } 
        return conexao; 
    }
}
