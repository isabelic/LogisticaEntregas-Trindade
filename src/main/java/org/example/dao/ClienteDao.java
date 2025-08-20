package org.example.dao;

import java.sql.Connection;

import org.example.model.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    public void inserir(int id,String nome, String estado){
        String query = "INSERT INTO cliente (id, nome, estado) VALUES(?,?,?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement st = conn.prepareStatement(query)){

            st.setInt(1,id);
            st.setString(2, nome);
            st.setString(3,estado);
            st.executeUpdate();

            System.out.println("Cliente cadastrado com sucesso!");

        } catch (SQLException e) {
           e.printStackTrace();
        }

    }

    public void listar(){
        List<Cliente> lista = new ArrayList<>();

        String query = "SELECT * FROM Cliente";

        try( Connection conn = Conexao.conectar();
        PreparedStatement st = conn.prepareStatement(query);
             ResultSet rt = st.executeQuery()){

            while(rt.next()){
                Cliente c = new Cliente(
                        rt.getInt("id"),
                        rt.getString("nome"),
                        rt.getString("cpf_cnpj"),
                        rt.getString("endereco"),
                        rt.getString("cidade"),
                        rt.getString("estado")
                );
                lista.add(c);
            }
        } catch (SQLException e){
            e.printStackTrace();

        }
       // return lista;
    }

    public void relatorioVolumeMaior(){
        String query = """
                SELECT c.nome, SUM(p.volume_m3) AS totalVolume
                FROM Cliente c
                JOIN Pedido p ON c.id = p.cliente_id
                JOIN Entrega e ON e.pedido_id = p.id
                WHERE e.status = 'ENTREGUE'
                GROUP BY c.nome
                ORDER BY totalVolume  DESC
               """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet rt = st.executeQuery()){

            while(rt.next()){
                System.out.println("Cliente: " +  rt.getString("nome")+
                        " | Entrega: " + rt.getString("totalVolume"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


 public void excluir(int id){
    String query = "DELETE FROM Cliente WHERE id = ? AND NOT EXISTS ( SELECT id FROM Pedido WHERE Pedido.cliente_id = Cliente.id) ";

    try (Connection conn = Conexao.conectar();
    PreparedStatement st = conn.prepareStatement(query)){

        st.setInt(1,id);
        int validar = st.executeUpdate();


        if(validar > 0 ){
            System.out.println("Cliente excluido com sucesso!");
        } else{
            System.out.println("Não foi possivel excluir esse cliente!");
        }


    }catch (SQLException e){
        e.printStackTrace();

    }}
}
