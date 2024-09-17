package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.PreparoProduto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class PreparoProdutoDAO extends DAO<PreparoProduto> {

    public PreparoProdutoDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<PreparoProduto> buscarTodos() throws PersistenciaException {
        List<PreparoProduto> listaPreparoProduto;
        try {
            Query query = this.entityManager.createQuery("SELECT p FROM PreparoProduto p ORDER BY p.dataPreparo");
            listaPreparoProduto = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todos os preparos de produtos - " + ex.getMessage());
        }
        return listaPreparoProduto;
    }

    public PreparoProduto buscarPorCodigo(int codigo) throws PersistenciaException {
        PreparoProduto preparoProduto;
        try {
            Query query = this.entityManager.createQuery("SELECT p FROM PreparoProduto p WHERE p.codigo = :codigo");
            query.setParameter("codigo", codigo);
            preparoProduto = (PreparoProduto) query.getSingleResult();
        } catch (NoResultException e) {
            preparoProduto = null; // Retorna null se nenhum preparo de produto for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do preparo de produto pelo código - " + ex.getMessage());
        }
        return preparoProduto;
    }

    @SuppressWarnings("unchecked")
    public List<PreparoProduto> buscarPreparosPorProduto(int produtoId) throws PersistenciaException {
        List<PreparoProduto> listaPreparoProduto;
        try {
            Query query = this.entityManager.createQuery("SELECT p FROM PreparoProduto p WHERE p.produto.codigo = :produtoId ORDER BY p.dataPreparo");
            query.setParameter("produtoId", produtoId);
            listaPreparoProduto = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos preparos de produtos por produto - " + ex.getMessage());
        }
        return listaPreparoProduto;
    }

    @SuppressWarnings("unchecked")
    public List<PreparoProduto> buscarPreparosPorData(String dataPreparo) throws PersistenciaException {
        List<PreparoProduto> listaPreparoProduto;
        try {
            Query query = this.entityManager.createQuery("SELECT p FROM PreparoProduto p WHERE p.dataPreparo = :dataPreparo ORDER BY p.horaPreparo");
            query.setParameter("dataPreparo", dataPreparo);
            listaPreparoProduto = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos preparos de produtos por data - " + ex.getMessage());
        }
        return listaPreparoProduto;
    }
}
