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
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(name = "cliente_codigo")
    private int clienteCodigo;

    @Column(name = "data_pedido")
    private java.time.LocalDateTime dataPedido;

    @Column(name = "estado_codigo")
    private int estadoCodigo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getClienteCodigo() {
        return clienteCodigo;
    }

    public void setClienteCodigo(int clienteCodigo) {
        this.clienteCodigo = clienteCodigo;
    }

    public java.time.LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(java.time.LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public int getEstadoCodigo() {
        return estadoCodigo;
    }

    public void setEstadoCodigo(int estadoCodigo) {
        this.estadoCodigo = estadoCodigo;
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
        Pedido other = (Pedido) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }

    public String validar() {
        String retorno = "";

        if (this.dataPedido == null) {
            retorno += "Data do pedido inválida";
        }

        if (this.clienteCodigo <= 0) {
            retorno += "Cliente inválido";
        }

        if (this.estadoCodigo <= 0) {
            retorno += "Estado inválido";
        }

        return retorno;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
