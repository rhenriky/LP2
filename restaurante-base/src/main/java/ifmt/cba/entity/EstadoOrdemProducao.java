package ifmt.cba.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordem_producao")
public class EstadoOrdemProducao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;

    // Relacionamento com EstadoOrdemProducao
    @ManyToOne(cascade = CascadeType.ALL) // Comportamento em cascata
    @JoinColumn(name = "estado_codigo", referencedColumnName = "codigo")
    private EstadoOrdemProducao estadoOrdemProducao;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public EstadoOrdemProducao getEstadoOrdemProducao() {
        return estadoOrdemProducao;
    }

    public void setEstadoOrdemProducao(EstadoOrdemProducao estadoOrdemProducao) {
        this.estadoOrdemProducao = estadoOrdemProducao;
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
        EstadoOrdemProducao other = (EstadoOrdemProducao) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }

    public String validar() {
        String retorno = "";

        if (this.descricao == null || this.descricao.length() < 3) {
            retorno += "Descrição inválida.";
        }

        if (this.dataRegistro == null) {
            retorno += "Data de registro inválida.";
        }

        if (this.estadoOrdemProducao == null || this.estadoOrdemProducao.getCodigo() <= 0) {
            retorno += "Estado inválido.";
        }

        return retorno;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
