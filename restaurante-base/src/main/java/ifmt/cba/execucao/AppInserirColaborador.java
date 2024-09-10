package ifmt.cba.execucao;

import ifmt.cba.dto.ColaboradorDTO;
import ifmt.cba.negocio.ColaboradorNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.ColaboradorDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirColaborador {
    public static void main(String[] args) {
        try {
            ColaboradorDAO colaboradorDAO = new ColaboradorDAO(FabricaEntityManager.getEntityManagerProducao());
            ColaboradorNegocio colaboradorNegocio = new ColaboradorNegocio(colaboradorDAO);

            ColaboradorDTO colaboradorDTO = new ColaboradorDTO();
            colaboradorDTO.setNome("Carlos Alberto");
            colaboradorDTO.setCargo("Chef de Cozinha");
            colaboradorNegocio.inserir(colaboradorDTO);

            colaboradorDTO = new ColaboradorDTO();
            colaboradorDTO.setNome("Ana Maria");
            colaboradorDTO.setCargo("Garçonete");
            colaboradorNegocio.inserir(colaboradorDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
