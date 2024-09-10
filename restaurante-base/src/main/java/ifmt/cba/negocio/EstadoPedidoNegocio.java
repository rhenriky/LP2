package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.EstadoPedidoDTO;
import ifmt.cba.entity.EstadoPedido;
import ifmt.cba.persistencia.EstadoPedidoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class EstadoPedidoNegocio {

    private ModelMapper modelMapper;
    private EstadoPedidoDAO estadoPedidoDAO;

    public EstadoPedidoNegocio(EstadoPedidoDAO estadoPedidoDAO) {
        this.estadoPedidoDAO = estadoPedidoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(EstadoPedidoDTO estadoPedidoDTO) throws NegocioException {
        EstadoPedido estadoPedido = this.toEntity(estadoPedidoDTO);
        String mensagemErros = estadoPedido.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            estadoPedidoDAO.beginTransaction();
            estadoPedidoDAO.incluir(estadoPedido);
            estadoPedidoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            estadoPedidoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o estado do pedido - " + ex.getMessage());
        }
    }

    public void alterar(EstadoPedidoDTO estadoPedidoDTO) throws NegocioException {
        EstadoPedido estadoPedido = this.toEntity(estadoPedidoDTO);
        String mensagemErros = estadoPedido.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (estadoPedidoDAO.buscarPorCodigo(estadoPedido.getCodigo()) == null) {
                throw new NegocioException("Não existe esse estado do pedido");
            }

            estadoPedidoDAO.beginTransaction();
            estadoPedidoDAO.alterar(estadoPedido);
            estadoPedidoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            estadoPedidoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o estado do pedido - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            EstadoPedido estadoPedido = estadoPedidoDAO.buscarPorCodigo(codigo);
            if (estadoPedido == null) {
                throw new NegocioException("Não existe esse estado do pedido");
            }

            estadoPedidoDAO.beginTransaction();
            estadoPedidoDAO.excluir(estadoPedido);
            estadoPedidoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            estadoPedidoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o estado do pedido - " + ex.getMessage());
        }
    }

    public List<EstadoPedidoDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(estadoPedidoDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar estados dos pedidos - " + ex.getMessage());
        }
    }

    public EstadoPedidoDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(estadoPedidoDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar estado do pedido pelo código - " + ex.getMessage());
        }
    }

    public List<EstadoPedidoDTO> toDTOAll(List<EstadoPedido> listaEstadoPedido) {
        List<EstadoPedidoDTO> listaDTO = new ArrayList<>();

        for (EstadoPedido estadoPedido : listaEstadoPedido) {
            listaDTO.add(this.toDTO(estadoPedido));
        }
        return listaDTO;
    }

    public EstadoPedidoDTO toDTO(EstadoPedido estadoPedido) {
        return this.modelMapper.map(estadoPedido, EstadoPedidoDTO.class);
    }

    public EstadoPedido toEntity(EstadoPedidoDTO estadoPedidoDTO) {
        return this.modelMapper.map(estadoPedidoDTO, EstadoPedido.class);
    }
}
