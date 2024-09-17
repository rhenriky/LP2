package ifmt.cba.negocio;

import ifmt.cba.dto.ProdutoDTO;
import ifmt.cba.entity.Produto;
import ifmt.cba.persistencia.ProdutoDAO;
import ifmt.cba.persistencia.PersistenciaException;
import jakarta.persistence.EntityTransaction;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoNegocio {

    private ProdutoDAO produtoDAO;
    private ModelMapper modelMapper;

    public ProdutoNegocio(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(ProdutoDTO produtoDTO) throws NegocioException, PersistenciaException {
        Produto produto = modelMapper.map(produtoDTO, Produto.class);

        EntityTransaction transaction = produtoDAO.getEntityManager().getTransaction();
        try {
            transaction.begin();
            produtoDAO.incluir(produto);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new NegocioException("Erro ao inserir produto: " + e.getMessage());
        }
    }

    public List<ProdutoDTO> buscarTodosOrdenadosPorNome() throws PersistenciaException {
        return produtoDAO.buscarTodosOrdenadosPorNome().stream()
                .map(produto -> modelMapper.map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProdutoDTO> buscarProdutosComEstoqueBaixo() throws PersistenciaException {
        return produtoDAO.buscarProdutosComEstoqueBaixo().stream()
                .map(produto -> modelMapper.map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }
}
