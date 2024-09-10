package ifmt.cba.execucao;

import ifmt.cba.dto.PedidoDTO;
import ifmt.cba.negocio.PedidoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.PedidoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirPedido {
    public static void main(String[] args) {
        try {
            PedidoDAO pedidoDAO = new PedidoDAO(FabricaEntityManager.getEntityManagerProducao());
            PedidoNegocio pedidoNegocio = new PedidoNegocio(pedidoDAO);

            PedidoDTO pedidoDTO = new PedidoDTO();
            pedidoDTO.setData(java.time.LocalDateTime.now());
            pedidoNegocio.inserir(pedidoDTO);

            pedidoDTO = new PedidoDTO();
            pedidoDTO.setData(java.time.LocalDateTime.now().plusDays(2));
            pedidoNegocio.inserir(pedidoDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
