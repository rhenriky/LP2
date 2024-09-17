package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.RegistroEstoque;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class RegistroEstoqueDAO extends DAO<RegistroEstoque> {

    public RegistroEstoqueDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<RegistroEstoque> buscarTodos() throws PersistenciaException {
        List<RegistroEstoque> listaRegistroEstoque;
        try {
            Query query = this.entityManager.createQuery("SELECT r FROM RegistroEstoque r ORDER BY r.dataRegistro");
            listaRegistroEstoque = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todos os registros de estoque - " + ex.getMessage());
        }
        return listaRegistroEstoque;
    }

    public RegistroEstoque buscarPorCodigo(int codigo) throws PersistenciaException {
        RegistroEstoque registroEstoque;
        try {
            Query query = this.entityManager.createQuery("SELECT r FROM RegistroEstoque r WHERE r.codigo = :codigo");
            query.setParameter("codigo", codigo);
            registroEstoque = (RegistroEstoque) query.getSingleResult();
        } catch (NoResultException e) {
            registroEstoque = null; // Retorna null se nenhum registro de estoque for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do registro de estoque pelo código - " + ex.getMessage());
        }
        return registroEstoque;
    }

    @SuppressWarnings("unchecked")
    public List<RegistroEstoque> buscarRegistrosPorProduto(int produtoId) throws PersistenciaException {
        List<RegistroEstoque> listaRegistroEstoque;
        try {
            Query query = this.entityManager.createQuery("SELECT r FROM RegistroEstoque r WHERE r.produto.codigo = :produtoId ORDER BY r.dataRegistro");
            query.setParameter("produtoId", produtoId);
            listaRegistroEstoque = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos registros de estoque por produto - " + ex.getMessage());
        }
        return listaRegistroEstoque;
    }

    @SuppressWarnings("unchecked")
    public List<RegistroEstoque> buscarRegistrosPorData(String dataRegistro) throws PersistenciaException {
        List<RegistroEstoque> listaRegistroEstoque;
        try {
            Query query = this.entityManager.createQuery("SELECT r FROM RegistroEstoque r WHERE r.dataRegistro = :dataRegistro ORDER BY r.horaRegistro");
            query.setParameter("dataRegistro", dataRegistro);
            listaRegistroEstoque = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos registros de estoque por data - " + ex.getMessage());
        }
        return listaRegistroEstoque;
    }
}
