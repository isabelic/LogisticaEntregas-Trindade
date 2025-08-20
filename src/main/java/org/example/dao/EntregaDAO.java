package org.example.dao;

import java.sql.Connection;
import org.example.Conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EntregaDAO {

    public void inserir(int id, int pedidoID, int motoristaID, String status){
        String query = "INSERT INTO Entrega (id,pedido_id, motorista_id,status) VALUES (?,?,?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement st = conn.prepareStatement(query)){
            st.setInt(1, id);
            st.setInt(2, pedidoID);
            st.setInt(3, motoristaID);;
            st.setString(4, status);
            st.executeUpdate();

            System.out.println(" Entrega criada com sucesso!");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    public void atualizarStatus(int id, String statusAtualizado, LocalDate dataEntrega){
        String query = "UPDATE Entrega set status = ?, data_entrega = ?, WHERE id = ?";

        try(Connection conn = Conexao.conectar();
        PreparedStatement st = conn.prepareStatement(query)){
            st.setString(1, statusAtualizado);
            st.setObject(2, dataEntrega);
            st.setInt(3, id);
            st.executeUpdate();
            System.out.println("Status de entrega atualizado!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void ListarMC (){
        String query = """
            SELECT e.id, c.nome AS Cliente, m.nome AS Motorista, e.status
            FROM Entrega e 
            JOIN Pedido p ON e.pedido_id = p.id
            JOIN Cliente c ON p.cliente_id = c.id
            JOIN Motorista m ON e.motorista_id = m.id
        """;


        try (Connection conn = Conexao.conectar();
        PreparedStatement st = conn.prepareStatement(query);

            ResultSet rt = st.executeQuery()){
             while(rt.next()){

                 System.out.println("Entrega " + rt.getInt("id")+
                         " | Cliente: " + rt.getString("cliente")+
                         " | Motorista: " + rt.getString("motorista")+
                         " | Status: "+ rt.getString("status"));
             }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }




}
