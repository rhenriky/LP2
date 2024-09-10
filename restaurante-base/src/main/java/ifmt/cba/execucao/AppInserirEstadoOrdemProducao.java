package ifmt.cba.execucao;

import ifmt.cba.dto.EstadoOrdemProducaoDTO;
import ifmt.cba.negocio.EstadoOrdemProducaoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.EstadoOrdemProducaoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirEstadoOrdemProducao {
    public static void main(String[] args) {
        try {
            EstadoOrdemProducaoDAO estadoOrdemProducaoDAO = new EstadoOrdemProducaoDAO(FabricaEntityManager.getEntityManagerProducao());
            EstadoOrdemProducaoNegocio estadoOrdemProducaoNegocio = new EstadoOrdemProducaoNegocio(estadoOrdemProducaoDAO);

            EstadoOrdemProducaoDTO estadoOrdemProducaoDTO = new EstadoOrdemProducaoDTO();
            estadoOrdemProducaoDTO.setDescricao("Registrada");
            estadoOrdemProducaoNegocio.inserir(estadoOrdemProducaoDTO);

            estadoOrdemProducaoDTO = new EstadoOrdemProducaoDTO();
            estadoOrdemProducaoDTO.setDescricao("Processada");
            estadoOrdemProducaoNegocio.inserir(estadoOrdemProducaoDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
