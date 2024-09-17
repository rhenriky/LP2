package ifmt.cba.execucao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

import ifmt.cba.dto.ItemOrdemProducaoDTO;
import ifmt.cba.negocio.ItemOrdemProducaoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.ItemOrdemProducaoDAO;
import ifmt.cba.persistencia.PersistenciaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@SuppressWarnings("unused")
public class AppInserirItemOrdemProducao {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ItemOrdemProducaoPU");
        EntityManager em = emf.createEntityManager();

        try {
            ItemOrdemProducaoDAO itemOrdemProducaoDAO = new ItemOrdemProducaoDAO(em);
            ItemOrdemProducaoNegocio itemOrdemProducaoNegocio = new ItemOrdemProducaoNegocio(itemOrdemProducaoDAO);

            ItemOrdemProducaoDTO itemOrdemProducaoDTO = new ItemOrdemProducaoDTO();

            System.out.println("Informe os dados do item da ordem de produção:");
            
            System.out.print("Descrição: ");
            itemOrdemProducaoDTO.setDescricao(scanner.nextLine());
            
            System.out.print("Quantidade: ");
            itemOrdemProducaoDTO.setQuantidadePorcao(scanner.nextInt());
            
            System.out.print("Valor Unitário: ");
            itemOrdemProducaoDTO.setValorUnitario(scanner.nextBigDecimal());
            
            itemOrdemProducaoDTO.setDataHoraRegistro(LocalDateTime.now()); // Define a data e hora atuais como data de registro
            itemOrdemProducaoDTO.setEstado("REGISTRADA"); // Define o estado inicial como 'REGISTRADA'

            try {
                itemOrdemProducaoNegocio.inserir(itemOrdemProducaoDTO);
                System.out.println("Item de ordem de produção inserido com sucesso!");
            } catch (NegocioException | PersistenciaException ex) {
                System.out.println("Erro ao inserir item de ordem de produção: " + ex.getMessage());
            }

        } finally {
            em.close();
            emf.close();
            scanner.close();
        }
    }
}
