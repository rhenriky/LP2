package ifmt.cba.execucao;

import ifmt.cba.dto.EstadoPedidoDTO;
import ifmt.cba.negocio.EstadoPedidoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.EstadoPedidoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirEstadoPedido {
    public static void main(String[] args) {
        try {
            EstadoPedidoDAO estadoPedidoDAO = new EstadoPedidoDAO(FabricaEntityManager.getEntityManagerProducao());
            EstadoPedidoNegocio estadoPedidoNegocio = new EstadoPedidoNegocio(estadoPedidoDAO);

            EstadoPedidoDTO estadoPedidoDTO = new EstadoPedidoDTO();
            estadoPedidoDTO.setDescricao("Novo");
            estadoPedidoNegocio.inserir(estadoPedidoDTO);

            estadoPedidoDTO = new EstadoPedidoDTO();
            estadoPedidoDTO.setDescricao("Finalizado");
            estadoPedidoNegocio.inserir(estadoPedidoDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
