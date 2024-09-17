package ifmt.cba.execucao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import ifmt.cba.dto.ClienteDTO;
import ifmt.cba.dto.EstadoPedidoDTO;
import ifmt.cba.dto.EntregadorDTO;
import ifmt.cba.dto.ItemPedidoDTO;
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

            // Inserir o primeiro pedido
            PedidoDTO pedidoDTO = new PedidoDTO();
            pedidoDTO.setCodigo(1);
            pedidoDTO.setCliente(new ClienteDTO(0, "João", "123456789")); // Ajuste aqui para definir o código do cliente corretamente
            pedidoDTO.setDataPedido(LocalDate.now());
            pedidoDTO.setHoraPedido(LocalTime.now());
            pedidoDTO.setHoraProducao(LocalTime.of(10, 30));
            pedidoDTO.setHoraPronto(LocalTime.of(11, 30));
            pedidoDTO.setHoraEntrega(LocalTime.of(12, 0));
            pedidoDTO.setHoraFinalizado(LocalTime.of(12, 30));
            pedidoDTO.setEstado(new EstadoPedidoDTO(1, "ENTREGUE", "Pedido entregue"));
            pedidoDTO.setEntregador(new EntregadorDTO(1, "Carlos", "987654321")); // Ajuste aqui para definir o código do entregador corretamente
            pedidoDTO.setItensPedido(new ArrayList<ItemPedidoDTO>());

            // Inserir e exibir o primeiro pedido
            pedidoNegocio.inserir(pedidoDTO);
            System.out.println(pedidoDTO.toString());

            // Inserir o segundo pedido
            pedidoDTO = new PedidoDTO();
            pedidoDTO.setCodigo(2);
            pedidoDTO.setCliente(new ClienteDTO(2, "Maria", "987654321")); // Ajuste aqui para definir o código do cliente corretamente
            pedidoDTO.setDataPedido(LocalDate.now().plusDays(2));
            pedidoDTO.setHoraPedido(LocalTime.now().minusHours(1));
            pedidoDTO.setHoraProducao(LocalTime.of(14, 0));
            pedidoDTO.setHoraPronto(LocalTime.of(15, 0));
            pedidoDTO.setHoraEntrega(LocalTime.of(15, 30));
            pedidoDTO.setHoraFinalizado(LocalTime.of(16, 0));
            pedidoDTO.setEstado(new EstadoPedidoDTO(2, "PENDENTE", "Pedido Pendente"));
            pedidoDTO.setEntregador(new EntregadorDTO(2, "Pedro", "123123123")); // Ajuste aqui para definir o código do entregador corretamente
            pedidoDTO.setItensPedido(new ArrayList<ItemPedidoDTO>());

            // Inserir e exibir o segundo pedido
            pedidoNegocio.inserir(pedidoDTO);
            System.out.println(pedidoDTO.toString());

            // Exibir pedidos em uma faixa de datas
            LocalDate dataInicio = LocalDate.now().minusDays(1);
            LocalDate dataFim = LocalDate.now().plusDays(3);
            System.out.println("\nPedidos entre " + dataInicio + " e " + dataFim + ":");
            List<PedidoDTO> pedidos = pedidoNegocio.pesquisarPedidosPorFaixaDeDatas(dataInicio, dataFim);
            for (PedidoDTO pedido : pedidos) {
                System.out.println(pedido.toString());
            }

            // Exibir itens de um pedido específico
            int codigoPedido = 1; // Código do pedido para consulta
            System.out.println("\nItens do Pedido " + codigoPedido + ":");
            List<ItemPedidoDTO> itensPedido = pedidoNegocio.pesquisarItensPorPedido(codigoPedido);
            for (ItemPedidoDTO item : itensPedido) {
                System.out.println("Item: " + item.getNome() + ", Quantidade: " + item.getQuantidade());
            }

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
