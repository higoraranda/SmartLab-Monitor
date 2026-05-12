package com.smartlab.monitor.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "computadores")
public class Computador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String patrimonio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "laboratorio_id")
    @JsonBackReference("lab-computadores")
    private Laboratorio laboratorio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusComputador status = StatusComputador.DESLIGADO;

    // instante em que o mouse se moveu pela última vez (null = nunca ligado)
    private LocalDateTime ultimaAtividade;

    // minutos sem atividade para ir de EM_USO → OCIOSO
    @Column(nullable = false)
    private int tempoInativaMinutos = 10;

    protected Computador() {}

    public Computador(String patrimonio, Laboratorio laboratorio) {
        this.patrimonio  = patrimonio;
        this.laboratorio = laboratorio;
    }

    public Long getId()                       { return id; }
    public String getPatrimonio()             { return patrimonio; }
    public Laboratorio getLaboratorio()       { return laboratorio; }
    public StatusComputador getStatus()       { return status; }
    public LocalDateTime getUltimaAtividade() { return ultimaAtividade; }
    public int getTempoInativaMinutos()       { return tempoInativaMinutos; }

    public void atualizarDados(String patrimonio, Laboratorio laboratorio) {
        this.patrimonio  = patrimonio;
        this.laboratorio = laboratorio;
    }

    public void registrarAtividade() {
        this.status          = StatusComputador.EM_USO;
        this.ultimaAtividade = LocalDateTime.now();
    }

    public void desligar() {
        this.status = StatusComputador.DESLIGADO;
    }

    public void marcarOcioso() {
        this.status = StatusComputador.OCIOSO;
    }

    public void atualizarTempo(int minutos) {
        this.tempoInativaMinutos = minutos;
    }
}
