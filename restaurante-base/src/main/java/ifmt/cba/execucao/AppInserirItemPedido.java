package ifmt.cba.execucao;

import ifmt.cba.dto.ItemPedidoDTO;
import ifmt.cba.negocio.ItemPedidoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.ItemPedidoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirItemPedido {
    public static void main(String[] args) {
        try {
            ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(FabricaEntityManager.getEntityManagerProducao());
            ItemPedidoNegocio itemPedidoNegocio = new ItemPedidoNegocio(itemPedidoDAO);

            ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
            itemPedidoDTO.setQuantidade(3);
            itemPedidoDTO.setPedidoCodigo(1);
            itemPedidoNegocio.inserir(itemPedidoDTO);

            itemPedidoDTO = new ItemPedidoDTO();
            itemPedidoDTO.setQuantidade(7);
            itemPedidoDTO.setPedidoCodigo(2);
            itemPedidoNegocio.inserir(itemPedidoDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
