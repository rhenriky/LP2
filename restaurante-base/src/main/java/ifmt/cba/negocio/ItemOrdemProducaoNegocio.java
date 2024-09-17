package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import ifmt.cba.dto.ItemOrdemProducaoDTO;
import ifmt.cba.persistencia.ItemOrdemProducaoDAO;
import ifmt.cba.persistencia.PersistenciaException;
import ifmt.cba.entity.ItemOrdemProducao;

public class ItemOrdemProducaoNegocio {

    private ModelMapper modelMapper;
    private ItemOrdemProducaoDAO itemOrdemProducaoDAO;

    public ItemOrdemProducaoNegocio(ItemOrdemProducaoDAO itemOrdemProducaoDAO) {
        this.itemOrdemProducaoDAO = itemOrdemProducaoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(ItemOrdemProducaoDTO itemOrdemProducaoDTO) throws NegocioException, PersistenciaException {
        ItemOrdemProducao itemOrdemProducao = this.toEntity(itemOrdemProducaoDTO);
        String mensagemErros = itemOrdemProducao.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            itemOrdemProducaoDAO.beginTransaction();
            itemOrdemProducaoDAO.inserir(itemOrdemProducao);
            itemOrdemProducaoDAO.commitTransaction();
        } catch (PersistenciaException e) {
            itemOrdemProducaoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o item de ordem de produção - " + e.getMessage());
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

    public List<ItemOrdemProducaoDTO> pesquisarOrdensProducoesRegistradas() throws NegocioException {
        try {
            List<ItemOrdemProducao> listaItemOrdemProducao = itemOrdemProducaoDAO.buscarOrdensProducoesRegistradas();
            return this.toDTOAll(listaItemOrdemProducao);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar ordens de produção registradas - " + ex.getMessage());
        }
    }

    public List<ItemOrdemProducaoDTO> pesquisarOrdensProducoesProcessadas() throws NegocioException {
        try {
            List<ItemOrdemProducao> listaItemOrdemProducao = itemOrdemProducaoDAO.buscarOrdensProducoesProcessadas();
            return this.toDTOAll(listaItemOrdemProducao);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar ordens de produção processadas - " + ex.getMessage());
        }
    }

    private ItemOrdemProducaoDTO toDTO(ItemOrdemProducao itemOrdemProducao) {
        return modelMapper.map(itemOrdemProducao, ItemOrdemProducaoDTO.class);
    }

    private List<ItemOrdemProducaoDTO> toDTOAll(List<ItemOrdemProducao> listaItemOrdemProducao) {
        List<ItemOrdemProducaoDTO> listaItemOrdemProducaoDTO = new ArrayList<>();
        for (ItemOrdemProducao itemOrdemProducao : listaItemOrdemProducao) {
            listaItemOrdemProducaoDTO.add(toDTO(itemOrdemProducao));
        }
        return listaItemOrdemProducaoDTO;
    }

    private ItemOrdemProducao toEntity(ItemOrdemProducaoDTO itemOrdemProducaoDTO) {
        return modelMapper.map(itemOrdemProducaoDTO, ItemOrdemProducao.class);
    }
}
