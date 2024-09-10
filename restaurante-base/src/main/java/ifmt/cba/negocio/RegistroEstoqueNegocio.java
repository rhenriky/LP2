package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.RegistroEstoqueDTO;
import ifmt.cba.entity.RegistroEstoque;
import ifmt.cba.persistencia.RegistroEstoqueDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class RegistroEstoqueNegocio {

    private ModelMapper modelMapper;
    private RegistroEstoqueDAO registroEstoqueDAO;

    public RegistroEstoqueNegocio(RegistroEstoqueDAO registroEstoqueDAO) {
        this.registroEstoqueDAO = registroEstoqueDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(RegistroEstoqueDTO registroEstoqueDTO) throws NegocioException {
        RegistroEstoque registroEstoque = this.toEntity(registroEstoqueDTO);
        String mensagemErros = registroEstoque.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            registroEstoqueDAO.beginTransaction();
            registroEstoqueDAO.incluir(registroEstoque);
            registroEstoqueDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            registroEstoqueDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o registro de estoque - " + ex.getMessage());
        }
    }

    public void alterar(RegistroEstoqueDTO registroEstoqueDTO) throws NegocioException {
        RegistroEstoque registroEstoque = this.toEntity(registroEstoqueDTO);
        String mensagemErros = registroEstoque.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (registroEstoqueDAO.buscarPorCodigo(registroEstoque.getCodigo()) == null) {
                throw new NegocioException("Não existe esse registro de estoque");
            }

            registroEstoqueDAO.beginTransaction();
            registroEstoqueDAO.alterar(registroEstoque);
            registroEstoqueDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            registroEstoqueDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o registro de estoque - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            RegistroEstoque registroEstoque = registroEstoqueDAO.buscarPorCodigo(codigo);
            if (registroEstoque == null) {
                throw new NegocioException("Não existe esse registro de estoque");
            }

            registroEstoqueDAO.beginTransaction();
            registroEstoqueDAO.excluir(registroEstoque);
            registroEstoqueDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            registroEstoqueDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o registro de estoque - " + ex.getMessage());
        }
    }

    public List<RegistroEstoqueDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(registroEstoqueDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar registros de estoque - " + ex.getMessage());
        }
    }

    public RegistroEstoqueDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(registroEstoqueDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar registro de estoque pelo código - " + ex.getMessage());
        }
    }

    public List<RegistroEstoqueDTO> toDTOAll(List<RegistroEstoque> listaRegistroEstoque) {
        List<RegistroEstoqueDTO> listaDTO = new ArrayList<>();

        for (RegistroEstoque registroEstoque : listaRegistroEstoque) {
            listaDTO.add(this.toDTO(registroEstoque));
        }
        return listaDTO;
    }

    public RegistroEstoqueDTO toDTO(RegistroEstoque registroEstoque) {
        return this.modelMapper.map(registroEstoque, RegistroEstoqueDTO.class);
    }

    public RegistroEstoque toEntity(RegistroEstoqueDTO registroEstoqueDTO) {
        return this.modelMapper.map(registroEstoqueDTO, RegistroEstoque.class);
    }
}
