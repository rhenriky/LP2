package ifmt.cba.execucao;

import ifmt.cba.dto.ColaboradorDTO;
import ifmt.cba.negocio.ColaboradorNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.ColaboradorDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

public class AppInserirColaborador {
    public static void main(String[] args) {
        try {
            ColaboradorDAO colaboradorDAO = new ColaboradorDAO(FabricaEntityManager.getEntityManagerProducao());
            ColaboradorNegocio colaboradorNegocio = new ColaboradorNegocio(colaboradorDAO);

            //  Colaborador 1
            ColaboradorDTO colaboradorDTO = new ColaboradorDTO();
            colaboradorDTO.setNome("Carlos Alberto");
            colaboradorDTO.setRG("123456789");
            colaboradorDTO.setCPF("111.222.333-44");
            colaboradorDTO.setTelefone("1234-5678");
            colaboradorDTO.setCargo("Chef de Cozinha");
            colaboradorNegocio.inserir(colaboradorDTO);

            //  Colaborador 2
            colaboradorDTO = new ColaboradorDTO();
            colaboradorDTO.setNome("Ana Maria");
            colaboradorDTO.setRG("987654321");
            colaboradorDTO.setCPF("555.666.777-88");
            colaboradorDTO.setTelefone("8765-4321");
            colaboradorDTO.setCargo("Garçonete");
            colaboradorNegocio.inserir(colaboradorDTO);

            //  Colaborador 3
            colaboradorDTO = new ColaboradorDTO();
            colaboradorDTO.setNome("Roberto Souza");
            colaboradorDTO.setRG("111223344");
            colaboradorDTO.setCPF("999.888.777-66");
            colaboradorDTO.setTelefone("2345-6789");
            colaboradorDTO.setCargo("Sushiman");
            colaboradorNegocio.inserir(colaboradorDTO);

            //  Colaborador 4
            colaboradorDTO = new ColaboradorDTO();
            colaboradorDTO.setNome("Maria Silva");
            colaboradorDTO.setRG("555666777");
            colaboradorDTO.setCPF("333.444.555-66");
            colaboradorDTO.setTelefone("3456-7890");
            colaboradorDTO.setCargo("Recepcionista");
            colaboradorNegocio.inserir(colaboradorDTO);

            // Colaborador 5
            colaboradorDTO = new ColaboradorDTO();
            colaboradorDTO.setNome("João Pereira");
            colaboradorDTO.setRG("888999000");
            colaboradorDTO.setCPF("222.333.444-55");
            colaboradorDTO.setTelefone("4567-8901");
            colaboradorDTO.setCargo("Auxiliar de Cozinha");
            colaboradorNegocio.inserir(colaboradorDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
