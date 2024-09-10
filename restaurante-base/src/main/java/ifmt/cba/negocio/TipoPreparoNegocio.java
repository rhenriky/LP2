package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.TipoPreparoDTO;
import ifmt.cba.entity.TipoPreparo;
import ifmt.cba.persistencia.TipoPreparoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class TipoPreparoNegocio {

    private ModelMapper modelMapper;
    private TipoPreparoDAO tipoPreparoDAO;

    public TipoPreparoNegocio(TipoPreparoDAO tipoPreparoDAO) {
        this.tipoPreparoDAO = tipoPreparoDAO;
        this.modelMapper = new ModelMapper();
    }

    public void inserir(TipoPreparoDTO tipoPreparoDTO) throws NegocioException {
        TipoPreparo tipoPreparo = this.toEntity(tipoPreparoDTO);
        String mensagemErros = tipoPreparo.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            tipoPreparoDAO.beginTransaction();
            tipoPreparoDAO.incluir(tipoPreparo);
            tipoPreparoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            tipoPreparoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao incluir o tipo de preparo - " + ex.getMessage());
        }
    }

    public void alterar(TipoPreparoDTO tipoPreparoDTO) throws NegocioException {
        TipoPreparo tipoPreparo = this.toEntity(tipoPreparoDTO);
        String mensagemErros = tipoPreparo.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (tipoPreparoDAO.buscarPorCodigo(tipoPreparo.getCodigo()) == null) {
                throw new NegocioException("Não existe esse tipo de preparo");
            }

            tipoPreparoDAO.beginTransaction();
            tipoPreparoDAO.alterar(tipoPreparo);
            tipoPreparoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            tipoPreparoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao alterar o tipo de preparo - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            TipoPreparo tipoPreparo = tipoPreparoDAO.buscarPorCodigo(codigo);
            if (tipoPreparo == null) {
                throw new NegocioException("Não existe esse tipo de preparo");
            }

            tipoPreparoDAO.beginTransaction();
            tipoPreparoDAO.excluir(tipoPreparo);
            tipoPreparoDAO.commitTransaction();
        } catch (PersistenciaException ex) {
            tipoPreparoDAO.rollbackTransaction();
            throw new NegocioException("Erro ao excluir o tipo de preparo - " + ex.getMessage());
        }
    }

    public List<TipoPreparoDTO> pesquisarTodos() throws NegocioException {
        try {
            return this.toDTOAll(tipoPreparoDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar tipos de preparo - " + ex.getMessage());
        }
    }

    public TipoPreparoDTO pesquisarCodigo(int codigo) throws NegocioException {
        try {
            return this.toDTO(tipoPreparoDAO.buscarPorCodigo(codigo));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar tipo de preparo pelo código - " + ex.getMessage());
        }
    }

    public List<TipoPreparoDTO> toDTOAll(List<TipoPreparo> listaTipoPreparo) {
        List<TipoPreparoDTO> listaDTO = new ArrayList<>();

        for (TipoPreparo tipoPreparo : listaTipoPreparo) {
            listaDTO.add(this.toDTO(tipoPreparo));
        }
        return listaDTO;
    }

    public TipoPreparoDTO toDTO(TipoPreparo tipoPreparo) {
        return this.modelMapper.map(tipoPreparo, TipoPreparoDTO.class);
    }

    public TipoPreparo toEntity(TipoPreparoDTO tipoPreparoDTO) {
        return this.modelMapper.map(tipoPreparoDTO, TipoPreparo.class);
    }
}
