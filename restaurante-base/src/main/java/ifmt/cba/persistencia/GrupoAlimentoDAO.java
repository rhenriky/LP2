package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.GrupoAlimento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class GrupoAlimentoDAO extends DAO<GrupoAlimento> {

    public GrupoAlimentoDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<GrupoAlimento> buscarTodosOrdenadosPorNome() throws PersistenciaException {
        List<GrupoAlimento> listaGrupoAlimento;
        try {
            Query query = this.entityManager.createQuery("SELECT g FROM GrupoAlimento g ORDER BY g.nome");
            listaGrupoAlimento = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos grupos alimentares ordenados por nome - " + ex.getMessage());
        }
        return listaGrupoAlimento;
    }

    public GrupoAlimento buscarPorNome(String nome) throws PersistenciaException {
        GrupoAlimento grupoAlimento;
        try {
            Query query = this.entityManager.createQuery("SELECT g FROM GrupoAlimento g WHERE g.nome = :nome");
            query.setParameter("nome", nome);
            grupoAlimento = (GrupoAlimento) query.getSingleResult();
        } catch (NoResultException e) {
            grupoAlimento = null; // Retorna null se nenhum grupo alimentar for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do grupo alimentar por nome - " + ex.getMessage());
        }
        return grupoAlimento;
    }

    @SuppressWarnings("unchecked")
    public List<GrupoAlimento> buscarPorParteNome(String parteNome) throws PersistenciaException {
        List<GrupoAlimento> listaGrupoAlimento;
        try {
            Query query = this.entityManager.createQuery("SELECT g FROM GrupoAlimento g WHERE g.nome LIKE :parteNome ORDER BY g.nome");
            query.setParameter("parteNome", "%" + parteNome + "%");
            listaGrupoAlimento = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos grupos alimentares por parte do nome - " + ex.getMessage());
        }
        return listaGrupoAlimento;
    }

    public GrupoAlimento buscarPorCodigo(int codigo) throws PersistenciaException {
        GrupoAlimento grupoAlimento;
        try {
            Query query = this.entityManager.createQuery("SELECT g FROM GrupoAlimento g WHERE g.codigo = :codigo");
            query.setParameter("codigo", codigo);
            grupoAlimento = (GrupoAlimento) query.getSingleResult();
        } catch (NoResultException e) {
            grupoAlimento = null; // Retorna null se nenhum grupo alimentar for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do grupo alimentar pelo código - " + ex.getMessage());
        }
        return grupoAlimento;
    }
}
