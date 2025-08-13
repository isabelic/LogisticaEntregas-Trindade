package org.example.model;

public class Cliente {
    int id;
   String endereco, cidade, estado, cpf_cnpj, nome;

   public Cliente(int id,  String nome, String endereco, String cidade, String estado, String cpf_cnpj){
       this.id = id;
       this.nome = nome;
       this.endereco = endereco;
       this.cidade = cidade;
       this.estado = estado;
      this.cpf_cnpj = cpf_cnpj;
   }

    public Cliente( String nome, String endereco, String cidade, String estado, String cpf_cnpj){
        this.nome = nome;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.cpf_cnpj = cpf_cnpj;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
