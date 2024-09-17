package ifmt.cba.negocio;

import java.util.List;
import ifmt.cba.entity.MovimentoEstoque;
import ifmt.cba.persistencia.MovimentoEstoqueDAO;
import ifmt.cba.persistencia.PersistenciaException;
import jakarta.persistence.EntityManager;

public class MovimentoEstoqueNegocio {

    private MovimentoEstoqueDAO movimentoEstoqueDAO;

    public MovimentoEstoqueNegocio(EntityManager entityManager) throws PersistenciaException {
        this.movimentoEstoqueDAO = new MovimentoEstoqueDAO(entityManager);
    }

    public List<MovimentoEstoque> buscarTodos() throws PersistenciaException {
        return movimentoEstoqueDAO.buscarTodos();
    }

    public MovimentoEstoque buscarPorCodigo(int codigo) throws PersistenciaException {
        return movimentoEstoqueDAO.buscarPorCodigo(codigo);
    }

    public void inserir(MovimentoEstoque movimentoEstoque) throws PersistenciaException {
        if (movimentoEstoque.getDescricao() == null || movimentoEstoque.getDescricao().length() < 3) {
            throw new PersistenciaException("A descrição do movimento de estoque deve ter pelo menos 3 caracteres.");
        }
        movimentoEstoqueDAO.inserir(movimentoEstoque);
    }
}
