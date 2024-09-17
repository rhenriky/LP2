package ifmt.cba.execucao;

import ifmt.cba.entity.PreparoProduto;
import ifmt.cba.negocio.PreparoProdutoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.PreparoProdutoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirPreparoProduto {
    public static void main(String[] args) {
        try {
            // Criação do DAO e do Negócio
            PreparoProdutoDAO preparoProdutoDAO = new PreparoProdutoDAO(FabricaEntityManager.getEntityManagerProducao());
            PreparoProdutoNegocio preparoProdutoNegocio = new PreparoProdutoNegocio(preparoProdutoDAO);

            // Criar e configurar o primeiro PreparoProduto
            PreparoProduto preparoProduto1 = new PreparoProduto();
            preparoProduto1.setDescricao("Preparação Comum ");
            preparoProduto1.setTempoPreparo(30);

            // Validação do primeiro preparo
            String validacao1 = preparoProduto1.validar();
            if (!validacao1.isEmpty()) {
                throw new NegocioException("Erro ao validar o PreparoProduto1: " + validacao1);
            }

            // Inserir o primeiro preparo de produto
            preparoProdutoNegocio.inserir(preparoProduto1);

            // Criar e configurar o segundo PreparoProduto
            PreparoProduto preparoProduto2 = new PreparoProduto();
            preparoProduto2.setDescricao("Preparação Execultivo");
            preparoProduto2.setTempoPreparo(60);

            // Validação do segundo preparo
            String validacao2 = preparoProduto2.validar();
            if (!validacao2.isEmpty()) {
                throw new NegocioException("Erro ao validar o PreparoProduto2: " + validacao2);
            }

            // Inserir o segundo preparo de produto
            preparoProdutoNegocio.inserir(preparoProduto2);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
