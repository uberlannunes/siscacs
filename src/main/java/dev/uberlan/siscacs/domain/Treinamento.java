package dev.uberlan.siscacs.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "treinamentos")
class Treinamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "data_treinamento", nullable = false)
    private LocalDate dataTreinamento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "arma_id", nullable = false)
    private Arma arma;

    @Column(name = "quantidade_tiros", nullable = false)
    private int quantidadeTiros;

    @Column(nullable = false)
    private int pontuacao;

    private String observacao;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Treinamento() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDataTreinamento() {
        return dataTreinamento;
    }

    public void setDataTreinamento(LocalDate dataTreinamento) {
        this.dataTreinamento = dataTreinamento;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public int getQuantidadeTiros() {
        return quantidadeTiros;
    }

    public void setQuantidadeTiros(int quantidadeTiros) {
        this.quantidadeTiros = quantidadeTiros;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
