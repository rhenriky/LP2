package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.EntregadorDTO;
import ifmt.cba.entity.Entregador;
import ifmt.cba.persistencia.EntregadorDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class EntregadorNegocio {

    private ModelMapper modelMapper;
    private EntregadorDAO entregadorDAO;

    public EntregadorNegocio(EntregadorDAO entregadorDAO) {
        this.entregadorDAO = entregadorDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(EntregadorDTO entregadorDTO) throws NegocioException {
        Entregador entregador = this.toEntity(entregadorDTO);
        String mensagemErros = entregador.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            entregadorDAO.beginTransaction();
            entregadorDAO.incluir(entregador);
            entregadorDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            entregadorDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o entregador - " + ex.getMessage());
        }
    }

    public void alterar(EntregadorDTO entregadorDTO) throws NegocioException {
        Entregador entregador = this.toEntity(entregadorDTO);
        String mensagemErros = entregador.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (entregadorDAO.buscarPorCodigo(entregador.getCodigo()) == null) {
                throw new NegocioException("Não existe esse entregador");
            }

            entregadorDAO.beginTransaction();
            entregadorDAO.alterar(entregador);
            entregadorDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            entregadorDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o entregador - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            Entregador entregador = entregadorDAO.buscarPorCodigo(codigo);
            if (entregador == null) {
                throw new NegocioException("Não existe esse entregador");
            }

            entregadorDAO.beginTransaction();
            entregadorDAO.excluir(entregador);
            entregadorDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            entregadorDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o entregador - " + ex.getMessage());
        }
    }

    public List<EntregadorDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(entregadorDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar entregadores - " + ex.getMessage());
        }
    }

    public EntregadorDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(entregadorDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar entregador pelo código - " + ex.getMessage());
        }
    }

    public List<EntregadorDTO> toDTOAll(List<Entregador> listaEntregador) {
        List<EntregadorDTO> listaDTO = new ArrayList<>();

        for (Entregador entregador : listaEntregador) {
            listaDTO.add(this.toDTO(entregador));
        }
        return listaDTO;
    }

    public EntregadorDTO toDTO(Entregador entregador) {
        return this.modelMapper.map(entregador, EntregadorDTO.class);
    }

    public Entregador toEntity(EntregadorDTO entregadorDTO) {
        return this.modelMapper.map(entregadorDTO, Entregador.class);
    }
}
