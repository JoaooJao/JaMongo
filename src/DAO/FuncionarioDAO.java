package DAO;
import estacionamento.Funcionario;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;

public class FuncionarioDAO extends Funcionario {
    
    //Atributo
    Connection conexao;
    PreparedStatement pstm;
    //Métodos
    public ResultSet autenticacaoFuncionario(Funcionario objFuncionarioDAO){
        //Fazendo a conexão com o Banco
        conexao = new ConexaoBD().conexaoMySql();
        
        //Execução
        
        try{
            //Sentença para pegar o nome e senha do usuario
            String comandoSQL = "SELECT * FROM FUNCIONARIO WHERE NOME = ? AND SENHA = ?;";
            
            // Classe do objeto
            PreparedStatement pstm = conexao.prepareStatement(comandoSQL);
            
            //Método
            pstm.setString(1,objFuncionarioDAO.getNome());
            pstm.setString(2,objFuncionarioDAO.getSenha());
            
            //Execução
            ResultSet execucaoSQL = pstm.executeQuery();
            
            return execucaoSQL;
        }
        catch(SQLException erro)
        {
            JOptionPane.showMessageDialog(null,"FuncionarioDAO" + erro);
            
            return null;
        }
    }
    
    public void novoFuncionario(FuncionarioDAO objFuncionarioDAO){
        conexao = new ConexaoBD().conexaoMySql();
        String comandoSQL = "INSERT INTO FUNCIONARIO(NOME,SENHA) VALUES(?,?)";
        try{
            pstm = conexao.prepareStatement(comandoSQL);
            pstm.setString(1, objFuncionarioDAO.getNome());
            pstm.setString(2, objFuncionarioDAO.getSenha());
            
            pstm.execute();
            JOptionPane.showMessageDialog(null,"Novo Funcionario Cadastrado","Cadastro Funcionario",JOptionPane.OK_OPTION);
        }catch(SQLException erro){
            JOptionPane.showMessageDialog(null,"ERRO AO CADASTRAR","CADASTRO FUNCIONARIO", JOptionPane.ERROR_MESSAGE);
        }finally{
            try{
                if (pstm != null){
                    pstm.close();
                }
                if(pstm != null){
                    pstm.close();
                }
            }catch(SQLException erro){
                JOptionPane.showMessageDialog(null,"Erro ao fechar Conexão Final","FUNCIONARIODAO", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
