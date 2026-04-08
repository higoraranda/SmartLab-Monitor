package com.smartlab.monitor.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "predios")
public class Predio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @OneToMany(mappedBy = "predio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("predio-laboratorios")
    private List<Laboratorio> laboratorios = new ArrayList<>();

    protected Predio() {
        // construtor protegido para o JPA
    }

    public Predio(String nome) {
        this.nome = nome;
    }

    public Long getId()                        { return id; }
    public String getNome()                    { return nome; }
    public List<Laboratorio> getLaboratorios() { return laboratorios; }

    public void atualizarNome(String nome) {
        this.nome = nome;
    }
}
