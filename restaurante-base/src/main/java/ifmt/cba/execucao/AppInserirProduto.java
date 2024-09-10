package ifmt.cba.execucao;

import ifmt.cba.dto.ProdutoDTO;
import ifmt.cba.negocio.ProdutoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.ProdutoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirProduto {
    public static void main(String[] args) {
        try {
            ProdutoDAO produtoDAO = new ProdutoDAO(FabricaEntityManager.getEntityManagerProducao());
            ProdutoNegocio produtoNegocio = new ProdutoNegocio(produtoDAO);

            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setNome("Produto A");
            produtoDTO.setPreco(10.00f);
            produtoNegocio.inserir(produtoDTO);

            produtoDTO = new ProdutoDTO();
            produtoDTO.setNome("Produto B");
            produtoDTO.setPreco(20.00f);
            produtoNegocio.inserir(produtoDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
