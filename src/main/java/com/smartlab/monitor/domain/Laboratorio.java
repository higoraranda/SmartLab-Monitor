package com.smartlab.monitor.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "laboratorios")
public class Laboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "predio_id")
    @JsonBackReference("predio-laboratorios")
    private Predio predio;

    @OneToMany(mappedBy = "laboratorio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("lab-computadores")
    private List<Computador> computadores = new ArrayList<>();

    protected Laboratorio() {
        // construtor protegido para o JPA
    }

    public Laboratorio(String nome, Predio predio) {
        this.nome   = nome;
        this.predio = predio;
    }

    public Long getId()                       { return id; }
    public String getNome()                   { return nome; }
    public Predio getPredio()                 { return predio; }
    public List<Computador> getComputadores() { return computadores; }

    public void atualizarDados(String nome, Predio predio) {
        this.nome   = nome;
        this.predio = predio;
    }
}
