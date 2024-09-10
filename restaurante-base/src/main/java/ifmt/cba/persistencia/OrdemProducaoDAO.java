package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.OrdemProducao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class OrdemProducaoDAO extends DAO<OrdemProducao> {

    public OrdemProducaoDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
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
