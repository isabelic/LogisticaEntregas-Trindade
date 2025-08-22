package org.example.dao;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MotoristaDAO {

    public void inserir( String nome, String veiculo, String cnh, String cidadeB){
        String query = "INSERT INTO Motorista ( nome,cnh, veiculo, cidade_base) VALUES (?,?,?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement st = conn.prepareStatement(query)){


            st.setString(1,nome);
            st.setString(2,cnh);
            st.setString(3,veiculo);
            st.setString(4,cidadeB);
            st.executeUpdate();

            System.out.println("Motorista cadastrado com sucesso!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    public void relatorioTotalEntregas(){
        String query = """
                SELECT m.nome, COUNT(e.id) AS total
                FROM Motorista m 
                LEFT JOIN Entrega e ON m.id = e.motorista_id
                GROUP BY m.nome
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement st = conn.prepareStatement(query);
             ResultSet rt = st.executeQuery()){

            while(rt.next()){
                System.out.println("Motorista "+ rt.getString("motorista")+
                        " | Entregas: " + rt.getString("total"));
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }

    public void excluirCascata(int id) {
        String query = "SELECT id, pedido_id, status FROM Entrega WHERE motorista_id = ?";
        String queryExcluirEntregas = "DELETE FROM Entrega WHERE motorista_id = ?";
        String queryExcluirMotora = "DELETE FROM Motorista WHERE id = ?";

        try (Connection conn = Conexao.conectar()) {

            // Buscar entregas do motorista
            try (PreparedStatement st = conn.prepareStatement(query)) {
                st.setInt(1, id);
                ResultSet rt = st.executeQuery();

                boolean entrega = false;

                while (rt.next()) {
                    entrega = true;
                    int idEntrega = rt.getInt("id");
                    int idPedido = rt.getInt("pedido_id");
                    String status = rt.getString("status");

                    System.out.println("Entrega ID: " + idEntrega + " | Pedido: " + idPedido + " | Status: " + status);
                }

                if (entrega) {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Antes de excluir o motorista, as entregas vinculadas precisam ser removidas.\n--> Deseja excluir as entregas do motorista? (s/n): ");
                    String resp = sc.next();

                    if (!resp.equalsIgnoreCase("s")) {
                        System.out.println("Exclusão cancelada.");
                        return;
                    }

                    // Exclui entregas vinculadas
                    try (PreparedStatement stEntregas = conn.prepareStatement(queryExcluirEntregas)) {
                        stEntregas.setInt(1, id);
                        stEntregas.executeUpdate();
                        System.out.println("Entregas excluídas!");
                    }
                }
            }

            // Exclui motorista
            try (PreparedStatement stMotora = conn.prepareStatement(queryExcluirMotora)) {
                stMotora.setInt(1, id);
                stMotora.executeUpdate();
                System.out.println("Motorista excluído com sucesso!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}






