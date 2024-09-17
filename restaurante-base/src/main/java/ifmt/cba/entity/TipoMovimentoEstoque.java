package ifmt.cba.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimento_estoque")
public class TipoMovimentoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(name = "tipo_movimento", nullable = false)
    private String tipoMovimento;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @Column(name = "produto_codigo", nullable = false)
    private int produtoCodigo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getProdutoCodigo() {
        return produtoCodigo;
    }

    public void setProdutoCodigo(int produtoCodigo) {
        this.produtoCodigo = produtoCodigo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codigo;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TipoMovimentoEstoque other = (TipoMovimentoEstoque) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }

    public String validar() {
        StringBuilder retorno = new StringBuilder();

        if (this.tipoMovimento == null || this.tipoMovimento.isEmpty()) {
            retorno.append("Tipo de movimento é obrigatório. ");
        } else if (this.tipoMovimento.length() < 3) {
            retorno.append("Tipo de movimento deve ter pelo menos 3 caracteres. ");
        }

        if (this.quantidade <= 0) {
            retorno.append("Quantidade deve ser maior que zero. ");
        }

        if (this.produtoCodigo <= 0) {
            retorno.append("Código do produto deve ser maior que zero. ");
        }

        return retorno.toString().trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
