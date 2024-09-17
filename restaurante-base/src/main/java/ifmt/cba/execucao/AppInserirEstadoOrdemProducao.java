package ifmt.cba.execucao;

import ifmt.cba.dto.EstadoOrdemProducaoDTO;
import ifmt.cba.negocio.EstadoOrdemProducaoNegocio;
import ifmt.cba.persistencia.PersistenciaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AppInserirEstadoOrdemProducao {

    public static void main(String[] args) throws PersistenciaException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ifmt_pu");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EstadoOrdemProducaoNegocio estadoOrdemProducaoNegocio = new EstadoOrdemProducaoNegocio(entityManager);

        try {
            // Iniciar transação
            entityManager.getTransaction().begin();

            // Criar DTO com o estado
            EstadoOrdemProducaoDTO estadoOrdemProducaoDTO = EstadoOrdemProducaoDTO.REGISTRADA;

            // Inserir estado
            estadoOrdemProducaoNegocio.inserir(estadoOrdemProducaoDTO);

            // Commit da transação
            entityManager.getTransaction().commit();

            System.out.println("Estado de Ordem de Produção inserido com sucesso!");

        } catch (PersistenciaException e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
