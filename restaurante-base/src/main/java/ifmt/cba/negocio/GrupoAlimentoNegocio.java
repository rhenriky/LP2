package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.GrupoAlimentoDTO;
import ifmt.cba.entity.GrupoAlimento;
import ifmt.cba.persistencia.GrupoAlimentoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class GrupoAlimentoNegocio {

    private ModelMapper modelMapper;
    private GrupoAlimentoDAO grupoAlimentoDAO;

    public GrupoAlimentoNegocio(GrupoAlimentoDAO grupoAlimentoDAO) throws NegocioException {
        this.grupoAlimentoDAO = grupoAlimentoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(GrupoAlimentoDTO grupoAlimentoDTO) throws NegocioException {

        GrupoAlimento grupoAlimento = this.toEntity(grupoAlimentoDTO);
        String mensagemErros = grupoAlimento.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            // Não pode existir outro com o mesmo nome
            if (!grupoAlimentoDAO.buscarPorParteNome(grupoAlimento.getNome()).isEmpty()) {
                throw new NegocioException("Já existe esse grupo alimentar");
            }
            grupoAlimentoDAO.beginTransaction();
            grupoAlimentoDAO.incluir(grupoAlimento);
            grupoAlimentoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            grupoAlimentoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o grupo alimentar - " + ex.getMessage());
        }
    }

    public void alterar(GrupoAlimentoDTO grupoAlimentoDTO) throws NegocioException {

        GrupoAlimento grupoAlimento = this.toEntity(grupoAlimentoDTO);
        String mensagemErros = grupoAlimento.validar();
        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }
        try {
            // Deve existir para ser alterado
            if (grupoAlimentoDAO.buscarPorCodigo(grupoAlimento.getCodigo()) == null) {
                throw new NegocioException("Não existe esse grupo alimentar");
            }
            grupoAlimentoDAO.beginTransaction();
            grupoAlimentoDAO.alterar(grupoAlimento);
            grupoAlimentoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            grupoAlimentoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o grupo alimentar - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {

        try {
            GrupoAlimento grupoAlimento = grupoAlimentoDAO.buscarPorCodigo(codigo);
            if (grupoAlimento == null) {
                throw new NegocioException("Esse GrupoAlimento não existe");
            }

            grupoAlimentoDAO.beginTransaction();
            grupoAlimentoDAO.excluir(grupoAlimento);
            grupoAlimentoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            grupoAlimentoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o grupo alimentar - " + ex.getMessage());
        }
    }

    public List<GrupoAlimentoDTO> pesquisaParteNome(String parteNome) throws NegocioException {
        try {
            return this.toDTOAll(grupoAlimentoDAO.buscarPorParteNome(parteNome));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar grupo alimentar pelo nome - " + ex.getMessage());
        }
    }

    public GrupoAlimentoDTO pesquisaCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(grupoAlimentoDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar grupo alimentar pelo código - " + ex.getMessage());
        }
    }

    public List<GrupoAlimentoDTO> toDTOAll(List<GrupoAlimento> listaGrupoAlimento) {
        List<GrupoAlimentoDTO> listDTO = new ArrayList<GrupoAlimentoDTO>();

        for (GrupoAlimento grupoAlimento : listaGrupoAlimento) {
            listDTO.add(this.toDTO(grupoAlimento));
        }
        return listDTO;
    }

    public GrupoAlimentoDTO toDTO(GrupoAlimento grupoAlimento) {
        return this.modelMapper.map(grupoAlimento, GrupoAlimentoDTO.class);
    }

    public GrupoAlimento toEntity(GrupoAlimentoDTO grupoAlimentoDTO) {
        return this.modelMapper.map(grupoAlimentoDTO, GrupoAlimento.class);
    }

}
