package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.ItemOrdemProducaoDTO;
import ifmt.cba.entity.ItemOrdemProducao;
import ifmt.cba.persistencia.ItemOrdemProducaoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class ItemOrdemProducaoNegocio {

    private ModelMapper modelMapper;
    private ItemOrdemProducaoDAO itemOrdemProducaoDAO;

    public ItemOrdemProducaoNegocio(ItemOrdemProducaoDAO itemOrdemProducaoDAO) {
        this.itemOrdemProducaoDAO = itemOrdemProducaoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(ItemOrdemProducaoDTO itemOrdemProducaoDTO) throws NegocioException {
        ItemOrdemProducao itemOrdemProducao = this.toEntity(itemOrdemProducaoDTO);
        String mensagemErros = itemOrdemProducao.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            itemOrdemProducaoDAO.beginTransaction();
            itemOrdemProducaoDAO.incluir(itemOrdemProducao);
            itemOrdemProducaoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            itemOrdemProducaoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o item de ordem de produção - " + ex.getMessage());
        }
    }

    public void alterar(ItemOrdemProducaoDTO itemOrdemProducaoDTO) throws NegocioException {
        ItemOrdemProducao itemOrdemProducao = this.toEntity(itemOrdemProducaoDTO);
        String mensagemErros = itemOrdemProducao.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (itemOrdemProducaoDAO.buscarPorCodigo(itemOrdemProducao.getCodigo()) == null) {
                throw new NegocioException("Não existe esse item de ordem de produção");
            }

            itemOrdemProducaoDAO.beginTransaction();
            itemOrdemProducaoDAO.alterar(itemOrdemProducao);
            itemOrdemProducaoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            itemOrdemProducaoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o item de ordem de produção - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            ItemOrdemProducao itemOrdemProducao = itemOrdemProducaoDAO.buscarPorCodigo(codigo);
            if (itemOrdemProducao == null) {
                throw new NegocioException("Não existe esse item de ordem de produção");
            }

            itemOrdemProducaoDAO.beginTransaction();
            itemOrdemProducaoDAO.excluir(itemOrdemProducao);
            itemOrdemProducaoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            itemOrdemProducaoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o item de ordem de produção - " + ex.getMessage());
        }
    }

    public List<ItemOrdemProducaoDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(itemOrdemProducaoDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar itens de ordem de produção - " + ex.getMessage());
        }
    }

    public ItemOrdemProducaoDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(itemOrdemProducaoDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar item de ordem de produção pelo código - " + ex.getMessage());
        }
    }

    public List<ItemOrdemProducaoDTO> toDTOAll(List<ItemOrdemProducao> listaItemOrdemProducao) {
        List<ItemOrdemProducaoDTO> listaDTO = new ArrayList<>();

        for (ItemOrdemProducao itemOrdemProducao : listaItemOrdemProducao) {
            listaDTO.add(this.toDTO(itemOrdemProducao));
        }
        return listaDTO;
    }

    public ItemOrdemProducaoDTO toDTO(ItemOrdemProducao itemOrdemProducao) {
        return this.modelMapper.map(itemOrdemProducao, ItemOrdemProducaoDTO.class);
    }

    public ItemOrdemProducao toEntity(ItemOrdemProducaoDTO itemOrdemProducaoDTO) {
        return this.modelMapper.map(itemOrdemProducaoDTO, ItemOrdemProducao.class);
    }
}
