package ifmt.cba.negocio;

import java.time.LocalDate;
import java.util.List;
import org.modelmapper.ModelMapper;

import ifmt.cba.dto.ItemPedidoDTO;
import ifmt.cba.dto.PedidoDTO;
import ifmt.cba.entity.Pedido;
import ifmt.cba.entity.ItemPedido;
import ifmt.cba.persistencia.PedidoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class PedidoNegocio {

    private ModelMapper modelMapper;
    private PedidoDAO pedidoDAO;

    public PedidoNegocio(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(PedidoDTO pedidoDTO) throws NegocioException {
        Pedido pedido = this.toEntity(pedidoDTO);
        String mensagemErros = pedido.validar(); // Verifica se há erros de validação

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException("Validação falhou: " + mensagemErros); // Lança exceção se houver erros
        }

        // Verifica se o cliente existe
        if (pedidoDTO.getCliente() == null || pedidoDTO.getCliente().getCodigo() <= 0) {
            throw new NegocioException("Código do cliente inválido.");
        }

        try {
            pedidoDAO.beginTransaction();
            pedidoDAO.incluir(pedido); // Inclui o pedido no banco de dados
            pedidoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            pedidoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o pedido: " + ex.getMessage());
        }
    }

    public void alterar(PedidoDTO pedidoDTO) throws NegocioException {
        Pedido pedido = this.toEntity(pedidoDTO);
        String mensagemErros = pedido.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException("Validação falhou: " + mensagemErros);
        }

        try {
            if (pedidoDAO.buscarPorCodigo(pedido.getCodigo()) == null) {
                throw new NegocioException("Pedido não encontrado.");
            }

            pedidoDAO.beginTransaction();
            pedidoDAO.alterar(pedido);
            pedidoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            pedidoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o pedido: " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            Pedido pedido = pedidoDAO.buscarPorCodigo(codigo);
            if (pedido == null) {
                throw new NegocioException("Pedido não encontrado.");
            }

            pedidoDAO.beginTransaction();
            pedidoDAO.excluir(pedido);
            pedidoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            pedidoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o pedido: " + ex.getMessage());
        }
    }

    public List<PedidoDTO> pesquisarPedidosPorFaixaDeDatas(LocalDate dataInicio, LocalDate dataFim) throws NegocioException {
        try {
            List<Pedido> pedidos = pedidoDAO.buscarPedidosPorFaixaDeDatas(dataInicio, dataFim);
            return this.toDTOAll(pedidos);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar pedidos na faixa de datas: " + ex.getMessage());
        }
    }

    public List<PedidoDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(pedidoDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar pedidos: " + ex.getMessage());
        }
    }

    public PedidoDTO buscarPedidoPorCodigo(int codigo) throws NegocioException {
        try {
            Pedido pedido = pedidoDAO.buscarPorCodigo(codigo);
            if (pedido == null) {
                throw new NegocioException("Pedido não encontrado.");
            }
            return this.toDTO(pedido);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar pedido pelo código: " + ex.getMessage());
        }
    }

    public List<ItemPedidoDTO> pesquisarItensPorPedido(int codigoPedido) throws NegocioException {
        try {
            List<ItemPedido> itens = pedidoDAO.buscarItensPorPedido(codigoPedido);
            return itens.stream()
                    .map(item -> modelMapper.map(item, ItemPedidoDTO.class))
                    .toList();
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar itens do pedido: " + ex.getMessage());
        }
    }

    private List<PedidoDTO> toDTOAll(List<Pedido> listaPedidos) {
        return listaPedidos.stream()
                .map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
                .toList();
    }

    private PedidoDTO toDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    private Pedido toEntity(PedidoDTO pedidoDTO) {
        return modelMapper.map(pedidoDTO, Pedido.class);
    }
}
