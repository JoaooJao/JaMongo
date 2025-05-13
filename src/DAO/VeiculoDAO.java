package DAO;

import estacionamento.Veiculos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.sql.ResultSet;

public class VeiculoDAO extends Veiculos {

    Connection conexao;
    PreparedStatement pstm;
    ResultSet retornoSQL;
    ArrayList<VeiculoDAO> lista = new ArrayList<>();

    public void cadastrarVeiculo(VeiculoDAO objVeiculoDAO) {
        conexao = new ConexaoBD().conexaoMySql();
        String comandoSQL = "INSERT INTO VEICULOS (id_cliente,placa,marca,modelo,cor) VALUES (?,?,?,?,?)";
        try {
            pstm = conexao.prepareStatement(comandoSQL);
            pstm.setInt(1, objVeiculoDAO.getIdCliente());
            pstm.setString(2, objVeiculoDAO.getPlaca());
            pstm.setString(3, objVeiculoDAO.getMarca());
            pstm.setString(4, objVeiculoDAO.getModelo());
            pstm.setString(5, objVeiculoDAO.getCor());

            pstm.execute();
            JOptionPane.showMessageDialog(null, "# Veiculo Adicionado c/Sucesso!!!", "ESTACIONAMENTO",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar Veiculo! Classe VeiculoAO: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
    }

    public ArrayList<VeiculoDAO> listaVeiculo() {
        ArrayList<VeiculoDAO> lista = new ArrayList<>();
        conexao = new ConexaoBD().conexaoMySql();
        String comandoSQL = "SELECT VEICULOS.MARCA, VEICULOS.MODELO, VEICULOS.PLACA, VEICULOS.COR FROM VEICULOS INNER JOIN CLIENTE ON VEICULOS.ID_CLIENTE = CLIENTE.ID_CLIENTE";
        try {
            pstm = conexao.prepareStatement(comandoSQL);
            retornoSQL = pstm.executeQuery();

            while (retornoSQL.next()) {
                VeiculoDAO objVeiculoDAO = new VeiculoDAO();
                objVeiculoDAO.setMarca(retornoSQL.getString("MARCA"));
                objVeiculoDAO.setModelo(retornoSQL.getString("MODELO"));
                objVeiculoDAO.setPlaca(retornoSQL.getString("PLACA"));
                objVeiculoDAO.setCor(retornoSQL.getString("COR"));
                lista.add(objVeiculoDAO);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR VEICULO " + e.getMessage(), "VEICULODAO", JOptionPane.ERROR_MESSAGE);
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
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERRO AO FECHAR CONEXÕES " + e.getMessage(), "VeiculoDAO", JOptionPane.ERROR_MESSAGE);
            }
        }
        return lista;
    }


}
