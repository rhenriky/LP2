package ifmt.cba.persistencia;

import java.time.LocalDate;
import java.util.List;

import ifmt.cba.entity.ItemPedido;
import ifmt.cba.entity.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class PedidoDAO extends DAO<Pedido> {

    public PedidoDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    // Buscar todos os pedidos
    public List<Pedido> buscarTodos() throws PersistenciaException {
        try {
            TypedQuery<Pedido> query = this.entityManager.createQuery("SELECT p FROM Pedido p ORDER BY p.dataPedido", Pedido.class);
            return query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todos os pedidos: " + ex.getMessage());
        }
    }

    // Buscar pedido por código
    public Pedido buscarPorCodigo(int codigo) throws PersistenciaException {
        try {
            return this.entityManager.find(Pedido.class, codigo);
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do pedido pelo código: " + ex.getMessage());
        }
    }

    // Buscar pedidos por faixa de datas
    public List<Pedido> buscarPedidosPorFaixaDeDatas(LocalDate dataInicio, LocalDate dataFim) throws PersistenciaException {
        try {
            TypedQuery<Pedido> query = entityManager.createQuery(
                "SELECT p FROM Pedido p WHERE p.dataPedido BETWEEN :dataInicio AND :dataFim ORDER BY p.dataPedido", 
                Pedido.class
            );
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Erro ao consultar pedidos por faixa de datas: " + e.getMessage());
        }
    }

    // Inclusão de um pedido
    public void incluir(Pedido pedido) throws PersistenciaException {
        try {
            this.entityManager.persist(pedido);
        } catch (Exception ex) {
            throw new PersistenciaException("Erro ao incluir o pedido: " + ex.getMessage());
        }
    }

    // Alteração de um pedido
    public void alterar(Pedido pedido) throws PersistenciaException {
        try {
            this.entityManager.merge(pedido);
        } catch (Exception ex) {
            throw new PersistenciaException("Erro ao alterar o pedido: " + ex.getMessage());
        }
    }

    // Exclusão de um pedido
    public void excluir(Pedido pedido) throws PersistenciaException {
        try {
            this.entityManager.remove(pedido);
        } catch (Exception ex) {
            throw new PersistenciaException("Erro ao excluir o pedido: " + ex.getMessage());
        }
    }

    // Buscar itens de um pedido específico
    public List<ItemPedido> buscarItensPorPedido(int codigoPedido) throws PersistenciaException {
        try {
            TypedQuery<ItemPedido> query = entityManager.createQuery(
                "SELECT i FROM ItemPedido i WHERE i.pedido.codigo = :codigoPedido", 
                ItemPedido.class
            );
            query.setParameter("codigoPedido", codigoPedido);
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Erro ao consultar itens do pedido: " + e.getMessage());
        }
    }

    

    
}
