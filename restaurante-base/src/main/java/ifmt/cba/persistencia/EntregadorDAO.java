package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.Entregador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class EntregadorDAO extends DAO<Entregador> {

    public EntregadorDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<Entregador> buscarTodosOrdenadosPorNome() throws PersistenciaException {
        List<Entregador> listaEntregador;
        try {
            Query query = this.entityManager.createQuery("SELECT e FROM Entregador e ORDER BY e.nome");
            listaEntregador = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos entregadores ordenados por nome - " + ex.getMessage());
        }
        return listaEntregador;
    }

    @SuppressWarnings("unchecked")
    public List<Entregador> buscarPorZonaEntrega(String zonaEntrega) throws PersistenciaException {
        List<Entregador> listaEntregador;
        try {
            Query query = this.entityManager.createQuery("SELECT e FROM Entregador e WHERE e.zonaEntrega = :zonaEntrega ORDER BY e.nome");
            query.setParameter("zonaEntrega", zonaEntrega);
            listaEntregador = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos entregadores por zona de entrega - " + ex.getMessage());
        }
        return listaEntregador;
    }

    public Entregador buscarPorCodigo(int codigo) throws PersistenciaException {
        Entregador entregador;
        try {
            Query query = this.entityManager.createQuery("SELECT e FROM Entregador e WHERE e.codigo = :codigo");
            query.setParameter("codigo", codigo);
            entregador = (Entregador) query.getSingleResult();
        } catch (NoResultException e) {
            entregador = null; // Retorna null se nenhum entregador for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do entregador pelo código - " + ex.getMessage());
        }
        return entregador;
    }

    @SuppressWarnings("unchecked")
    public List<Entregador> buscarTodos() throws PersistenciaException {
        List<Entregador> listaEntregador;
        try {
            Query query = this.entityManager.createQuery("SELECT e FROM Entregador e");
            listaEntregador = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todos os entregadores - " + ex.getMessage());
        }
        return listaEntregador;
    }
}
