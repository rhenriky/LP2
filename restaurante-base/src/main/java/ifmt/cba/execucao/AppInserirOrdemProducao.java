package ifmt.cba.execucao;

import ifmt.cba.dto.OrdemProducaoDTO;
import ifmt.cba.negocio.OrdemProducaoNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.persistencia.OrdemProducaoDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;

import java.time.LocalDate;

public class AppInserirOrdemProducao {
    public static void main(String[] args) {
        try {
            OrdemProducaoDAO ordemProducaoDAO = new OrdemProducaoDAO(FabricaEntityManager.getEntityManagerProducao());
            OrdemProducaoNegocio ordemProducaoNegocio = new OrdemProducaoNegocio(ordemProducaoDAO);

            // Criando a primeira ordem de produção com a data atual
            OrdemProducaoDTO ordemProducaoDTO = new OrdemProducaoDTO();
            ordemProducaoDTO.setDataProducao(LocalDate.now());  // Usando LocalDate, conforme DTO
            ordemProducaoNegocio.inserir(ordemProducaoDTO);

            // Criando a segunda ordem de produção com data de um dia após a data atual
            ordemProducaoDTO = new OrdemProducaoDTO();
            ordemProducaoDTO.setDataProducao(LocalDate.now().plusDays(1));  // Definindo data de amanhã
            ordemProducaoNegocio.inserir(ordemProducaoDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }
}
