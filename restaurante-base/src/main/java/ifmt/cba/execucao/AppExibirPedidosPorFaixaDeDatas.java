package ifmt.cba.execucao;

import java.time.LocalDate;
import java.util.List;

import ifmt.cba.dto.PedidoDTO;
import ifmt.cba.negocio.PedidoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.PedidoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppExibirPedidosPorFaixaDeDatas {
    public static void main(String[] args) {
        try {
            PedidoDAO pedidoDAO = new PedidoDAO(FabricaEntityManager.getEntityManagerProducao());
            PedidoNegocio pedidoNegocio = new PedidoNegocio(pedidoDAO);

            // Definir a faixa de datas
            LocalDate dataInicio = LocalDate.now().minusDays(1);
            LocalDate dataFim = LocalDate.now().plusDays(3);

            // Exibir pedidos em uma faixa de datas
            System.out.println("Pedidos entre " + dataInicio + " e " + dataFim + ":");
            List<PedidoDTO> pedidos = pedidoNegocio.pesquisarPedidosPorFaixaDeDatas(dataInicio, dataFim);

            // Verificar se existem pedidos e exibir
            if (pedidos.isEmpty()) {
                System.out.println("Nenhum pedido encontrado na faixa de datas.");
            } else {
                for (PedidoDTO pedido : pedidos) {
                    System.out.println(pedido.toString());
                }
            }

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
