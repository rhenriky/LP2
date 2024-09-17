package ifmt.cba.execucao;

import ifmt.cba.dto.ProdutoDTO;
import ifmt.cba.dto.RegistroEstoqueDTO;
import ifmt.cba.negocio.RegistroEstoqueNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.RegistroEstoqueDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirRegistroEstoque {
    public static void main(String[] args) {
        try {
            // Criação do DAO e do Negócio
            RegistroEstoqueDAO registroEstoqueDAO = new RegistroEstoqueDAO(FabricaEntityManager.getEntityManagerProducao());
            RegistroEstoqueNegocio registroEstoqueNegocio = new RegistroEstoqueNegocio(registroEstoqueDAO);

            // Criar e configurar o primeiro RegistroEstoqueDTO
            RegistroEstoqueDTO registroEstoqueDTO1 = new RegistroEstoqueDTO();
            registroEstoqueDTO1.setData(java.time.LocalDate.now());
            registroEstoqueDTO1.setQuantidade(100);
            ProdutoDTO produtoDTO1 = new ProdutoDTO();
            produtoDTO1.setCodigo(1); // Defina o código do produto conforme necessário
            registroEstoqueDTO1.setProduto(produtoDTO1);

            // Inserir o primeiro registro de estoque
            registroEstoqueNegocio.inserir(registroEstoqueDTO1);

            // Criar e configurar o segundo RegistroEstoqueDTO
            RegistroEstoqueDTO registroEstoqueDTO2 = new RegistroEstoqueDTO();
            registroEstoqueDTO2.setData(java.time.LocalDate.now().plusDays(1));
            registroEstoqueDTO2.setQuantidade(150);
            ProdutoDTO produtoDTO2 = new ProdutoDTO();
            produtoDTO2.setCodigo(2); // Defina o código do produto conforme necessário
            registroEstoqueDTO2.setProduto(produtoDTO2);

            // Inserir o segundo registro de estoque
            registroEstoqueNegocio.inserir(registroEstoqueDTO2);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
