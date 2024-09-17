package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.EstadoOrdemProducao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class EstadoOrdemProducaoDAO extends DAO<EstadoOrdemProducao> {

    public EstadoOrdemProducaoDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<EstadoOrdemProducao> buscarTodos() throws PersistenciaException {
        List<EstadoOrdemProducao> listaEstadoOrdemProducao;
        try {
            Query query = this.entityManager.createQuery("SELECT e FROM EstadoOrdemProducao e ORDER BY e.descricao");
            listaEstadoOrdemProducao = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todos os estados de ordem de produção - " + ex.getMessage());
        }
        return listaEstadoOrdemProducao;
    }

    public EstadoOrdemProducao buscarPorCodigo(int codigo) throws PersistenciaException {
        EstadoOrdemProducao estadoOrdemProducao;
        try {
            Query query = this.entityManager.createQuery("SELECT e FROM EstadoOrdemProducao e WHERE e.codigo = :codigo");
            query.setParameter("codigo", codigo);
            estadoOrdemProducao = (EstadoOrdemProducao) query.getSingleResult();
        } catch (NoResultException e) {
            estadoOrdemProducao = null; // Retorna null se nenhum estado for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do estado de ordem de produção pelo código - " + ex.getMessage());
        }
        return estadoOrdemProducao;
    }

    // Método para inserir um novo estado de ordem de produção
    public void inserir(EstadoOrdemProducao estadoOrdemProducao) throws PersistenciaException {
        try {
            this.entityManager.getTransaction().begin(); // Inicia a transação
            this.entityManager.persist(estadoOrdemProducao);
            this.entityManager.getTransaction().commit(); // Comita a transação
        } catch (Exception ex) {
            if (this.entityManager.getTransaction().isActive()) {
                this.entityManager.getTransaction().rollback(); // Realiza o rollback em caso de erro
            }
            throw new PersistenciaException("Erro ao inserir o estado de ordem de produção - " + ex.getMessage());
        }
    }

    // Método para excluir um estado de ordem de produção
    public void excluir(EstadoOrdemProducao estadoOrdemProducao) throws PersistenciaException {
        try {
            this.entityManager.getTransaction().begin(); // Inicia a transação
            this.entityManager.remove(this.entityManager.contains(estadoOrdemProducao) ? estadoOrdemProducao : this.entityManager.merge(estadoOrdemProducao));
            this.entityManager.getTransaction().commit(); // Comita a transação
        } catch (Exception ex) {
            if (this.entityManager.getTransaction().isActive()) {
                this.entityManager.getTransaction().rollback(); // Realiza o rollback em caso de erro
            }
            throw new PersistenciaException("Erro ao excluir o estado de ordem de produção - " + ex.getMessage());
        }
    }
}
