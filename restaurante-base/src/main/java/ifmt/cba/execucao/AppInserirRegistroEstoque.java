package ifmt.cba.execucao;

import ifmt.cba.dto.RegistroEstoqueDTO;
import ifmt.cba.negocio.RegistroEstoqueNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.RegistroEstoqueDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirRegistroEstoque {
    public static void main(String[] args) {
        try {
            RegistroEstoqueDAO registroEstoqueDAO = new RegistroEstoqueDAO(FabricaEntityManager.getEntityManagerProducao());
            RegistroEstoqueNegocio registroEstoqueNegocio = new RegistroEstoqueNegocio(registroEstoqueDAO);

            RegistroEstoqueDTO registroEstoqueDTO = new RegistroEstoqueDTO();
            registroEstoqueDTO.setProdutoCodigo(1);
            registroEstoqueDTO.setQuantidade(100);
            registroEstoqueNegocio.inserir(registroEstoqueDTO);

            registroEstoqueDTO = new RegistroEstoqueDTO();
            registroEstoqueDTO.setProdutoCodigo(2);
            registroEstoqueDTO.setQuantidade(150);
            registroEstoqueNegocio.inserir(registroEstoqueDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
