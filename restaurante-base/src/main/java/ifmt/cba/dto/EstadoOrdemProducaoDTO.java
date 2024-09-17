package ifmt.cba.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public enum EstadoOrdemProducaoDTO {
    
    REGISTRADA("Registrada"),
    PROCESSADA("Processada");

    private String descricao;

    // Construtor para definir a descrição
    EstadoOrdemProducaoDTO(String descricao) {
        this.descricao = descricao;
    }

    // Getter para a descrição
    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        // Usando ToStringBuilder para seguir o estilo JSON
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
