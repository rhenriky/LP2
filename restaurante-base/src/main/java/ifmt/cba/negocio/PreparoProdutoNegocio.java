package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.PreparoProdutoDTO;
import ifmt.cba.entity.PreparoProduto;
import ifmt.cba.persistencia.PreparoProdutoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class PreparoProdutoNegocio {

    private ModelMapper modelMapper;
    private PreparoProdutoDAO preparoProdutoDAO;

    public PreparoProdutoNegocio(PreparoProdutoDAO preparoProdutoDAO) {
        this.preparoProdutoDAO = preparoProdutoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(PreparoProduto preparoProduto1) throws NegocioException {
        PreparoProduto preparoProduto = this.toEntity(preparoProduto1);
        String mensagemErros = preparoProduto.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            preparoProdutoDAO.beginTransaction();
            preparoProdutoDAO.incluir(preparoProduto);
            preparoProdutoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            preparoProdutoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o preparo de produto - " + ex.getMessage());
        }
    }

    public void alterar(PreparoProdutoDTO preparoProdutoDTO) throws NegocioException {
        PreparoProduto preparoProduto = this.toEntity(preparoProdutoDTO);
        String mensagemErros = preparoProduto.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (preparoProdutoDAO.buscarPorCodigo(preparoProduto.getCodigo()) == null) {
                throw new NegocioException("Não existe esse preparo de produto");
            }

            preparoProdutoDAO.beginTransaction();
            preparoProdutoDAO.alterar(preparoProduto);
            preparoProdutoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            preparoProdutoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o preparo de produto - " + ex.getMessage());
        }
    }

    private PreparoProduto toEntity(PreparoProdutoDTO preparoProdutoDTO) {
       
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            PreparoProduto preparoProduto = preparoProdutoDAO.buscarPorCodigo(codigo);
            if (preparoProduto == null) {
                throw new NegocioException("Não existe esse preparo de produto");
            }

            preparoProdutoDAO.beginTransaction();
            preparoProdutoDAO.excluir(preparoProduto);
            preparoProdutoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            preparoProdutoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o preparo de produto - " + ex.getMessage());
        }
    }

    public List<PreparoProdutoDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(preparoProdutoDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar preparos de produto - " + ex.getMessage());
        }
    }

    public PreparoProdutoDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            PreparoProduto preparoProduto = preparoProdutoDAO.buscarPorCodigo(codigo);
            if (preparoProduto == null) {
                throw new NegocioException("Preparo de produto não encontrado.");
            }
            return this.toDTO(preparoProduto);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar preparo de produto pelo código - " + ex.getMessage());
        }
    }

    public List<PreparoProdutoDTO> toDTOAll(List<PreparoProduto> listaPreparoProduto) {
        List<PreparoProdutoDTO> listaDTO = new ArrayList<>();
        for (PreparoProduto preparoProduto : listaPreparoProduto) {
            listaDTO.add(this.toDTO(preparoProduto));
        }
        return listaDTO;
    }

    public PreparoProdutoDTO toDTO(PreparoProduto preparoProduto) {
        return this.modelMapper.map(preparoProduto, PreparoProdutoDTO.class);
    }

    public PreparoProduto toEntity(PreparoProduto preparoProduto1) {
        return this.modelMapper.map(preparoProduto1, PreparoProduto.class);
    }
}
