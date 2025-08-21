package org.example.dao;

import java.sql.Connection;

import org.example.model.Cliente;
import org.example.model.Pedido;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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


/* public void excluir(int id){
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

    }}*/



    public void excluirCascata(int id){
        // variavel para pedidos existentes

        String queryPedidos = "SELECT id, data_pedido, status FROM Pedido WHERE cliente_id = ?";
        String queryExcluirPedidos = "DELETE FROM Pedido WHERE cliente_id = ?";

        String queryExcluirCliente = "DELETE FROM Cliente WHERE id = ?";


        try (Connection conn = Conexao.conectar()){
        try(PreparedStatement st = conn.prepareStatement(queryPedidos)){

            st.setInt(1,id);
            ResultSet rt = st.executeQuery();


            boolean pedidos = false;
            System.out.println("Pedidos relacionados a esse cliente: ");
            while(rt.next()){
                pedidos = true;
                int idP = rt.getInt("id");
                Date dataPedido = rt.getDate("data_pedido");
                String status = rt.getString("status");
                System.out.println("ID: " + idP + ", Valor: " + dataPedido + ", Data: " + status);
            }

            if(pedidos){
                Scanner sc = new Scanner(System.in);

                System.out.println("Antes de excluir o cliente, os pedidos vinculados precisam ser removidos.\n--> Deseja excluir os pedidos do cliente? (s/n): ");
                String resp = sc.next();

                if(!resp.equalsIgnoreCase("s")){
                    System.out.println("Falha ao tentar excluir");
                    return;
                }


                try(PreparedStatement stPedido = conn.prepareStatement(queryExcluirPedidos)){
                    stPedido.setInt(1,id);
                    stPedido.executeUpdate();

                    System.out.println("Pedidos excluidos!");
                }
            }
        }

                    try(PreparedStatement stCliente = conn.prepareStatement(queryExcluirCliente)){
                        stCliente.setInt(1,id);
                        stCliente.executeUpdate();
                        System.out.println("Cliente excluido");
                    }
        }  catch (SQLException e){
            e.printStackTrace();
        }


    }
}
