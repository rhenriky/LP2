package ifmt.cba.persistencia;

import ifmt.cba.entity.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class ProdutoDAO extends DAO<Produto> {

    public ProdutoDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<Produto> buscarTodosOrdenadosPorNome() throws PersistenciaException {
        try {
            Query query = this.entityManager.createQuery("SELECT p FROM Produto p ORDER BY p.nome");
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Erro ao buscar produtos ordenados por nome: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<Produto> buscarProdutosComEstoqueBaixo() throws PersistenciaException {
        try {
            Query query = this.entityManager.createQuery("SELECT p FROM Produto p WHERE p.estoque < p.estoqueMinimo");
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Erro ao buscar produtos com estoque baixo: " + e.getMessage());
        }
    }
}
