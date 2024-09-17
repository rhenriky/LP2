package ifmt.cba.execucao;

import ifmt.cba.dto.TipoPreparoDTO;
import ifmt.cba.negocio.TipoPreparoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.TipoPreparoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirTipoPreparo {
    public static void main(String[] args) {
        try {
            TipoPreparoDAO tipoPreparoDAO = new TipoPreparoDAO(FabricaEntityManager.getEntityManagerProducao());
            TipoPreparoNegocio tipoPreparoNegocio = new TipoPreparoNegocio(tipoPreparoDAO);

            TipoPreparoDTO tipoPreparoDTO = new TipoPreparoDTO();
            tipoPreparoDTO.setDescricao("Molho");
            tipoPreparoNegocio.inserir(tipoPreparoDTO);

            tipoPreparoDTO = new TipoPreparoDTO();
            tipoPreparoDTO.setDescricao("Refogado");
            tipoPreparoNegocio.inserir(tipoPreparoDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
