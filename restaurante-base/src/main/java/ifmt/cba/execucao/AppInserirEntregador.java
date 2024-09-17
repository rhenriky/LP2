package ifmt.cba.execucao;

import ifmt.cba.dto.EntregadorDTO;
import ifmt.cba.negocio.EntregadorNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.EntregadorDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirEntregador {
    public static void main(String[] args) {
        try {
            // Criação do DAO e do Negócio
            EntregadorDAO entregadorDAO = new EntregadorDAO(FabricaEntityManager.getEntityManagerProducao());
            EntregadorNegocio entregadorNegocio = new EntregadorNegocio(entregadorDAO);

            // Criar e configurar o primeiro EntregadorDTO
            EntregadorDTO entregadorDTO1 = new EntregadorDTO(0, null, null);
            entregadorDTO1.setNome("Ricardo Pereira");
            entregadorDTO1.setRG("123456789");
            entregadorDTO1.setCPF("123.456.789-00");
            entregadorDTO1.setTelefone("456789123");
            entregadorDTO1.setVeiculo("Fiorino");

            // Inserir o primeiro entregador
            entregadorNegocio.inserir(entregadorDTO1);

            // Criar e configurar o segundo EntregadorDTO
            EntregadorDTO entregadorDTO2 = new EntregadorDTO(0, null, null);
            entregadorDTO2.setNome("Juliana Costa");
            entregadorDTO2.setRG("987654321");
            entregadorDTO2.setCPF("987.654.321-00");
            entregadorDTO2.setTelefone("321654987");
            entregadorDTO2.setVeiculo("Civic");

            // Inserir o segundo entregador
            entregadorNegocio.inserir(entregadorDTO2);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
