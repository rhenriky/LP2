package ifmt.cba.negocio;

import java.util.List;
import org.modelmapper.ModelMapper;
import ifmt.cba.dto.ClienteDTO;
import ifmt.cba.entity.Cliente;
import ifmt.cba.persistencia.ClienteDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class ClienteNegocio {

    private ModelMapper modelMapper;
    private ClienteDAO clienteDAO;

    public ClienteNegocio(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
        this.modelMapper = new ModelMapper();
    }

    // Método para inserir cliente com validação de CPF já existente
    public void inserir(ClienteDTO clienteDTO) throws NegocioException {
        Cliente cliente = toEntity(clienteDTO);
        String mensagemErros = cliente.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException("Validação falhou: " + mensagemErros);
        }

        try {
            if (clienteDAO.buscarPorCPF(cliente.getCPF()) != null) {
                throw new NegocioException("Cliente com CPF já cadastrado.");
            }
            clienteDAO.beginTransaction();
            clienteDAO.incluir(cliente);
            clienteDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            clienteDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir cliente: " + ex.getMessage());
        }
    }

    // Método para alterar cliente
    public void alterar(ClienteDTO clienteDTO) throws NegocioException {
        Cliente cliente = toEntity(clienteDTO);
        String mensagemErros = cliente.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException("Validação falhou: " + mensagemErros);
        }

        try {
            if (clienteDAO.buscarPorCodigo(cliente.getCodigo()) == null) {
                throw new NegocioException("Cliente não encontrado.");
            }
            clienteDAO.beginTransaction();
            clienteDAO.alterar(cliente);
            clienteDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            clienteDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar cliente: " + ex.getMessage());
        }
    }

    // Método para excluir cliente
    public void excluir(int codigo) throws NegocioException {
        try {
            Cliente cliente = clienteDAO.buscarPorCodigo(codigo);
            if (cliente == null) {
                throw new NegocioException("Cliente não encontrado.");
            }

            clienteDAO.beginTransaction();
            clienteDAO.excluir(cliente);
            clienteDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            clienteDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir cliente: " + ex.getMessage());
        }
    }

    // Método para pesquisar clientes por parte do nome
    public List<ClienteDTO> pesquisaParteNome(String parteNome) throws NegocioException {
        try {
            return toDTOAll(clienteDAO.buscarPorParteNome(parteNome));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar cliente por parte do nome: " + ex.getMessage());
        }
    }

    // Método para pesquisar cliente por código
    public ClienteDTO pesquisaCodigo(int codigo) throws NegocioException {
        try {
            Cliente cliente = clienteDAO.buscarPorCodigo(codigo);
            if (cliente == null) {
                throw new NegocioException("Cliente não encontrado.");
            }
            return toDTO(cliente);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar cliente pelo código: " + ex.getMessage());
        }
    }

    // Método para pesquisar todos os clientes ordenados por nome
    public List<ClienteDTO> pesquisarTodosOrdenadosPorNome() throws NegocioException {
        try {
            return toDTOAll(clienteDAO.buscarTodosOrdenadosPorNome());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar clientes: " + ex.getMessage());
        }
    }

    // Conversão de lista de Cliente para lista de ClienteDTO usando stream
    private List<ClienteDTO> toDTOAll(List<Cliente> listaClientes) {
        return listaClientes.stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .toList();
    }

    // Conversão de Cliente para ClienteDTO
    private ClienteDTO toDTO(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    // Conversão de ClienteDTO para Cliente
    private Cliente toEntity(ClienteDTO clienteDTO) {
        return modelMapper.map(clienteDTO, Cliente.class);
    }
}
