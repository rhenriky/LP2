package ifmt.cba.entity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
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

@Entity
@Table(name = "pedido")
public class Pedido {

    private static final int clienteCodigo = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_codigo", nullable = false)
    private Cliente cliente;

    @Column(name = "data_pedido", nullable = false)
    private LocalDate dataPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_codigo", nullable = false)
    private EstadoPedido estado;

    // Getters e setters

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    // Equals e HashCode baseados no código
    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Pedido other = (Pedido) obj;
        return codigo == other.codigo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public void setDataPedido(Date date) {
    if (date != null) {
        this.dataPedido = date.toInstant()
                              .atZone(ZoneId.systemDefault())
                              .toLocalDate();
    } else {
        this.dataPedido = null;
    }
    }

    @SuppressWarnings("static-access")
    public String validar() {
        StringBuilder erros = new StringBuilder();
    
        // Valida a data do pedido
        if (this.dataPedido == null) {
            erros.append("Data do pedido não pode ser nula. ");
        }
    
        // Valida o cliente
        if (this.clienteCodigo <= 0) {
            erros.append("Código do cliente inválido. ");
        }
    
        // Valida o estado do pedido
        if (this.getCodigo() <= 0) {
            erros.append("Código de estado inválido. ");
        }
    
        // Se houver algum erro, retorna a mensagem completa; caso contrário, retorna uma string vazia
        return erros.toString();
    }
    
}