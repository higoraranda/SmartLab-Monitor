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

    protected Computador() {
        // construtor protegido para o JPA
    }

    public Computador(String patrimonio, Laboratorio laboratorio) {
        this.patrimonio  = patrimonio;
        this.laboratorio = laboratorio;
    }

    public Long getId()              { return id; }
    public String getPatrimonio()    { return patrimonio; }
    public Laboratorio getLaboratorio() { return laboratorio; }

    public void atualizarDados(String patrimonio, Laboratorio laboratorio) {
        this.patrimonio  = patrimonio;
        this.laboratorio = laboratorio;
    }
}
