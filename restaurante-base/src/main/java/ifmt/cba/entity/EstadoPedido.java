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
@Table(name = "estado_pedido")
public class EstadoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // Alterado de 'codigo' para 'id' para manter consistência

    @Column(name = "descricao")
    private String descricao;

    // Implementando o método getId()
    public int getId() {
        return id;
    }

    // Implementando o método setId(), se necessário
    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;  // Alterado de 'codigo' para 'id'
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
        EstadoPedido other = (EstadoPedido) obj;
        if (id != other.id)  // Alterado de 'codigo' para 'id'
            return false;
        return true;
    }

    public String validar() {
        String retorno = "";

        if (this.descricao == null || this.descricao.length() < 3) {
            retorno += "Descrição inválida";
        }

        return retorno;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    // Implementando o método name() para retornar o nome do estado (supondo que a descrição seja o nome)
    public String name() {
        return this.descricao;
    }

    // Implementando o método valueOf() para criar um EstadoPedido a partir de uma string (simulação)
    public static EstadoPedido valueOf(String estado) {
        EstadoPedido estadoPedido = new EstadoPedido();
        estadoPedido.setDescricao(estado);
        return estadoPedido;
    }

    public int getCodigo() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getCodigo'");
    }
}
