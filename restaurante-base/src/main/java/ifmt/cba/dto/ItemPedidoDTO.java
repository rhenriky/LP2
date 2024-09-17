package ifmt.cba.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ItemPedidoDTO {

    private int codigo;
    private PreparoProdutoDTO preparoProduto;
    private int quantidadePorcao;
    private int quantidade;
    private int pedidoCodigo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public PreparoProdutoDTO getPreparoProduto() {
        return preparoProduto;
    }

    public void setPreparoProduto(PreparoProdutoDTO preparoProduto) {
        this.preparoProduto = preparoProduto;
    }

    public int getQuantidadePorcao() {
        return quantidadePorcao;
    }

    public void setQuantidadePorcao(int quantidadePorcao) {
        this.quantidadePorcao = quantidadePorcao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getPedidoCodigo() {
        return pedidoCodigo;
    }

    public void setPedidoCodigo(int pedidoCodigo) {
        this.pedidoCodigo = pedidoCodigo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public String getNome() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getNome'");
    }
}
