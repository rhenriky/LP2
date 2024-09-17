package ifmt.cba.negocio;

import ifmt.cba.dto.EstadoOrdemProducaoDTO;
import ifmt.cba.entity.EstadoOrdemProducao;
import ifmt.cba.persistencia.EstadoOrdemProducaoDAO;
import ifmt.cba.persistencia.PersistenciaException;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EstadoOrdemProducaoNegocio {

    private final EstadoOrdemProducaoDAO estadoOrdemProducaoDAO;

    public EstadoOrdemProducaoNegocio(EntityManager entityManager) throws PersistenciaException {
        this.estadoOrdemProducaoDAO = new EstadoOrdemProducaoDAO(entityManager);
    }

    public void inserir(EstadoOrdemProducaoDTO estadoOrdemProducaoDTO) throws PersistenciaException {
        EstadoOrdemProducao estadoOrdemProducao = new EstadoOrdemProducao();
        
        // Popula a entidade a partir do DTO
        estadoOrdemProducao.setDescricao(estadoOrdemProducaoDTO.getDescricao());

        try {
            estadoOrdemProducaoDAO.beginTransaction();
            estadoOrdemProducaoDAO.inserir(estadoOrdemProducao);
            estadoOrdemProducaoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            estadoOrdemProducaoDAO.rollbackTransaction();
            throw new PersistenciaException("Erro ao inserir o estado de ordem de produção - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws PersistenciaException {
        EstadoOrdemProducao estadoOrdemProducao = estadoOrdemProducaoDAO.buscarPorCodigo(codigo);

        if (estadoOrdemProducao == null) {
            throw new PersistenciaException("Estado de Ordem de Produção não encontrado.");
        }

        try {
            estadoOrdemProducaoDAO.beginTransaction();
            estadoOrdemProducaoDAO.excluir(estadoOrdemProducao);
            estadoOrdemProducaoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            estadoOrdemProducaoDAO.rollbackTransaction();
            throw new PersistenciaException("Erro ao excluir o estado de ordem de produção - " + ex.getMessage());
        }
    }

    public List<EstadoOrdemProducao> buscarTodos() throws PersistenciaException {
        return estadoOrdemProducaoDAO.buscarTodos();
    }

    public EstadoOrdemProducao buscarPorCodigo(int codigo) throws PersistenciaException {
        return estadoOrdemProducaoDAO.buscarPorCodigo(codigo);
    }
}
