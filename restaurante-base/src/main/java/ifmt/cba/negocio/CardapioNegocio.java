package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.CardapioDTO;
import ifmt.cba.entity.Cardapio;
import ifmt.cba.persistencia.CardapioDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class CardapioNegocio {

    private ModelMapper modelMapper;
    private CardapioDAO cardapioDAO;

    public CardapioNegocio(CardapioDAO cardapioDAO) {
        this.cardapioDAO = cardapioDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(CardapioDTO cardapioDTO) throws NegocioException {
        Cardapio cardapio = this.toEntity(cardapioDTO);
        String mensagemErros = cardapio.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            cardapioDAO.beginTransaction();
            cardapioDAO.incluir(cardapio);
            cardapioDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            cardapioDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o cardápio - " + ex.getMessage());
        }
    }

    public void alterar(CardapioDTO cardapioDTO) throws NegocioException {
        Cardapio cardapio = this.toEntity(cardapioDTO);
        String mensagemErros = cardapio.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (cardapioDAO.buscarPorCodigo(cardapio.getCodigo()) == null) {
                throw new NegocioException("Não existe esse cardápio");
            }

            cardapioDAO.beginTransaction();
            cardapioDAO.alterar(cardapio);
            cardapioDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            cardapioDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o cardápio - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            Cardapio cardapio = cardapioDAO.buscarPorCodigo(codigo);
            if (cardapio == null) {
                throw new NegocioException("Não existe esse cardápio");
            }

            cardapioDAO.beginTransaction();
            cardapioDAO.excluir(cardapio);
            cardapioDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            cardapioDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o cardápio - " + ex.getMessage());
        }
    }

    public List<CardapioDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(cardapioDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar cardápios - " + ex.getMessage());
        }
    }

    public CardapioDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(cardapioDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar cardápio pelo código - " + ex.getMessage());
        }
    }

    public List<CardapioDTO> toDTOAll(List<Cardapio> listaCardapio) {
        List<CardapioDTO> listaDTO = new ArrayList<>();

        for (Cardapio cardapio : listaCardapio) {
            listaDTO.add(this.toDTO(cardapio));
        }
        return listaDTO;
    }

    public CardapioDTO toDTO(Cardapio cardapio) {
        return this.modelMapper.map(cardapio, CardapioDTO.class);
    }

    public Cardapio toEntity(CardapioDTO cardapioDTO) {
        return this.modelMapper.map(cardapioDTO, Cardapio.class);
    }
}
