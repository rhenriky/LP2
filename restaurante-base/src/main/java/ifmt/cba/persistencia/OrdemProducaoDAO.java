package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.OrdemProducao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class OrdemProducaoDAO extends DAO<OrdemProducao> {

    public OrdemProducaoDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<OrdemProducao> buscarTodos() throws PersistenciaException {
        List<OrdemProducao> listaOrdemProducao;
        try {
            Query query = this.entityManager.createQuery("SELECT o FROM OrdemProducao o ORDER BY o.dataRegistro");
            listaOrdemProducao = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todas as ordens de produção - " + ex.getMessage());
        }
        return listaOrdemProducao;
    }

    public OrdemProducao buscarPorCodigo(int codigo) throws PersistenciaException {
        OrdemProducao ordemProducao;
        try {
            Query query = this.entityManager.createQuery("SELECT o FROM OrdemProducao o WHERE o.codigo = :codigo");
            query.setParameter("codigo", codigo);
            ordemProducao = (OrdemProducao) query.getSingleResult();
        } catch (NoResultException e) {
            ordemProducao = null; // Retorna null se nenhuma ordem de produção for encontrada
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção da ordem de produção pelo código - " + ex.getMessage());
        }
        return ordemProducao;
    }

    @SuppressWarnings("unchecked")
    public List<OrdemProducao> buscarPorEstadoRegistrada() throws PersistenciaException {
        List<OrdemProducao> listaOrdemProducao;
        try {
            Query query = this.entityManager.createQuery("SELECT o FROM OrdemProducao o WHERE o.estado = 'REGISTRADA' ORDER BY o.dataRegistro");
            listaOrdemProducao = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção das ordens de produção registradas - " + ex.getMessage());
        }
        return listaOrdemProducao;
    }

    @SuppressWarnings("unchecked")
    public List<OrdemProducao> buscarPorEstadoProcessada() throws PersistenciaException {
        List<OrdemProducao> listaOrdemProducao;
        try {
            Query query = this.entityManager.createQuery("SELECT o FROM OrdemProducao o WHERE o.estado = 'PROCESSADA' ORDER BY o.dataProcessamento");
            listaOrdemProducao = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção das ordens de produção processadas - " + ex.getMessage());
        }
        return listaOrdemProducao;
    }
}
