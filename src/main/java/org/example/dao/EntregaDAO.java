package org.example.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EntregaDAO {

    public void inserir(int pedidoID, int motoristaID, String status){
        String query = "INSERT INTO Entrega (pedido_id, motorista_id,status) VALUES (?,?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement st = conn.prepareStatement(query)){

            st.setInt(1, pedidoID);
            st.setInt(2, motoristaID);;
            st.setString(3, status);
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

    public void relatorioEntrega(){
        String query = """
                        SELECT m.nome, COUNT(e.id) AS totalEntregas
                        FROM Motorista m
                        LEFT JOIN Entrega e ON m.id = e.motorista_id
                        GROUP BY m.nome
                           """;


        try (Connection conn = Conexao.conectar();
        PreparedStatement st = conn.prepareStatement(query);
         ResultSet rt = st.executeQuery()){

            while (rt.next()) {

                System.out.println("Motorista: "+ rt.getString("nome")+
                        " | Entregas: " + rt.getString("totalEntregas"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void relatorioEntregasAtrasadas(){
        String query = """
                SELECT c.cidade, COUNT(e.id) AS atrasadas
                FROM Entrega e
                JOIN  Pedido p ON  e.pedido_id =  p.id
                WHERE e.status = 'ATRASADA'
                GROUP BY c.cidade
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rt  = st.executeQuery()){
            while (rt.next()){
                System.out.println("Cliente: " + rt.getString("nome")+
                        " | Entregas atrasasdas: " + rt.getString("atrasadas"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void excluir(int entregaID){
        // query para validacao
        String query = "SELECT id FROM entrega WHERE id = ?";
        // query para exclusão
        String excluirQuery = "DELETE FROM Entrega WHERE id = ? ";

        //setar o id --> variavel
        try(Connection conn = Conexao.conectar();
            // usando a query para validar
        PreparedStatement st =  conn.prepareStatement(query)){;
         st.setInt(1,entregaID);
         ResultSet rt = st.executeQuery();

         // Validar se a entrega foi excluida ou nao
         if(rt.next()){
             // usando a query de exclusao
          try(PreparedStatement excluirST = conn.prepareStatement(excluirQuery)){
              excluirST.setInt(1,entregaID);

              int validar = excluirST.executeUpdate();


              if(validar > 0 ){
                  System.out.println("Entrega excluida com sucesso!");
              } else{
                  System.out.println("Falha ao excluir a entrega!");
              }
          }

        } else{
                System.out.println("Entrega com ID: "+ entregaID + " não encontrada");
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }







}
