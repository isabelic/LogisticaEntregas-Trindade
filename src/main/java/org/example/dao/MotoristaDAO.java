package org.example.dao;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MotoristaDAO {

    public void inserir(int id, String nome, String veiculo){
        String query = "INSERT INTO Motorista (id, nome,veiculo) VALUES (?,?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement st = conn.prepareStatement(query)){

            st.setInt(1,id);
            st.setString(2,nome);
            st.setString(3,veiculo);

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


}
