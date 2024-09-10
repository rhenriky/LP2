package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.EstadoOrdemProducaoDTO;
import ifmt.cba.entity.EstadoOrdemProducao;
import ifmt.cba.persistencia.EstadoOrdemProducaoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class EstadoOrdemProducaoNegocio {

    private ModelMapper modelMapper;
    private EstadoOrdemProducaoDAO estadoOrdemProducaoDAO;

    public EstadoOrdemProducaoNegocio(EstadoOrdemProducaoDAO estadoOrdemProducaoDAO) {
        this.estadoOrdemProducaoDAO = estadoOrdemProducaoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(EstadoOrdemProducaoDTO estadoOrdemProducaoDTO) throws NegocioException {
        EstadoOrdemProducao estadoOrdemProducao = this.toEntity(estadoOrdemProducaoDTO);
        String mensagemErros = estadoOrdemProducao.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            estadoOrdemProducaoDAO.beginTransaction();
            estadoOrdemProducaoDAO.incluir(estadoOrdemProducao);
            estadoOrdemProducaoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            estadoOrdemProducaoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o estado da ordem de produção - " + ex.getMessage());
        }
    }

    public void alterar(EstadoOrdemProducaoDTO estadoOrdemProducaoDTO) throws NegocioException {
        EstadoOrdemProducao estadoOrdemProducao = this.toEntity(estadoOrdemProducaoDTO);
        String mensagemErros = estadoOrdemProducao.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (estadoOrdemProducaoDAO.buscarPorCodigo(estadoOrdemProducao.getCodigo()) == null) {
                throw new NegocioException("Não existe esse estado da ordem de produção");
            }

            estadoOrdemProducaoDAO.beginTransaction();
            estadoOrdemProducaoDAO.alterar(estadoOrdemProducao);
            estadoOrdemProducaoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            estadoOrdemProducaoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o estado da ordem de produção - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            EstadoOrdemProducao estadoOrdemProducao = estadoOrdemProducaoDAO.buscarPorCodigo(codigo);
            if (estadoOrdemProducao == null) {
                throw new NegocioException("Não existe esse estado da ordem de produção");
            }

            estadoOrdemProducaoDAO.beginTransaction();
            estadoOrdemProducaoDAO.excluir(estadoOrdemProducao);
            estadoOrdemProducaoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            estadoOrdemProducaoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o estado da ordem de produção - " + ex.getMessage());
        }
    }

    public List<EstadoOrdemProducaoDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(estadoOrdemProducaoDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar estados da ordem de produção - " + ex.getMessage());
        }
    }

    public EstadoOrdemProducaoDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(estadoOrdemProducaoDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar estado da ordem de produção pelo código - " + ex.getMessage());
        }
    }

    public List<EstadoOrdemProducaoDTO> toDTOAll(List<EstadoOrdemProducao> listaEstadoOrdemProducao) {
        List<EstadoOrdemProducaoDTO> listaDTO = new ArrayList<>();

        for (EstadoOrdemProducao estadoOrdemProducao : listaEstadoOrdemProducao) {
            listaDTO.add(this.toDTO(estadoOrdemProducao));
        }
        return listaDTO;
    }

    public EstadoOrdemProducaoDTO toDTO(EstadoOrdemProducao estadoOrdemProducao) {
        return this.modelMapper.map(estadoOrdemProducao, EstadoOrdemProducaoDTO.class);
    }

    public EstadoOrdemProducao toEntity(EstadoOrdemProducaoDTO estadoOrdemProducaoDTO) {
        return this.modelMapper.map(estadoOrdemProducaoDTO, EstadoOrdemProducao.class);
    }
}
