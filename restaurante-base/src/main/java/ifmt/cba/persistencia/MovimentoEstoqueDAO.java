package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.MovimentoEstoque;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class MovimentoEstoqueDAO extends DAO<MovimentoEstoque> {

    public MovimentoEstoqueDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<MovimentoEstoque> buscarTodos() throws PersistenciaException {
        List<MovimentoEstoque> listaMovimentoEstoque;
        try {
            Query query = this.entityManager.createQuery("SELECT m FROM MovimentoEstoque m ORDER BY m.codigo");
            listaMovimentoEstoque = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todos os movimentos de estoque - " + ex.getMessage());
        }
        return listaMovimentoEstoque;
    }

    public MovimentoEstoque buscarPorCodigo(int codigo) throws PersistenciaException {
        MovimentoEstoque movimentoEstoque;
        try {
            Query query = this.entityManager.createQuery("SELECT m FROM MovimentoEstoque m WHERE m.codigo = :codigo");
            query.setParameter("codigo", codigo);
            movimentoEstoque = (MovimentoEstoque) query.getSingleResult();
        } catch (NoResultException e) {
            movimentoEstoque = null; // Retorna null se nenhum movimento for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do movimento de estoque pelo código - " + ex.getMessage());
        }
        return movimentoEstoque;
    }

    public void inserir(MovimentoEstoque movimentoEstoque) throws PersistenciaException {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(movimentoEstoque);
            this.entityManager.getTransaction().commit();
        } catch (Exception ex) {
            this.entityManager.getTransaction().rollback();
            throw new PersistenciaException("Erro ao inserir o movimento de estoque - " + ex.getMessage());
        }
    }
}
