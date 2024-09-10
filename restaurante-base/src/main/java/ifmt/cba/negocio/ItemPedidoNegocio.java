package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.ItemPedidoDTO;
import ifmt.cba.entity.ItemPedido;
import ifmt.cba.persistencia.ItemPedidoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class ItemPedidoNegocio {

    private ModelMapper modelMapper;
    private ItemPedidoDAO itemPedidoDAO;

    public ItemPedidoNegocio(ItemPedidoDAO itemPedidoDAO) {
        this.itemPedidoDAO = itemPedidoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(ItemPedidoDTO itemPedidoDTO) throws NegocioException {
        ItemPedido itemPedido = this.toEntity(itemPedidoDTO);
        String mensagemErros = itemPedido.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            itemPedidoDAO.beginTransaction();
            itemPedidoDAO.incluir(itemPedido);
            itemPedidoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            itemPedidoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o item de pedido - " + ex.getMessage());
        }
    }

    public void alterar(ItemPedidoDTO itemPedidoDTO) throws NegocioException {
        ItemPedido itemPedido = this.toEntity(itemPedidoDTO);
        String mensagemErros = itemPedido.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (itemPedidoDAO.buscarPorCodigo(itemPedido.getCodigo()) == null) {
                throw new NegocioException("Não existe esse item de pedido");
            }

            itemPedidoDAO.beginTransaction();
            itemPedidoDAO.alterar(itemPedido);
            itemPedidoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            itemPedidoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o item de pedido - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            ItemPedido itemPedido = itemPedidoDAO.buscarPorCodigo(codigo);
            if (itemPedido == null) {
                throw new NegocioException("Não existe esse item de pedido");
            }

            itemPedidoDAO.beginTransaction();
            itemPedidoDAO.excluir(itemPedido);
            itemPedidoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            itemPedidoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o item de pedido - " + ex.getMessage());
        }
    }

    public List<ItemPedidoDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(itemPedidoDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar itens de pedido - " + ex.getMessage());
        }
    }

    public ItemPedidoDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(itemPedidoDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar item de pedido pelo código - " + ex.getMessage());
        }
    }

    public List<ItemPedidoDTO> toDTOAll(List<ItemPedido> listaItemPedido) {
        List<ItemPedidoDTO> listaDTO = new ArrayList<>();

        for (ItemPedido itemPedido : listaItemPedido) {
            listaDTO.add(this.toDTO(itemPedido));
        }
        return listaDTO;
    }

    public ItemPedidoDTO toDTO(ItemPedido itemPedido) {
        return this.modelMapper.map(itemPedido, ItemPedidoDTO.class);
    }

    public ItemPedido toEntity(ItemPedidoDTO itemPedidoDTO) {
        return this.modelMapper.map(itemPedidoDTO, ItemPedido.class);
    }
}
