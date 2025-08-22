package org.example;

import org.example.dao.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*try(Connection conn = Conexao.conectar()){
            if(conn != null){
                System.out.println("deu certo ");
            } else{
                System.out.println("nao deu ");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }*/
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
            System.out.println("16 - Listar clientes e seus pedidos");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();



            switch (opcao){

                case 1 -> {
                    System.out.print("ID Cliente: ");
                    int ID = sc.nextInt();
                    System.out.println("CPF/CNPJ Cliente: ");
                    int cC = sc.nextInt();
                    sc.nextLine();
                    System.out.println("NOME Cliente: ");
                    String nomeC = sc.nextLine();
                    System.out.println("ESTADO Cliente: ");
                    String estadoC = sc.nextLine();

                    cdao.inserir(ID,cC, nomeC,estadoC);
                }

                case 2 -> {
                    System.out.println("NOME Motora: ");
                    String nomeM = sc.nextLine();
                    System.out.println("VEICULO: ");
                    String estadoM = sc.nextLine();
                    System.out.println("CNH: ");
                    String cnhM = sc.nextLine();
                    System.out.println("CIDADE BASE: ");
                    String cid = sc.nextLine();

                    mdao.inserir(nomeM,estadoM,cnhM,cid);
                }


                case 3 -> {
                    System.out.println("Cliente (id): ");
                    int cliNome = sc.nextInt();
                    System.out.println("VOLUME: ");
                    Double volume = sc.nextDouble();
                    System.out.println("PESO --> KG: ");
                    Double kg = sc.nextDouble();
                    sc.nextLine();
                    pdao.inserir(cliNome,LocalDate.now(), volume,kg );
                }
                case 4 ->{
                    System.out.println("PEDIDO (id): ");
                    int pedidoID = sc.nextInt();
                    System.out.println("MOTORISTA: ");
                    int motora = sc.nextInt();
                    sc.nextLine();
                    edao.inserir(pedidoID,motora, "EM_ROTA" );
                }
                case 5 -> {
                    System.out.print("ID Evento: ");
                    int id = sc.nextInt();
                    System.out.print("ID Entrega: ");
                    int eid = sc.nextInt(); sc.nextLine();
                    System.out.print("Descrição: ");
                    String desc = sc.nextLine();
                    edao.relatorioEntrega();
                }
                case 6 -> {
                    System.out.print("ID Entrega: ");
                    int eid = sc.nextInt(); sc.nextLine();
                    System.out.print("Novo Status (EM_ROTA, ENTREGUE, ATRASADA): ");
                    String status = sc.nextLine();
                    edao.atualizarStatus(eid, status, LocalDate.now());
                }
                case 7 -> edao.ListarMC();
                case 8 -> mdao.relatorioTotalEntregas();
                case 9 -> cdao.relatorioVolumeMaior();
                case 10 -> pdao.relatorioPedidosPendentes();
                case 11 -> edao.relatorioEntregasAtrasadas();
                /*case 12 -> {
                    System.out.print("CPF/CNPJ Cliente: ");
                    int cpf = sc.nextInt();
                    pdao.relorioBuscarCpfCnpj(cpf);
                }*/
                case 13 -> {
                    System.out.print("ID Pedido: ");
                    int pid = sc.nextInt();
                    pdao.cancelarPedido(pid);
                }
                case 14 -> {
                    System.out.print("ID Entrega: ");
                    int eid = sc.nextInt();
                    edao.excluir(eid);
                }
                case 15 -> {
                    System.out.print("ID Cliente: ");
                    int cid = sc.nextInt();
                    cdao.excluirCascata(cid);
                }
                case 16 -> {
                    System.out.print("ID Motorista: ");
                    int mid = sc.nextInt();
                    mdao.excluirCascata(mid);
                }
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        sc.close();
    }
}