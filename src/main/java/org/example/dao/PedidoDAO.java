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


    public void relatorioPedidosPendentes() {
        String query = """
        SELECT e.id AS entrega_id,
               m.nome AS motorista,
               c.nome AS cliente,
               e.status,
               e.data_saida,
               e.data_entrega
        FROM Entrega e
        JOIN Motorista m ON e.id = m.id
        JOIN Pedido p ON e.id = p.id
        JOIN Cliente c ON p.cliente_id = c.id
        WHERE e.status = 'ATRASADA';
        
    """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement st = conn.prepareStatement(query);
             ResultSet rt = st.executeQuery()) {


                while(rt.next()) {
                    System.out.println("Entrega ID: " + rt.getInt("id") +
                            " | Cliente: " + rt.getString("cliente") +
                            " | Motorista: " + rt.getString("motorista") +
                            " | Status: " + rt.getString("status") +
                            " | Data Saída: " + rt.getDate("data_saida") +
                            " | Data Entrega: " + rt.getDate("data_entrega"));


            }

        } catch (SQLException e) {
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
                 System.out.println("Cliente: " +  rt.getString("id") +
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


            st.setString(1,"CANCELAR");
            st.executeUpdate();

            System.out.println("Pedido cancelado com sucesso!");

        }catch (SQLException e){
            e.printStackTrace();
        }



    }

}
