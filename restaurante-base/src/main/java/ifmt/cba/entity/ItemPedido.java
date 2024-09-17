package ifmt.cba.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


import java.io.Serializable;

@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @ManyToOne
    @JoinColumn(name = "preparo_produto_codigo", nullable = false)
    private PreparoProduto preparoProduto;

    @Column(name = "quantidade_porcao", nullable = false)
    private int quantidadePorcao;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "pedido_codigo", nullable = false)
    private Pedido pedido;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public PreparoProduto getPreparoProduto() {
        return preparoProduto;
    }

    public void setPreparoProduto(PreparoProduto preparoProduto) {
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "codigo=" + codigo +
                ", preparoProduto=" + preparoProduto +
                ", quantidadePorcao=" + quantidadePorcao +
                ", quantidade=" + quantidade +
                ", pedido=" + pedido +
                '}';
    }
}
