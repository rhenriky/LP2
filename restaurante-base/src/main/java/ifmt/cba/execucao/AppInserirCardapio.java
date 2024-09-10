package ifmt.cba.execucao;

import ifmt.cba.dto.CardapioDTO;
import ifmt.cba.negocio.CardapioNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.CardapioDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirCardapio {
    public static void main(String[] args) {
        try {
            CardapioDAO cardapioDAO = new CardapioDAO(FabricaEntityManager.getEntityManagerProducao());
            CardapioNegocio cardapioNegocio = new CardapioNegocio(cardapioDAO);

            CardapioDTO cardapioDTO = new CardapioDTO();
            cardapioDTO.setNome("Menu Especial");
            cardapioNegocio.inserir(cardapioDTO);

            cardapioDTO = new CardapioDTO();
            cardapioDTO.setNome("Menu Executivo");
            cardapioNegocio.inserir(cardapioDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
