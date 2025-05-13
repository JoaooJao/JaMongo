package DAO;

import estacionamento.Cliente;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClienteDAO extends Cliente {

    Connection conexao;
    PreparedStatement pstm;
    ResultSet retornoSQL;
    ArrayList<ClienteDAO> lista = new ArrayList<>();

    public int cadastrarCliente(ClienteDAO objClienteDAO) {
        int idGerado = -1;
        conexao = new ConexaoBD().conexaoMySql();
        String comandoSQL = "INSERT INTO CLIENTE (nome, cpf, telefone, cidade) VALUES (?, ?, ?, ?)";

        try {

            pstm = conexao.prepareStatement(comandoSQL, PreparedStatement.RETURN_GENERATED_KEYS);

            pstm.setString(1, objClienteDAO.getNome());
            pstm.setString(2, objClienteDAO.getCpf());
            pstm.setString(3, objClienteDAO.getTelefone());
            pstm.setString(4, objClienteDAO.getCidade());

            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                idGerado = rs.getInt(1);
            }

            JOptionPane.showMessageDialog(null, "Cliente Adicionado !", "ESTACIONAMENTO",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar cliente! Classe ClienteDAO: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar recursos: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        return idGerado;
    }

    public ArrayList<ClienteDAO> listaCliente() {
        ArrayList<ClienteDAO> lista = new ArrayList<>();
        conexao = new ConexaoBD().conexaoMySql();
        String comandoSQL = "SELECT CLIENTE.NOME AS DONO FROM VEICULOS INNER JOIN CLIENTE ON VEICULOS.ID_CLIENTE = CLIENTE.ID_CLIENTE ORDER BY CLIENTE.NOME ASC";
        try {
            pstm = conexao.prepareStatement(comandoSQL);
            retornoSQL = pstm.executeQuery();

            while (retornoSQL.next()) {
                ClienteDAO objClienteDAO = new ClienteDAO();
                objClienteDAO.setNome(retornoSQL.getString("DONO"));
                lista.add(objClienteDAO);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR " + erro.getMessage(), "CLIENTE.DAO", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (retornoSQL != null) {
                    retornoSQL.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "ERRO AO FECHAR CONEXÃO " + erro.getMessage(), "ClienteDAO", JOptionPane.ERROR_MESSAGE);
            }
        }
        return lista;
    }

    public void removerCliente(ClienteDAO objClienteDAO) {
        conexao = new ConexaoBD().conexaoMySql();
        String comandoSQL = "DELETE FROM CLIENTE WHERE NOME = ?";
        

        try {
            pstm = conexao.prepareStatement(comandoSQL);
            pstm.setString(1,objClienteDAO.getNome());
            
            pstm.execute();
            pstm.close();
            
            JOptionPane.showMessageDialog(null,"CLIENTE REMOVIDO",null,JOptionPane.INFORMATION_MESSAGE);
        } catch(SQLException e)  {
            JOptionPane.showMessageDialog(null,"Erro ao remover cliente" + e.getMessage());
        }

    }
}
