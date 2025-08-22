package org.example.dao;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PedidoDAO {

    public void inserir( int clienteID, LocalDate dataPedido,double volume, double peso){
        String query = "INSERT INTO Pedido (cliente_id, data_pedido, volume_m3, peso_kg) VALUES (?,?,?,?)";


        try(Connection conn = Conexao.conectar();
            PreparedStatement st = conn.prepareStatement(query)){

            st.setObject(1, clienteID);
            st.setObject(2, dataPedido);
            st.setDouble(3, volume);
            st.setDouble(4, peso);

            st.executeUpdate();

            System.out.println("Pedido criado com sucesso!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void relatorioPedidosPendentes(){
        String query = """
                SELECT c.estado, COUNT(e.id) AS pendencias
                FROM Pedido p
                JOIN Cliente c ON c.id = p.cliente_id
                WHERE p.status = 'PENDENTE'
                GROUP BY c.estado
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement st = conn.prepareStatement(query);
             ResultSet rt = st.executeQuery()){

            while(rt.next()){
                System.out.println("Cliente: "+ rt.getString("nome") +
                        " | Pedidos pendentes: " + rt.getString("pendencias"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void relorioBuscarCpfCnpj(){
        String query = """
                SELECT p.id, p.data_pedido
                FROM Pedido p 
                JOIN Cliente c ON c.id = p.cliente_id
                WHERE c.cpf_cnpj
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement st = conn.prepareStatement(query);
            ResultSet rt = st.executeQuery()){

             while(rt.next()){
                 System.out.println("Cliente: " +  rt.getString("nome") +
                         " | Data do pedido: " + rt.getString("data_pedido"));
             }
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }


    public void cancelarPedido(int id){
        String query = "UPDATE Pedido  SET status = 'CANCELAR' WHERE id = ? ";

        try(Connection conn = Conexao.conectar();
        PreparedStatement st = conn.prepareStatement(query)){


            st.setInt(1,id);
            st.executeUpdate();

            System.out.println("Pedido cancelado com sucesso!");

        }catch (SQLException e){
            e.printStackTrace();
        }



    }

}
