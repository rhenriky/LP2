package ifmt.cba.execucao;

import java.util.List;

import ifmt.cba.entity.Produto;
import ifmt.cba.persistencia.ProdutoDAO;
import ifmt.cba.persistencia.PersistenciaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AppBuscarProdutos {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            // Cria o EntityManager a partir da unidade de persistência
            emf = Persistence.createEntityManagerFactory("restaurante_producao");
            em = emf.createEntityManager();

            // Cria uma instância do ProdutoDAO
            ProdutoDAO produtoDAO = new ProdutoDAO(em);
            
            // Busca todos os produtos ordenados por nome
            List<Produto> listaProdutos = produtoDAO.buscarTodosOrdenadosPorNome();
            
            // Verifica se a lista está vazia
            if (listaProdutos.isEmpty()) {
                System.out.println("Nenhum produto encontrado.");
            } else {
                // Itera sobre a lista e imprime os produtos
                System.out.println("Lista de produtos ordenados por nome:");
                for (Produto produto : listaProdutos) {
                    System.out.println("Nome: " + produto.getNome() + " | Estoque: " + produto.getEstoque() + " | Estoque Mínimo: " + produto.getEstoqueMinimo());
                }
            }
        } catch (PersistenciaException e) {
            System.err.println("Erro ao buscar produtos: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
