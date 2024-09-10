package ifmt.cba.persistencia;

import java.util.Date;
import java.util.List;
import ifmt.cba.entity.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class PedidoDAO extends DAO<Pedido> {

    public PedidoDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<Pedido> buscarPorFaixaDeDatas(Date dataInicio, Date dataFim) throws PersistenciaException {
        List<Pedido> listaPedido;
        try {
            Query query = this.entityManager.createQuery("SELECT p FROM Pedido p WHERE p.data BETWEEN :dataInicio AND :dataFim");
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);
            listaPedido = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos pedidos por faixa de datas - " + ex.getMessage());
        }
        return listaPedido;
    }

    @SuppressWarnings("unchecked")
    public List<Pedido> buscarItensPorPedido(int idPedido) throws PersistenciaException {
        List<Pedido> listaPedido;
        try {
            Query query = this.entityManager.createQuery("SELECT p FROM Pedido p WHERE p.id = :idPedido");
            query.setParameter("idPedido", idPedido);
            listaPedido = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos itens do pedido - " + ex.getMessage());
        }
        return listaPedido;
    }
}
