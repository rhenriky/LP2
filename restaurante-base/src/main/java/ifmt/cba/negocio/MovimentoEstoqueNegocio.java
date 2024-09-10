package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.MovimentoEstoqueDTO;
import ifmt.cba.entity.MovimentoEstoque;
import ifmt.cba.persistencia.MovimentoEstoqueDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class MovimentoEstoqueNegocio {

    private ModelMapper modelMapper;
    private MovimentoEstoqueDAO movimentoEstoqueDAO;

    public MovimentoEstoqueNegocio(MovimentoEstoqueDAO movimentoEstoqueDAO) {
        this.movimentoEstoqueDAO = movimentoEstoqueDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(MovimentoEstoqueDTO movimentoEstoqueDTO) throws NegocioException {
        MovimentoEstoque movimentoEstoque = this.toEntity(movimentoEstoqueDTO);
        String mensagemErros = movimentoEstoque.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            movimentoEstoqueDAO.beginTransaction();
            movimentoEstoqueDAO.incluir(movimentoEstoque);
            movimentoEstoqueDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            movimentoEstoqueDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o movimento de estoque - " + ex.getMessage());
        }
    }

    public void alterar(MovimentoEstoqueDTO movimentoEstoqueDTO) throws NegocioException {
        MovimentoEstoque movimentoEstoque = this.toEntity(movimentoEstoqueDTO);
        String mensagemErros = movimentoEstoque.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (movimentoEstoqueDAO.buscarPorCodigo(movimentoEstoque.getCodigo()) == null) {
                throw new NegocioException("Não existe esse movimento de estoque");
            }

            movimentoEstoqueDAO.beginTransaction();
            movimentoEstoqueDAO.alterar(movimentoEstoque);
            movimentoEstoqueDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            movimentoEstoqueDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o movimento de estoque - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            MovimentoEstoque movimentoEstoque = movimentoEstoqueDAO.buscarPorCodigo(codigo);
            if (movimentoEstoque == null) {
                throw new NegocioException("Não existe esse movimento de estoque");
            }

            movimentoEstoqueDAO.beginTransaction();
            movimentoEstoqueDAO.excluir(movimentoEstoque);
            movimentoEstoqueDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            movimentoEstoqueDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o movimento de estoque - " + ex.getMessage());
        }
    }

    public List<MovimentoEstoqueDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(movimentoEstoqueDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar movimentos de estoque - " + ex.getMessage());
        }
    }

    public MovimentoEstoqueDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(movimentoEstoqueDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar movimento de estoque pelo código - " + ex.getMessage());
        }
    }

    public List<MovimentoEstoqueDTO> toDTOAll(List<MovimentoEstoque> listaMovimentoEstoque) {
        List<MovimentoEstoqueDTO> listaDTO = new ArrayList<>();

        for (MovimentoEstoque movimentoEstoque : listaMovimentoEstoque) {
            listaDTO.add(this.toDTO(movimentoEstoque));
        }
        return listaDTO;
    }

    public MovimentoEstoqueDTO toDTO(MovimentoEstoque movimentoEstoque) {
        return this.modelMapper.map(movimentoEstoque, MovimentoEstoqueDTO.class);
    }

    public MovimentoEstoque toEntity(MovimentoEstoqueDTO movimentoEstoqueDTO) {
        return this.modelMapper.map(movimentoEstoqueDTO, MovimentoEstoque.class);
    }
}
