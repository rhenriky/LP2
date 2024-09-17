package ifmt.cba.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import ifmt.cba.dto.ProdutoDTO;
import ifmt.cba.dto.RegistroEstoqueDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "registro_estoque")
public class RegistroEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(name = "data_movimento")
    private java.time.LocalDateTime dataMovimento;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "produto_codigo")
    private int produtoCodigo;

    // Getters e Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public java.time.LocalDateTime getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(java.time.LocalDateTime dataMovimento) {
        this.dataMovimento = dataMovimento;
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

    // Método para converter de DTO para Entity
    public static RegistroEstoque fromDTO(RegistroEstoqueDTO dto) {
        RegistroEstoque entity = new RegistroEstoque();
        entity.setCodigo(dto.getCodigo());
        entity.setDataMovimento(dto.getData().atStartOfDay()); // Converter LocalDate para LocalDateTime
        entity.setQuantidade(dto.getQuantidade());
        entity.setProdutoCodigo(dto.getProduto().getCodigo()); // Assumindo que ProdutoDTO tem getCodigo()
        return entity;
    }

    // Método para converter de Entity para DTO
    public RegistroEstoqueDTO toDTO() {
        RegistroEstoqueDTO dto = new RegistroEstoqueDTO();
        dto.setCodigo(this.codigo);
        dto.setData(this.dataMovimento.toLocalDate()); // Converter LocalDateTime para LocalDate
        dto.setQuantidade(this.quantidade);
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setCodigo(this.produtoCodigo); // Assumindo que ProdutoDTO tem setCodigo()
        dto.setProduto(produtoDTO);
        return dto;
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
        RegistroEstoque other = (RegistroEstoque) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }

    public String validar() {
        String retorno = "";

        if (this.dataMovimento == null) {
            retorno += "Data do movimento inválida";
        }

        if (this.quantidade <= 0) {
            retorno += "Quantidade deve ser maior que zero";
        }

        if (this.produtoCodigo <= 0) {
            retorno += "Produto inválido";
        }

        return retorno;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
