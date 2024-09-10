package ifmt.cba.execucao;

import ifmt.cba.dto.MovimentoEstoqueDTO;
import ifmt.cba.negocio.MovimentoEstoqueNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.MovimentoEstoqueDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirMovimentoEstoque {
    public static void main(String[] args) {
        try {
            MovimentoEstoqueDAO movimentoEstoqueDAO = new MovimentoEstoqueDAO(FabricaEntityManager.getEntityManagerProducao());
            MovimentoEstoqueNegocio movimentoEstoqueNegocio = new MovimentoEstoqueNegocio(movimentoEstoqueDAO);

            MovimentoEstoqueDTO movimentoEstoqueDTO = new MovimentoEstoqueDTO();
            movimentoEstoqueDTO.setTipo("PRODUCAO");
            movimentoEstoqueDTO.setQuantidade(50);
            movimentoEstoqueNegocio.inserir(movimentoEstoqueDTO);

            movimentoEstoqueDTO = new MovimentoEstoqueDTO();
            movimentoEstoqueDTO.setTipo("COMPRA");
            movimentoEstoqueDTO.setQuantidade(30);
            movimentoEstoqueNegocio.inserir(movimentoEstoqueDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
