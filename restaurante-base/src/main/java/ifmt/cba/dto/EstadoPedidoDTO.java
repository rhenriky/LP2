package ifmt.cba.dto;

import ifmt.cba.entity.EstadoPedido;

public class EstadoPedidoDTO {

    private Integer codigo;  // Adicionando o atributo código
    private String estado;   // O estado representado como String
    private String descricao;

    // Construtor vazio
    public EstadoPedidoDTO() {
    }

    // Construtor com EstadoPedido (preenche código, estado e descrição)
    public EstadoPedidoDTO(EstadoPedido estado) {
        if (estado != null) {
            this.codigo = estado.getCodigo(); // Supondo que EstadoPedido tenha um método getCodigo
            this.estado = estado.name(); // Supondo que EstadoPedido seja um enum
            this.descricao = estado.getDescricao();
        } else {
            throw new IllegalArgumentException("Estado não pode ser nulo");
        }
    }

    // Construtor com parâmetros para código, estado e descrição
    public EstadoPedidoDTO(Integer codigo, String estado, String descricao) {
        this.codigo = codigo;
        this.estado = estado;
        this.descricao = descricao;
    }

    // Getters e Setters
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Método para converter de DTO para Enum
    public EstadoPedido toEnum() {
        return EstadoPedido.valueOf(this.estado);
    }

    @Override
    public String toString() {
        return "EstadoPedidoDTO{" +
                "codigo=" + codigo +
                ", estado='" + estado + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
