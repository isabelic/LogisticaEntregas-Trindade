package org.example;

import org.example.dao.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ClienteDao cdao = new ClienteDao();
        EntregaDAO edao = new EntregaDAO();
        MotoristaDAO mdao = new MotoristaDAO();
        PedidoDAO pdao = new PedidoDAO();

        int opcao;

        do{
            System.out.println("\n===== MENU SISTEMA DE ENTREGAS =====");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Cadastrar Motorista");
            System.out.println("3 - Criar Pedido");
            System.out.println("4 - Atribuir Pedido a Motorista (Gerar Entrega)");
            System.out.println("5 - Registrar Evento de Entrega (Histórico)");
            System.out.println("6 - Atualizar Status da Entrega");
            System.out.println("7 - Listar Todas as Entregas com Cliente e Motorista");
            System.out.println("8 - Relatório: Total de Entregas por Motorista");
            System.out.println("9 - Relatório: Clientes com Maior Volume Entregue");
            System.out.println("10 - Relatório: Pedidos Pendentes por Estado");
            System.out.println("11 - Relatório: Entregas Atrasadas por Cidade");
            System.out.println("12 - Buscar Pedido por CPF/CNPJ do Cliente");
            System.out.println("13 - Cancelar Pedido");
            System.out.println("14 - Excluir Entrega (com validação)");
            System.out.println("15 - Excluir Cliente (com verificação de dependência)");
            System.out.println("16 - Excluir Motorista (com verificação de dependência)");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();



            switch (opcao){
                case 15 -> {
                    System.out.print("ID Cliente: ");
                    int ID = sc.nextInt();
                    cdao.excluir(ID);
                }
            }



        }while (opcao != 0);



    }
}