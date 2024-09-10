package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.TipoPedidoDTO;
import ifmt.cba.entity.TipoPedido;
import ifmt.cba.persistencia.TipoPedidoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class TipoPedidoNegocio {

    private ModelMapper modelMapper;
    private TipoPedidoDAO tipoPedidoDAO;

    public TipoPedidoNegocio(TipoPedidoDAO tipoPedidoDAO) {
        this.tipoPedidoDAO = tipoPedidoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(TipoPedidoDTO tipoPedidoDTO) throws NegocioException {
        TipoPedido tipoPedido = this.toEntity(tipoPedidoDTO);
        String mensagemErros = tipoPedido.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            tipoPedidoDAO.beginTransaction();
            tipoPedidoDAO.incluir(tipoPedido);
            tipoPedidoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            tipoPedidoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o tipo de pedido - " + ex.getMessage());
        }
    }

    public void alterar(TipoPedidoDTO tipoPedidoDTO) throws NegocioException {
        TipoPedido tipoPedido = this.toEntity(tipoPedidoDTO);
        String mensagemErros = tipoPedido.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (tipoPedidoDAO.buscarPorCodigo(tipoPedido.getCodigo()) == null) {
                throw new NegocioException("Não existe esse tipo de pedido");
            }

            tipoPedidoDAO.beginTransaction();
            tipoPedidoDAO.alterar(tipoPedido);
            tipoPedidoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            tipoPedidoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o tipo de pedido - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            TipoPedido tipoPedido = tipoPedidoDAO.buscarPorCodigo(codigo);
            if (tipoPedido == null) {
                throw new NegocioException("Não existe esse tipo de pedido");
            }

            tipoPedidoDAO.beginTransaction();
            tipoPedidoDAO.excluir(tipoPedido);
            tipoPedidoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            tipoPedidoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o tipo de pedido - " + ex.getMessage());
        }
    }

    public List<TipoPedidoDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(tipoPedidoDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar tipos de pedido - " + ex.getMessage());
        }
    }

    public TipoPedidoDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(tipoPedidoDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar tipo de pedido pelo código - " + ex.getMessage());
        }
    }

    public List<TipoPedidoDTO> toDTOAll(List<TipoPedido> listaTipoPedido) {
        List<TipoPedidoDTO> listaDTO = new ArrayList<>();

        for (TipoPedido tipoPedido : listaTipoPedido) {
            listaDTO.add(this.toDTO(tipoPedido));
        }
        return listaDTO;
    }

    public TipoPedidoDTO toDTO(TipoPedido tipoPedido) {
        return this.modelMapper.map(tipoPedido, TipoPedidoDTO.class);
    }

    public TipoPedido toEntity(TipoPedidoDTO tipoPedidoDTO) {
        return this.modelMapper.map(tipoPedidoDTO, TipoPedido.class);
    }
}
