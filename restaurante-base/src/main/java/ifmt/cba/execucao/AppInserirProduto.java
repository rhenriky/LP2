package ifmt.cba.execucao;

import ifmt.cba.dto.ProdutoDTO;
import ifmt.cba.dto.GrupoAlimentoDTO;
import ifmt.cba.negocio.ProdutoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.ProdutoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

import java.util.List;

public class AppInserirProduto {
    public static void main(String[] args) {
        try {
            ProdutoDAO produtoDAO = new ProdutoDAO(FabricaEntityManager.getEntityManagerProducao());
            ProdutoNegocio produtoNegocio = new ProdutoNegocio(produtoDAO);

            // Criação e inserção dos produtos
            ProdutoDTO produtoDTO1 = new ProdutoDTO();
            produtoDTO1.setNome("Produto A");
            produtoDTO1.setCustoUnidade(10.00f);
            produtoDTO1.setValorEnergetico(150);
            produtoDTO1.setEstoque(100);
            produtoDTO1.setEstoqueMinimo(10);
            GrupoAlimentoDTO grupoAlimentoDTO1 = new GrupoAlimentoDTO();
            grupoAlimentoDTO1.setCodigo(1);
            produtoDTO1.setGrupoAlimento(grupoAlimentoDTO1);
            produtoNegocio.inserir(produtoDTO1);

            ProdutoDTO produtoDTO2 = new ProdutoDTO();
            produtoDTO2.setNome("Produto B");
            produtoDTO2.setCustoUnidade(20.00f);
            produtoDTO2.setValorEnergetico(200);
            produtoDTO2.setEstoque(50);
            produtoDTO2.setEstoqueMinimo(5);
            GrupoAlimentoDTO grupoAlimentoDTO2 = new GrupoAlimentoDTO();
            grupoAlimentoDTO2.setCodigo(2);
            produtoDTO2.setGrupoAlimento(grupoAlimentoDTO2);
            produtoNegocio.inserir(produtoDTO2);

            // Listar todos os produtos ordenados por nome
            System.out.println("Listando todos os produtos em ordem crescente de nome:");
            for (ProdutoDTO produto : produtoNegocio.buscarTodosOrdenadosPorNome()) {
                System.out.println(produto.getNome());
            }

            // Listar produtos com estoque abaixo do mínimo
            System.out.println("\nListando produtos com estoque abaixo do mínimo:");
            List<ProdutoDTO> produtosEstoqueBaixo = produtoNegocio.buscarProdutosComEstoqueBaixo();
            if (produtosEstoqueBaixo.isEmpty()) {
                System.out.println("Nenhum produto com estoque abaixo do mínimo.");
            } else {
                for (ProdutoDTO produto : produtosEstoqueBaixo) {
                    System.out.println("Nome: " + produto.getNome() +
                            ", Estoque: " + produto.getEstoque() +
                            ", Estoque Mínimo: " + produto.getEstoqueMinimo());
                }
            }

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
