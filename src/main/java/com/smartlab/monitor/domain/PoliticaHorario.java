package com.smartlab.monitor.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "politicas_horario")
public class PoliticaHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "laboratorio_id")
    private Laboratorio laboratorio;

    @Column(nullable = false, length = 5)
    private String horarioLigamento;

    @Column(nullable = false, length = 5)
    private String horarioDesligamento;

    protected PoliticaHorario() {}

    public PoliticaHorario(Laboratorio laboratorio, String horarioLigamento, String horarioDesligamento) {
        this.laboratorio        = laboratorio;
        this.horarioLigamento   = horarioLigamento;
        this.horarioDesligamento = horarioDesligamento;
    }

    public Long getId()                    { return id; }
    public Laboratorio getLaboratorio()    { return laboratorio; }
    public String getHorarioLigamento()    { return horarioLigamento; }
    public String getHorarioDesligamento() { return horarioDesligamento; }

    public void atualizar(Laboratorio laboratorio, String horarioLigamento, String horarioDesligamento) {
        this.laboratorio        = laboratorio;
        this.horarioLigamento   = horarioLigamento;
        this.horarioDesligamento = horarioDesligamento;
    }
}
