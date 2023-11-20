package dev.uberlan.siscacs.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.util.UUID;

@Builder
@Entity
@Table(name = "municoes")
class Municao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "arma_id", nullable = false)
    private Arma arma;

    @Column(nullable = false)
    @PositiveOrZero
    private int quantidade;

    public Municao() {
    }

    public Municao(Arma arma, int quantidade) {
        this.arma = arma;
        this.quantidade = quantidade;
    }

    public Municao(UUID id, Arma arma, int quantidade) {
        this.id = id;
        this.arma = arma;
        this.quantidade = quantidade;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
