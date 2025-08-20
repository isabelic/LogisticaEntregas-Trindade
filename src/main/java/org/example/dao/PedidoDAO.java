package org.example.dao;


import java.sql.Connection;
import org.example.Conexao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class PedidoDAO {

    public void inserir(int id, int clienteID, LocalDate dataPedido,double volume, double peso, String status){
        String query = "INSERT INTO Pedido (id,cliente_id, data_pedido, volume_m3, peso_kg, status) VALUES (?,?,?,?,?,?)";


        try(Connection conn = Conexao.conectar();
            PreparedStatement st = conn.prepareStatement(query)){


            st.setInt(1, id);
            st.setInt(2, clienteID);
            st.setObject(3, dataPedido);
            st.setDouble(4, volume);
            st.setDouble(5, peso);
            st.setString(6, status);
            st.executeUpdate();

            System.out.println("Pedido criado com sucesso!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
