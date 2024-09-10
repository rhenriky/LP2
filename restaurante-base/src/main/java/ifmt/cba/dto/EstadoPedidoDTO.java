
package ifmt.cba.dto;

public class EstadoPedidoDTO {

    private String estado;

    // Construtor padrão
    public EstadoPedidoDTO() {
    }

    // Construtor com parâmetro
    public EstadoPedidoDTO(EstadoPedido estado) {
        this.estado = estado.name();
    }

    // Getter e Setter
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "EstadoPedidoDTO{" +
                "estado='" + estado + '\'' +
                '}';
    }

    // Método para converter de DTO para Enum
    public EstadoPedido toEnum() {
        return EstadoPedido.valueOf(this.estado);
    }
}
