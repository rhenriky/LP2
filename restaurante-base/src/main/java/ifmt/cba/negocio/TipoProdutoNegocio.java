package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.TipoProdutoDTO;
import ifmt.cba.entity.TipoProduto;
import ifmt.cba.persistencia.TipoProdutoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class TipoProdutoNegocio {

    private ModelMapper modelMapper;
    private TipoProdutoDAO tipoProdutoDAO;

    public TipoProdutoNegocio(TipoProdutoDAO tipoProdutoDAO) {
        this.tipoProdutoDAO = tipoProdutoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(TipoProdutoDTO tipoProdutoDTO) throws NegocioException {
        TipoProduto tipoProduto = this.toEntity(tipoProdutoDTO);
        String mensagemErros = tipoProduto.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            tipoProdutoDAO.beginTransaction();
            tipoProdutoDAO.incluir(tipoProduto);
            tipoProdutoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            tipoProdutoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o tipo de produto - " + ex.getMessage());
        }
    }

    public void alterar(TipoProdutoDTO tipoProdutoDTO) throws NegocioException {
        TipoProduto tipoProduto = this.toEntity(tipoProdutoDTO);
        String mensagemErros = tipoProduto.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (tipoProdutoDAO.buscarPorCodigo(tipoProduto.getCodigo()) == null) {
                throw new NegocioException("Não existe esse tipo de produto");
            }

            tipoProdutoDAO.beginTransaction();
            tipoProdutoDAO.alterar(tipoProduto);
            tipoProdutoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            tipoProdutoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o tipo de produto - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            TipoProduto tipoProduto = tipoProdutoDAO.buscarPorCodigo(codigo);
            if (tipoProduto == null) {
                throw new NegocioException("Não existe esse tipo de produto");
            }

            tipoProdutoDAO.beginTransaction();
            tipoProdutoDAO.excluir(tipoProduto);
            tipoProdutoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            tipoProdutoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o tipo de produto - " + ex.getMessage());
        }
    }

    public List<TipoProdutoDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(tipoProdutoDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar tipos de produto - " + ex.getMessage());
        }
    }

    public TipoProdutoDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(tipoProdutoDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar tipo de produto pelo código - " + ex.getMessage());
        }
    }

    public List<TipoProdutoDTO> toDTOAll(List<TipoProduto> listaTipoProduto) {
        List<TipoProdutoDTO> listaDTO = new ArrayList<>();

        for (TipoProduto tipoProduto : listaTipoProduto) {
            listaDTO.add(this.toDTO(tipoProduto));
        }
        return listaDTO;
    }

    public TipoProdutoDTO toDTO(TipoProduto tipoProduto) {
        return this.modelMapper.map(tipoProduto, TipoProdutoDTO.class);
    }

    public TipoProduto toEntity(TipoProdutoDTO tipoProdutoDTO) {
        return this.modelMapper.map(tipoProdutoDTO, TipoProduto.class);
    }
}
