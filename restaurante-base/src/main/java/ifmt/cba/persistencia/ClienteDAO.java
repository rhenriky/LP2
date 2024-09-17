package ifmt.cba.persistencia;

import java.util.List;

import ifmt.cba.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;

public class ClienteDAO extends DAO<Cliente> {

    public ClienteDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    // Método para buscar cliente por código
    public Cliente buscarPorCodigo(int codigo) throws PersistenciaException {
        Cliente cliente = null;
        try {
            cliente = this.entityManager.find(Cliente.class, codigo);
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção por código - " + ex.getMessage());
        }
        return cliente;
    }

    // Método para buscar cliente por CPF
    public Cliente buscarPorCPF(String CPF) throws PersistenciaException {
        Cliente cliente;
        try {
            Query query = this.entityManager
                    .createQuery("SELECT c FROM Cliente c WHERE UPPER(c.CPF) = :pCPF");
            query.setParameter("pCPF", CPF.toUpperCase().trim());
            cliente = (Cliente) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            cliente = null;
        } catch (Exception ex) {
            cliente = null;
            throw new PersistenciaException("Erro na seleção por CPF - " + ex.getMessage());
        }
        return cliente;
    }

    // Método para buscar cliente por parte do nome
    @SuppressWarnings("unchecked")
    public List<Cliente> buscarPorParteNome(String nome) throws PersistenciaException {
        List<Cliente> listaCliente;
        try {
            Query query = this.entityManager
                    .createQuery("SELECT c FROM Cliente c WHERE UPPER(c.nome) LIKE :pNome ORDER BY c.nome");
            query.setParameter("pNome", "%" + nome.toUpperCase().trim() + "%");
            listaCliente = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção por parte do nome - " + ex.getMessage());
        }
        return listaCliente;
    }

    // Método para buscar todos os clientes ordenados por nome
    @SuppressWarnings("unchecked")
    public List<Cliente> buscarTodosOrdenadosPorNome() throws PersistenciaException {
        List<Cliente> listaCliente;
        try {
            // Consulta para buscar todos os clientes ordenados em ordem crescente
            Query query = this.entityManager
                    .createQuery("SELECT c FROM Cliente c ORDER BY c.nome ASC");
            listaCliente = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todos os clientes ordenados por nome - " + ex.getMessage());
        }
        return listaCliente;
    }
}
