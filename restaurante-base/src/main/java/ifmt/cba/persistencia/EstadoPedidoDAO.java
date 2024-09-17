package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.EstadoPedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class EstadoPedidoDAO extends DAO<EstadoPedido> {

    public EstadoPedidoDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<EstadoPedido> buscarTodosOrdenadosPorNome() throws PersistenciaException {
        List<EstadoPedido> listaEstadoPedido;
        try {
            Query query = this.entityManager.createQuery("SELECT e FROM EstadoPedido e ORDER BY e.nome");
            listaEstadoPedido = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos estados de pedido ordenados por nome - " + ex.getMessage());
        }
        return listaEstadoPedido;
    }

    @SuppressWarnings("unchecked")
    public List<EstadoPedido> buscarPorEstado(String estado) throws PersistenciaException {
        List<EstadoPedido> listaEstadoPedido;
        try {
            Query query = this.entityManager.createQuery("SELECT e FROM EstadoPedido e WHERE e.estado = :estado ORDER BY e.nome");
            query.setParameter("estado", estado);
            listaEstadoPedido = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos estados de pedido por estado - " + ex.getMessage());
        }
        return listaEstadoPedido;
    }

    public EstadoPedido buscarPorCodigo(int codigo) throws PersistenciaException {
        EstadoPedido estadoPedido;
        try {
            Query query = this.entityManager.createQuery("SELECT e FROM EstadoPedido e WHERE e.codigo = :codigo");
            query.setParameter("codigo", codigo);
            estadoPedido = (EstadoPedido) query.getSingleResult();
        } catch (NoResultException e) {
            estadoPedido = null; // Retorna null se nenhum estado de pedido for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do estado de pedido pelo código - " + ex.getMessage());
        }
        return estadoPedido;
    }

    @SuppressWarnings("unchecked")
    public List<EstadoPedido> buscarTodos() throws PersistenciaException {
        List<EstadoPedido> listaEstadoPedido;
        try {
            Query query = this.entityManager.createQuery("SELECT e FROM EstadoPedido e");
            listaEstadoPedido = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todos os estados de pedido - " + ex.getMessage());
        }
        return listaEstadoPedido;
    }
}
