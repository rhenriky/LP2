package ifmt.cba.persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class FabricaEntityManager {

    private static EntityManagerFactory emf_producao;
    private static EntityManagerFactory emf_teste;
    public static final String UNIDADE_PRODUCAO = "restaurante_producao";
    public static final String UNIDADE_TESTE = "restaurante_teste";

    private FabricaEntityManager() {
        // Construtor privado para evitar instanciação
    }

    public static EntityManager getEntityManagerProducao() {
        if (emf_producao == null) {
            emf_producao = Persistence.createEntityManagerFactory(UNIDADE_PRODUCAO);
        }
        return emf_producao.createEntityManager();
    }

    public static EntityManager getEntityManagerTeste() {
        if (emf_teste == null) {
            emf_teste = Persistence.createEntityManagerFactory(UNIDADE_TESTE);
        }
        return emf_teste.createEntityManager();
    }

    public static void closeEntityManager() {
        // Fechar EntityManagerFactory de produção
        if (emf_producao != null && emf_producao.isOpen()) {
            emf_producao.close();
            emf_producao = null;
        }

        // Fechar EntityManagerFactory de teste
        if (emf_teste != null && emf_teste.isOpen()) {
            emf_teste.close();
            emf_teste = null;
        }
    }
}
