package ifmt.cba.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private float custounidade;

    @Column(nullable = false)
    private int valorEnergetico;

    @Column(nullable = false)
    private int estoque;

    @Column(nullable = false)
    private int estoqueMinimo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "grupo_alimento_id", 
        nullable = false

    )
    private GrupoAlimento grupoAlimento;

    // Getters e setters

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getCustoUnidade() {
        return custounidade;
    }

    public void setCustounidade(float custounidade) {
        this.custounidade = custounidade;
    }

    public int getValorEnergetico() {
        return valorEnergetico;
    }

    public void setValorEnergetico(int valorEnergetico) {
        this.valorEnergetico = valorEnergetico;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public GrupoAlimento getGrupoAlimento() {
        return grupoAlimento;
    }

    public void setGrupoAlimento(GrupoAlimento grupoAlimento) {
        this.grupoAlimento = grupoAlimento;
    }
}