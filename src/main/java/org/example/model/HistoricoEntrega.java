package org.example.model;

import java.time.LocalDate;

public class HistoricoEntrega {
    private int id;
    private int entregaId;
    private LocalDate dataEvento;
    private String descricao;

    public HistoricoEntrega(int id, int entregaId, LocalDate dataEvento, String descricao) {
        this.id = id;
        this.entregaId = entregaId;
        this.dataEvento = dataEvento;
        this.descricao = descricao;
    }
    public HistoricoEntrega(int entregaId, LocalDate dataEvento, String descricao) {
        this.entregaId = entregaId;
        this.dataEvento = dataEvento;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEntregaId() {
        return entregaId;
    }

    public void setEntregaId(int entregaId) {
        this.entregaId = entregaId;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
