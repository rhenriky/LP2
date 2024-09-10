package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.OrdemProducaoDTO;
import ifmt.cba.entity.OrdemProducao;
import ifmt.cba.persistencia.OrdemProducaoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class OrdemProducaoNegocio {

    private ModelMapper modelMapper;
    private OrdemProducaoDAO ordemProducaoDAO;

    public OrdemProducaoNegocio(OrdemProducaoDAO ordemProducaoDAO) {
        this.ordemProducaoDAO = ordemProducaoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(OrdemProducaoDTO ordemProducaoDTO) throws NegocioException {
        OrdemProducao ordemProducao = this.toEntity(ordemProducaoDTO);
        String mensagemErros = ordemProducao.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            ordemProducaoDAO.beginTransaction();
            ordemProducaoDAO.incluir(ordemProducao);
            ordemProducaoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            ordemProducaoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir a ordem de produção - " + ex.getMessage());
        }
    }

    public void alterar(OrdemProducaoDTO ordemProducaoDTO) throws NegocioException {
        OrdemProducao ordemProducao = this.toEntity(ordemProducaoDTO);
        String mensagemErros = ordemProducao.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (ordemProducaoDAO.buscarPorCodigo(ordemProducao.getCodigo()) == null) {
                throw new NegocioException("Não existe essa ordem de produção");
            }

            ordemProducaoDAO.beginTransaction();
            ordemProducaoDAO.alterar(ordemProducao);
            ordemProducaoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            ordemProducaoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar a ordem de produção - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            OrdemProducao ordemProducao = ordemProducaoDAO.buscarPorCodigo(codigo);
            if (ordemProducao == null) {
                throw new NegocioException("Não existe essa ordem de produção");
            }

            ordemProducaoDAO.beginTransaction();
            ordemProducaoDAO.excluir(ordemProducao);
            ordemProducaoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            ordemProducaoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir a ordem de produção - " + ex.getMessage());
        }
    }

    public List<OrdemProducaoDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(ordemProducaoDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar ordens de produção - " + ex.getMessage());
        }
    }

    public OrdemProducaoDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(ordemProducaoDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar ordem de produção pelo código - " + ex.getMessage());
        }
    }

    public List<OrdemProducaoDTO> toDTOAll(List<OrdemProducao> listaOrdemProducao) {
        List<OrdemProducaoDTO> listaDTO = new ArrayList<>();

        for (OrdemProducao ordemProducao : listaOrdemProducao) {
            listaDTO.add(this.toDTO(ordemProducao));
        }
        return listaDTO;
    }

    public OrdemProducaoDTO toDTO(OrdemProducao ordemProducao) {
        return this.modelMapper.map(ordemProducao, OrdemProducaoDTO.class);
    }

    public OrdemProducao toEntity(OrdemProducaoDTO ordemProducaoDTO) {
        return this.modelMapper.map(ordemProducaoDTO, OrdemProducao.class);
    }
}
