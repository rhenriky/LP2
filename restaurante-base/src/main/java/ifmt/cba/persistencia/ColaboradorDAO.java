package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.Colaborador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class ColaboradorDAO extends DAO<Colaborador> {

    public ColaboradorDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<Colaborador> buscarTodosOrdenadosPorNome() throws PersistenciaException {
        List<Colaborador> listaColaborador;
        try {
            Query query = this.entityManager.createQuery("SELECT c FROM Colaborador c ORDER BY c.nome");
            listaColaborador = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos colaboradores ordenados por nome - " + ex.getMessage());
        }
        return listaColaborador;
    }

    @SuppressWarnings("unchecked")
    public List<Colaborador> buscarPorDepartamento(String departamento) throws PersistenciaException {
        List<Colaborador> listaColaborador;
        try {
            Query query = this.entityManager.createQuery("SELECT c FROM Colaborador c WHERE c.departamento = :departamento ORDER BY c.nome");
            query.setParameter("departamento", departamento);
            listaColaborador = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos colaboradores por departamento - " + ex.getMessage());
        }
        return listaColaborador;
    }

    public Colaborador buscarPorCodigo(int codigo) throws PersistenciaException {
        Colaborador colaborador;
        try {
            Query query = this.entityManager.createQuery("SELECT c FROM Colaborador c WHERE c.codigo = :codigo");
            query.setParameter("codigo", codigo);
            colaborador = (Colaborador) query.getSingleResult();
        } catch (NoResultException e) {
            colaborador = null; // Retorna null se nenhum colaborador for encontrado
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do colaborador pelo código - " + ex.getMessage());
        }
        return colaborador;
    }

    public List<Colaborador> buscarTodos() {
        
        throw new UnsupportedOperationException("Unimplemented method 'buscarTodos'");
    }
}
