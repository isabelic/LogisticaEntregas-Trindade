package org.example.dao;

import java.sql.Connection;
import org.example.Conexao;
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




}
