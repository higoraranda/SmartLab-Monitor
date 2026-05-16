package com.smartlab.monitor.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

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
    @Column(nullable = false, length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'DESLIGADO'")
    private StatusComputador status = StatusComputador.DESLIGADO;

    protected Computador() {
        // construtor protegido para o JPA
    }

    public Computador(String patrimonio, Laboratorio laboratorio) {
        this.patrimonio  = patrimonio;
        this.laboratorio = laboratorio;
    }

    public Long getId()                 { return id; }
    public String getPatrimonio()       { return patrimonio; }
    public Laboratorio getLaboratorio() { return laboratorio; }
    public StatusComputador getStatus() { return status; }

    public void setStatus(StatusComputador status) { this.status = status; }

    public void atualizarDados(String patrimonio, Laboratorio laboratorio) {
        this.patrimonio  = patrimonio;
        this.laboratorio = laboratorio;
    }
}
