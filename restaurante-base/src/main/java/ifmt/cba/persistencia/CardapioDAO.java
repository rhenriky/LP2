package ifmt.cba.persistencia;

import java.util.List;
import ifmt.cba.entity.Cardapio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class CardapioDAO extends DAO<Cardapio> {

    public CardapioDAO(EntityManager entityManager) throws PersistenciaException {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<Cardapio> buscarTodos() throws PersistenciaException {
        List<Cardapio> listaCardapio;
        try {
            Query query = this.entityManager.createQuery("SELECT c FROM Cardapio c ORDER BY c.nome");
            listaCardapio = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção de todos os cardápios - " + ex.getMessage());
        }
        return listaCardapio;
    }

    @SuppressWarnings("unchecked")
    public List<Cardapio> buscarItensPorCardapio(int idCardapio) throws PersistenciaException {
        List<Cardapio> listaCardapio;
        try {
            Query query = this.entityManager.createQuery("SELECT c FROM Cardapio c WHERE c.id = :idCardapio");
            query.setParameter("idCardapio", idCardapio);
            listaCardapio = query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção dos itens do cardápio - " + ex.getMessage());
        }
        return listaCardapio;
    }

    public Cardapio buscarPorCodigo(int codigo) throws PersistenciaException {
        Cardapio cardapio;
        try {
            Query query = this.entityManager.createQuery("SELECT c FROM Cardapio c WHERE c.codigo = :codigo");
            query.setParameter("codigo", codigo);
            cardapio = (Cardapio) query.getSingleResult();
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção do cardápio pelo código - " + ex.getMessage());
        }
        return cardapio;
    }
}
