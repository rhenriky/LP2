package ifmt.cba.execucao;

import ifmt.cba.dto.ItemOrdemProducaoDTO;
import ifmt.cba.negocio.ItemOrdemProducaoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.ItemOrdemProducaoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirItemOrdemProducao {
    public static void main(String[] args) {
        try {
            ItemOrdemProducaoDAO itemOrdemProducaoDAO = new ItemOrdemProducaoDAO(FabricaEntityManager.getEntityManagerProducao());
            ItemOrdemProducaoNegocio itemOrdemProducaoNegocio = new ItemOrdemProducaoNegocio(itemOrdemProducaoDAO);

            ItemOrdemProducaoDTO itemOrdemProducaoDTO = new ItemOrdemProducaoDTO();
            itemOrdemProducaoDTO.setQuantidade(10);
            itemOrdemProducaoDTO.setOrdemProducaoCodigo(1);
            itemOrdemProducaoNegocio.inserir(itemOrdemProducaoDTO);

            itemOrdemProducaoDTO = new ItemOrdemProducaoDTO();
            itemOrdemProducaoDTO.setQuantidade(5);
            itemOrdemProducaoDTO.setOrdemProducaoCodigo(2);
            itemOrdemProducaoNegocio.inserir(itemOrdemProducaoDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
