package ifmt.cba.execucao;

import ifmt.cba.dto.GrupoAlimentoDTO;
import ifmt.cba.negocio.GrupoAlimentoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.GrupoAlimentoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirGrupoAlimento {
    public static void main(String[] args) {

        try {
            GrupoAlimentoDAO grupoAlimentoDAO = new GrupoAlimentoDAO(
                    FabricaEntityManager.getEntityManagerProducao());
            GrupoAlimentoNegocio grupoAlimentoNegocio = new GrupoAlimentoNegocio(grupoAlimentoDAO);
    
            GrupoAlimentoDTO grupoDTO = new GrupoAlimentoDTO();
            grupoDTO.setNome("Carne");
            grupoAlimentoNegocio.inserir(grupoDTO);

            grupoDTO = new GrupoAlimentoDTO();
            grupoDTO.setNome("Frango");
            grupoAlimentoNegocio.inserir(grupoDTO);

            grupoDTO = new GrupoAlimentoDTO();
            grupoDTO.setNome("Peixe");
            grupoAlimentoNegocio.inserir(grupoDTO);
            
            grupoDTO = new GrupoAlimentoDTO();
            grupoDTO.setNome("Suino");
            grupoAlimentoNegocio.inserir(grupoDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
