package ifmt.cba.execucao;

import ifmt.cba.entity.ItemPedido;
import ifmt.cba.entity.Pedido;
import ifmt.cba.entity.PreparoProduto;
import ifmt.cba.negocio.ItemPedidoNegocio;

import ifmt.cba.persistencia.ItemPedidoDAO;
import ifmt.cba.persistencia.PedidoDAO;
import ifmt.cba.persistencia.PreparoProdutoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

import java.util.Date;

public class AppInserirItemPedido {
    public static void main(String[] args) {
        try {
            // Instanciar DAOs e classes de negócio
            PreparoProdutoDAO preparoProdutoDAO = new PreparoProdutoDAO(FabricaEntityManager.getEntityManagerProducao());
            PedidoDAO pedidoDAO = new PedidoDAO(FabricaEntityManager.getEntityManagerProducao());
            ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(FabricaEntityManager.getEntityManagerProducao());
            ItemPedidoNegocio itemPedidoNegocio = new ItemPedidoNegocio(itemPedidoDAO);

            // Criar e configurar um PreparoProduto existente (ou buscar do banco de dados)
            PreparoProduto preparoProduto = preparoProdutoDAO.buscarPorCodigo(1); // Supondo que o produto já existe no BD

            if (preparoProduto == null) {
                preparoProduto = new PreparoProduto();
                preparoProduto.setDescricao("Preparação Especial");
                preparoProduto.setTempoPreparo(45);
                preparoProdutoDAO.incluir(preparoProduto);
            }

            // Criar e configurar um Pedido existente (ou criar um novo)
            Pedido pedido = new Pedido();
            pedido.setDataPedido(new Date());
            pedidoDAO.incluir(pedido); // Inserindo o pedido no banco de dados

            // Criar e configurar o primeiro ItemPedido
            ItemPedido itemPedido1 = new ItemPedido();
            itemPedido1.setPreparoProduto(preparoProduto);
            itemPedido1.setQuantidadePorcao(2);
            itemPedido1.setQuantidade(5);
            itemPedido1.setPedido(pedido);

            // Inserir o primeiro item do pedido
            itemPedidoNegocio.inserir(itemPedido1);

            // Criar e configurar o segundo ItemPedido
            ItemPedido itemPedido2 = new ItemPedido();
            itemPedido2.setPreparoProduto(preparoProduto);
            itemPedido2.setQuantidadePorcao(1);
            itemPedido2.setQuantidade(3);
            itemPedido2.setPedido(pedido);

            // Inserir o segundo item do pedido
            itemPedidoNegocio.inserir(itemPedido2);

            // Confirmar a transação
            FabricaEntityManager.getEntityManagerProducao().getTransaction().commit();

            System.out.println("Itens do pedido inseridos com sucesso!");

        } catch (PersistenciaException e) {
            e.printStackTrace();
            try {
                FabricaEntityManager.getEntityManagerProducao().getTransaction().rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            FabricaEntityManager.closeEntityManager();
        }
    }
}
