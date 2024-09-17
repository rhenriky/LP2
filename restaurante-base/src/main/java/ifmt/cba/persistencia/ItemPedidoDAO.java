package ifmt.cba.persistencia;

import java.util.List;

import ifmt.cba.entity.ItemPedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class ItemPedidoDAO extends DAO<ItemPedido> {

    public ItemPedidoDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<ItemPedido> buscarTodos() throws PersistenciaException {
        List<ItemPedido> listaItemPedido;
        try {
            Query query = this.entityManager.createQuery("SELECT i FROM ItemPedido i ORDER BY i.codigo");
            listaItemPedido = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todos os itens do pedido - " + ex.getMessage());
        }
        return listaItemPedido;
    }

    public ItemPedido buscarPorCodigo(int codigo) throws PersistenciaException {
        ItemPedido itemPedido;
        try {
            Query query = this.entityManager.createQuery("SELECT i FROM ItemPedido i WHERE i.codigo = :codigo");
            query.setParameter("codigo", codigo);
            itemPedido = (ItemPedido) query.getSingleResult();
        } catch (NoResultException e) {
            itemPedido = null; // Retorna null se nenhum item for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do item do pedido pelo código - " + ex.getMessage());
        }
        return itemPedido;
    }

    @SuppressWarnings("unchecked")
    public List<ItemPedido> buscarTodosItensPorPedido(int pedidoId) throws PersistenciaException {
        List<ItemPedido> listaItemPedido;
        try {
            Query query = this.entityManager.createQuery("SELECT i FROM ItemPedido i WHERE i.pedido.codigo = :pedidoId ORDER BY i.codigo");
            query.setParameter("pedidoId", pedidoId);
            listaItemPedido = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos itens do pedido por código do pedido - " + ex.getMessage());
        }
        return listaItemPedido;
    }

    @SuppressWarnings("unchecked")
    public List<ItemPedido> buscarItensPorNomeProduto(String nomeProduto) throws PersistenciaException {
        List<ItemPedido> listaItemPedido;
        try {
            Query query = this.entityManager.createQuery("SELECT i FROM ItemPedido i WHERE UPPER(i.preparoProduto.nome) LIKE :nomeProduto ORDER BY i.preparoProduto.nome");
            query.setParameter("nomeProduto", "%" + nomeProduto.toUpperCase().trim() + "%");
            listaItemPedido = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos itens do pedido por nome de produto - " + ex.getMessage());
        }
        return listaItemPedido;
    }
}
