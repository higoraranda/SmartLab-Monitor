package com.smartlab.monitor.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_desligamento")
public class HistoricoDesligamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String patrimonio;

    @Column(nullable = false, length = 100)
    private String laboratorioNome;

    @Column(nullable = false, length = 100)
    private String predioNome;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MotivoDesligamento motivo;

    protected HistoricoDesligamento() {}

    public HistoricoDesligamento(String patrimonio, String laboratorioNome,
                                  String predioNome, MotivoDesligamento motivo) {
        this.patrimonio      = patrimonio;
        this.laboratorioNome = laboratorioNome;
        this.predioNome      = predioNome;
        this.motivo          = motivo;
        this.dataHora        = LocalDateTime.now();
    }

    public Long getId()                    { return id; }
    public String getPatrimonio()          { return patrimonio; }
    public String getLaboratorioNome()     { return laboratorioNome; }
    public String getPredioNome()          { return predioNome; }
    public LocalDateTime getDataHora()     { return dataHora; }
    public MotivoDesligamento getMotivo()  { return motivo; }
}
