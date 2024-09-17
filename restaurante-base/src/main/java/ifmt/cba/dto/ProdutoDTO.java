package ifmt.cba.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProdutoDTO {
    
    private int codigo;
    private String nome;
    private float custounidade;
    private int valorEnergetico;
    private int estoque;
    private int estoqueMinimo;
    private GrupoAlimentoDTO grupoAlimento;
   

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

    public float getcustounidade() {
        return custounidade;
    }

    public void setCustoUnidade(float custounidade) {
        this.custounidade = custounidade;
    }

    public int getValorEnergetico() {
        return valorEnergetico;
    }

    public void setValorEnergetico(int valorEnergetico) {
        this.valorEnergetico = valorEnergetico;
    }

    public GrupoAlimentoDTO getGrupoAlimentar() {
        return grupoAlimento;
    }

    public void setGrupoAlimento(GrupoAlimentoDTO grupoAlimento) {
        this.grupoAlimento = grupoAlimento;
    }

    public int getEstoque() {
        return estoque;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
