package ifmt.cba.execucao;

import ifmt.cba.entity.MovimentoEstoque;
import ifmt.cba.dto.MovimentoEstoqueDTO;
import ifmt.cba.negocio.MovimentoEstoqueNegocio;
import ifmt.cba.persistencia.PersistenciaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AppInserirMovimentoEstoque {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager em = emf.createEntityManager();

        try {
            MovimentoEstoqueNegocio movimentoEstoqueNegocio = new MovimentoEstoqueNegocio(em);

            MovimentoEstoque movimentoEstoque = new MovimentoEstoque();
            movimentoEstoque.setTipoMovimento(MovimentoEstoqueDTO.PRODUCAO);
            movimentoEstoque.setDescricao("Movimento de produção de itens no estoque");

            movimentoEstoqueNegocio.inserir(movimentoEstoque);

            System.out.println("Movimento de estoque inserido com sucesso!");

        } catch (PersistenciaException e) {
            System.err.println("Erro: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
    }
}
