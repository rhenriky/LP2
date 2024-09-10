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
            EntregadorDAO entregadorDAO = new EntregadorDAO(FabricaEntityManager.getEntityManagerProducao());
            EntregadorNegocio entregadorNegocio = new EntregadorNegocio(entregadorDAO);

            EntregadorDTO entregadorDTO = new EntregadorDTO();
            entregadorDTO.setNome("Ricardo Pereira");
            entregadorDTO.setTelefone("456789123");
            entregadorNegocio.inserir(entregadorDTO);

            entregadorDTO = new EntregadorDTO();
            entregadorDTO.setNome("Juliana Costa");
            entregadorDTO.setTelefone("321654987");
            entregadorNegocio.inserir(entregadorDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
