package ifmt.cba.dto;

public class ClienteDTO {

    private int codigo;
    private String nome;
    private String RG;
    private String CPF;
    private String telefone;
    private String logradouro;
    private String numero;
    private BairroDTO bairro;
    private String pontoReferencia;

    // Construtor padrão
    public ClienteDTO() {
    }

    // Construtor com parâmetros
    public ClienteDTO(int codigo, String nome, String CPF) {
        this.codigo = codigo;
        this.nome = nome;
        this.CPF = CPF;
    }

    // Getters e Setters
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

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BairroDTO getBairro() {
        return bairro;
    }

    public void setBairro(BairroDTO bairro) {
        this.bairro = bairro;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    @Override
    public String toString() {
        return "ClienteDTO [codigo=" + codigo + ", nome=" + nome + ", RG=" + RG + ", CPF=" + CPF + ", telefone=" + telefone
                + ", logradouro=" + logradouro + ", numero=" + numero + ", bairro=" + bairro + ", pontoReferencia="
                + pontoReferencia + "]";
    }
}
