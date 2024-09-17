package ifmt.cba.execucao;

import java.util.List;

import ifmt.cba.entity.Cliente;
import ifmt.cba.persistencia.ClienteDAO;
import ifmt.cba.persistencia.PersistenciaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AppBuscarClientes {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            // Cria o EntityManager a partir da unidade de persistência
            emf = Persistence.createEntityManagerFactory("restaurante_producao");
            em = emf.createEntityManager();

            // Cria uma instância do ClienteDAO
            ClienteDAO clienteDAO = new ClienteDAO(em);
            
            // Busca todos os clientes ordenados por nome
            List<Cliente> listaClientes = clienteDAO.buscarTodosOrdenadosPorNome();
            
            // Verifica se a lista está vazia
            if (listaClientes.isEmpty()) {
                System.out.println("Nenhum cliente encontrado.");
            } else {
                // Itera sobre a lista e imprime os clientes
                System.out.println("Lista de clientes ordenados por nome:");
                for (Cliente cliente : listaClientes) {
                    System.out.println("Nome: " + cliente.getNome() + " | CPF: " + cliente.getCPF());
                }
            }
        } catch (PersistenciaException e) {
            System.err.println("Erro ao buscar clientes: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
