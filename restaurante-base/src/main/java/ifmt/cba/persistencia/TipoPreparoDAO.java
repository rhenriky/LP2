package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.TipoPreparo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class TipoPreparoDAO extends DAO<TipoPreparo> {

    public TipoPreparoDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<TipoPreparo> buscarTodos() throws PersistenciaException {
        List<TipoPreparo> listaTipoPreparo;
        try {
            Query query = this.entityManager.createQuery("SELECT t FROM TipoPreparo t ORDER BY t.nome");
            listaTipoPreparo = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todos os tipos de preparo - " + ex.getMessage());
        }
        return listaTipoPreparo;
    }

    public TipoPreparo buscarPorCodigo(int codigo) throws PersistenciaException {
        TipoPreparo tipoPreparo;
        try {
            Query query = this.entityManager.createQuery("SELECT t FROM TipoPreparo t WHERE t.codigo = :codigo");
            query.setParameter("codigo", codigo);
            tipoPreparo = (TipoPreparo) query.getSingleResult();
        } catch (NoResultException e) {
            tipoPreparo = null; // Retorna null se nenhum tipo de preparo for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do tipo de preparo pelo código - " + ex.getMessage());
        }
        return tipoPreparo;
    }

    public TipoPreparo buscarPorNome(String nome) throws PersistenciaException {
        TipoPreparo tipoPreparo;
        try {
            Query query = this.entityManager.createQuery("SELECT t FROM TipoPreparo t WHERE t.nome = :nome");
            query.setParameter("nome", nome);
            tipoPreparo = (TipoPreparo) query.getSingleResult();
        } catch (NoResultException e) {
            tipoPreparo = null; // Retorna null se nenhum tipo de preparo for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do tipo de preparo por nome - " + ex.getMessage());
        }
        return tipoPreparo;
    }
}
