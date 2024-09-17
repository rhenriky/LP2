package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.ItemOrdemProducao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ItemOrdemProducaoDAO {

    private final EntityManager entityManager;

    public ItemOrdemProducaoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void beginTransaction() {
        if (!this.entityManager.getTransaction().isActive()) {
            this.entityManager.getTransaction().begin();
        }
    }

    public void commitTransaction() {
        if (this.entityManager.getTransaction().isActive()) {
            this.entityManager.getTransaction().commit();
        }
    }

    public void rollbackTransaction() {
        if (this.entityManager.getTransaction().isActive()) {
            this.entityManager.getTransaction().rollback();
        }
    }

    public void inserir(ItemOrdemProducao itemOrdemProducao) throws PersistenciaException {
        try {
            this.entityManager.persist(itemOrdemProducao);
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na inserção de ItemOrdemProducao - " + ex.getMessage(), ex);
        }
    }

    public void alterar(ItemOrdemProducao itemOrdemProducao) throws PersistenciaException {
        try {
            this.entityManager.merge(itemOrdemProducao);
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na alteração de ItemOrdemProducao - " + ex.getMessage(), ex);
        }
    }

    public void excluir(ItemOrdemProducao itemOrdemProducao) throws PersistenciaException {
        try {
            this.entityManager.remove(this.entityManager.contains(itemOrdemProducao) ? itemOrdemProducao : this.entityManager.merge(itemOrdemProducao));
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na exclusão de ItemOrdemProducao - " + ex.getMessage(), ex);
        }
    }

    public List<ItemOrdemProducao> buscarTodos() throws PersistenciaException {
        try {
            return this.entityManager.createQuery("SELECT i FROM ItemOrdemProducao i ORDER BY i.codigo", ItemOrdemProducao.class).getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todos os itens de ordem de produção - " + ex.getMessage(), ex);
        }
    }

    public ItemOrdemProducao buscarPorCodigo(int codigo) throws PersistenciaException {
        try {
            return this.entityManager.createQuery("SELECT i FROM ItemOrdemProducao i WHERE i.codigo = :codigo", ItemOrdemProducao.class)
                .setParameter("codigo", codigo)
                .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se nenhum item for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do item de ordem de produção pelo código - " + ex.getMessage(), ex);
        }
    }

    public List<ItemOrdemProducao> buscarOrdensProducoesRegistradas() throws PersistenciaException {
        try {
            String jpql = "SELECT i FROM ItemOrdemProducao i WHERE i.estado = :estado ORDER BY i.dataHoraRegistro ASC";
            TypedQuery<ItemOrdemProducao> query = this.entityManager.createQuery(jpql, ItemOrdemProducao.class);
            query.setParameter("estado", "REGISTRADA");
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Erro ao consultar ordens de produção registradas - " + e.getMessage(), e);
        }
    }

    public List<ItemOrdemProducao> buscarOrdensProducoesProcessadas() throws PersistenciaException {
        try {
            String jpql = "SELECT i FROM ItemOrdemProducao i WHERE i.estado = :estado ORDER BY i.dataHoraProcessamento ASC";
            TypedQuery<ItemOrdemProducao> query = this.entityManager.createQuery(jpql, ItemOrdemProducao.class);
            query.setParameter("estado", "PROCESSADA");
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Erro ao consultar ordens de produção processadas - " + e.getMessage(), e);
        }
    }
}
