package ifmt.cba.execucao;

import ifmt.cba.dto.PreparoProdutoDTO;
import ifmt.cba.negocio.PreparoProdutoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.PreparoProdutoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirPreparoProduto {
    public static void main(String[] args) {
        try {
            PreparoProdutoDAO preparoProdutoDAO = new PreparoProdutoDAO(FabricaEntityManager.getEntityManagerProducao());
            PreparoProdutoNegocio preparoProdutoNegocio = new PreparoProdutoNegocio(preparoProdutoDAO);

            PreparoProdutoDTO preparoProdutoDTO = new PreparoProdutoDTO();
            preparoProdutoDTO.setDescricao("Preparação Básica");
            preparoProdutoNegocio.inserir(preparoProdutoDTO);

            preparoProdutoDTO = new PreparoProdutoDTO();
            preparoProdutoDTO.setDescricao("Preparação Avançada");
            preparoProdutoNegocio.inserir(preparoProdutoDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
