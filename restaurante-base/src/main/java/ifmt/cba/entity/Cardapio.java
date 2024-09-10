
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
    @Table(name = "cardapio")
    public class Cardapio {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int codigo;
    
        @Column(name = "nome")
        private String nome;
    
        public int getCodigo() {
            return codigo;
        }
    
        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }
    
        public String getNome() {
            return nome;
        }
    
        public void setNome(String nome) {
            this.nome = nome;
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
            Cardapio other = (Cardapio) obj;
            if (codigo != other.codigo)
                return false;
            return true;
        }
    
        public String validar() {
            String retorno = "";
    
            if (this.nome == null || this.nome.length() < 3) {
                retorno += "Nome invalido";
            }
    
            return retorno;
        }
    
        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
        }
    }
    