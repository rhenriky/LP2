package ifmt.cba.execucao;

import ifmt.cba.dto.OrdemProducaoDTO;
import ifmt.cba.negocio.OrdemProducaoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.OrdemProducaoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirOrdemProducao {
    public static void main(String[] args) {
        try {
            OrdemProducaoDAO ordemProducaoDAO = new OrdemProducaoDAO(FabricaEntityManager.getEntityManagerProducao());
            OrdemProducaoNegocio ordemProducaoNegocio = new OrdemProducaoNegocio(ordemProducaoDAO);

            OrdemProducaoDTO ordemProducaoDTO = new OrdemProducaoDTO();
            ordemProducaoDTO.setData(java.time.LocalDateTime.now());
            ordemProducaoNegocio.inserir(ordemProducaoDTO);

            ordemProducaoDTO = new OrdemProducaoDTO();
            ordemProducaoDTO.setData(java.time.LocalDateTime.now().plusDays(1));
            ordemProducaoNegocio.inserir(ordemProducaoDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
