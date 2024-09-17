package ifmt.cba.execucao;

import java.util.List;

import ifmt.cba.dto.BairroDTO;
import ifmt.cba.dto.ClienteDTO;
import ifmt.cba.negocio.BairroNegocio;
import ifmt.cba.negocio.ClienteNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.BairroDAO;
import ifmt.cba.persistencia.ClienteDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirCliente {
    public static void main(String[] args) {
        try {
            ClienteDAO clienteDAO = new ClienteDAO(FabricaEntityManager.getEntityManagerProducao());
            ClienteNegocio clienteNegocio = new ClienteNegocio(clienteDAO);
            BairroDAO bairroDAO = new BairroDAO(FabricaEntityManager.getEntityManagerProducao());
            BairroNegocio bairroNegocio = new BairroNegocio(bairroDAO);

            // Inserção do primeiro cliente
            BairroDTO bairroDTO = bairroNegocio.pesquisaParteNome("Centro").get(0);
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setNome("Henriky");
            clienteDTO.setCPF("123.345.656-51");
            clienteDTO.setRG("234567-9");
            clienteDTO.setTelefone("65 99999-7070");
            clienteDTO.setLogradouro("Rua das coral");
            clienteDTO.setNumero("123");
            clienteDTO.setPontoReferencia("Próximo a nada");
            clienteDTO.setBairro(bairroDTO);
            clienteNegocio.inserir(clienteDTO);

            // Verifica inserção do primeiro cliente
            ClienteDTO clienteInserido = clienteNegocio.pesquisaCodigo(clienteDTO.getCodigo());
            if (clienteInserido != null) {
                System.out.println("Cliente inserido com sucesso: " + clienteInserido);
            }

            // Inserção do segundo cliente
            bairroDTO = bairroNegocio.pesquisaParteNome("Coxipó").get(0);
            clienteDTO = new ClienteDTO();
            clienteDTO.setNome("Henriky");
            clienteDTO.setCPF("123.430.678-99");
            clienteDTO.setRG("123456-8");
            clienteDTO.setTelefone("65 98888-3030");
            clienteDTO.setLogradouro("Rua das pedras");
            clienteDTO.setNumero("456");
            clienteDTO.setPontoReferencia("Final da rua");
            clienteDTO.setBairro(bairroDTO);
            clienteNegocio.inserir(clienteDTO);

            // Verifica inserção do segundo cliente
            clienteInserido = clienteNegocio.pesquisaCodigo(clienteDTO.getCodigo());
            if (clienteInserido != null) {
                System.out.println("Cliente inserido com sucesso: " + clienteInserido);
            }

            // Busca e exibe todos os clientes ordenados por nome
            System.out.println("Lista de clientes ordenados por nome:");
            List<ClienteDTO> listaClientes = clienteNegocio.pesquisarTodosOrdenadosPorNome();
            for (ClienteDTO cliente : listaClientes) {
                System.out.println(cliente);
            }

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
