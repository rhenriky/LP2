package ifmt.cba.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.GrupoAlimentarDTO;
import ifmt.cba.entity.GrupoAlimentar;
import ifmt.cba.persistencia.GrupoAlimentarDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class GrupoAlimentarNegocio {

	private ModelMapper modelMapper;
	private GrupoAlimentarDAO grupoAlimentarDAO;

	public GrupoAlimentarNegocio(GrupoAlimentarDAO grupoAlimentarDAO) throws NegocioException {

		this.grupoAlimentarDAO = grupoAlimentarDAO;
		this.modelMapper = new ModelMapper();
	}

	public void inserir(GrupoAlimentarDTO grupoAlimentarDTO) throws NegocioException {

		GrupoAlimentar grupoAlimentar = this.toEntity(grupoAlimentarDTO);
		String mensagemErros = grupoAlimentar.validar();

		if (!mensagemErros.isEmpty()) {
			throw new NegocioException(mensagemErros);
		}

		try {
			// nao pode existir outro com o mesmo nome
			if (!grupoAlimentarDAO.buscarPorParteNome(grupoAlimentar.getNome()).isEmpty()) {
				throw new NegocioException("Ja existe esse grupo alimentar");
			}
			grupoAlimentarDAO.beginTransaction();
			grupoAlimentarDAO.incluir(grupoAlimentar);
			grupoAlimentarDAO.commitTransaction();
		} catch (PersistenciaException ex) {
			grupoAlimentarDAO.rollbackTransaction();
			throw new NegocioException("Erro ao incluir o grupo alimentar - " + ex.getMessage());
		}
	}

	public void alterar(GrupoAlimentarDTO grupoAlimentarDTO) throws NegocioException {

		GrupoAlimentar grupoAlimentar = this.toEntity(grupoAlimentarDTO);
		String mensagemErros = grupoAlimentar.validar();
		if (!mensagemErros.isEmpty()) {
			throw new NegocioException(mensagemErros);
		}
		try {
			// deve existir para ser alterado
			if (grupoAlimentarDAO.buscarPorCodigo(grupoAlimentar.getCodigo()) == null) {
				throw new NegocioException("Nao existe esse grupo alimentar");
			}
			grupoAlimentarDAO.beginTransaction();
			grupoAlimentarDAO.alterar(grupoAlimentar);
			grupoAlimentarDAO.commitTransaction();
		} catch (PersistenciaException ex) {
			grupoAlimentarDAO.rollbackTransaction();
			throw new NegocioException("Erro ao alterar o grupo alimentar - " + ex.getMessage());
		}
	}

	public void excluir(int codigo) throws NegocioException {

		try {
			GrupoAlimentar grupoAlimentar = grupoAlimentarDAO.buscarPorCodigo(codigo);
			if( grupoAlimentar == null){
				throw new NegocioException("Esse GrupoAlimentar nao existe");
			}

			grupoAlimentarDAO.beginTransaction();
			grupoAlimentarDAO.excluir(grupoAlimentar);
			grupoAlimentarDAO.commitTransaction();
		} catch (PersistenciaException ex) {
			grupoAlimentarDAO.rollbackTransaction();
			throw new NegocioException("Erro ao excluir o grupo alimentar - " + ex.getMessage());
		}
	}

	public List<GrupoAlimentarDTO> pesquisaParteNome(String parteNome) throws NegocioException {
		try {

			return this.toDTOAll(grupoAlimentarDAO.buscarPorParteNome(parteNome));
		} catch (PersistenciaException ex) {
			throw new NegocioException("Erro ao pesquisar grupo alimentar pelo nome - " + ex.getMessage());
		}
	}

	public GrupoAlimentarDTO pesquisaCodigo(int codigo) throws NegocioException {
		try {
			return this.toDTO(grupoAlimentarDAO.buscarPorCodigo(codigo));
		} catch (PersistenciaException ex) {
			throw new NegocioException("Erro ao pesquisar grupo alimentar pelo codigo - " + ex.getMessage());
		}
	}

	public List<GrupoAlimentarDTO> toDTOAll(List<GrupoAlimentar> listaGrupoAlimentar) {
		List<GrupoAlimentarDTO> listDTO = new ArrayList<GrupoAlimentarDTO>();

		for (GrupoAlimentar grupoAlimentar : listaGrupoAlimentar) {
			listDTO.add(this.toDTO(grupoAlimentar));
		}
		return listDTO;
	}

	public GrupoAlimentarDTO toDTO(GrupoAlimentar grupoAlimentar) {
		return this.modelMapper.map(grupoAlimentar, GrupoAlimentarDTO.class);
	}

	public GrupoAlimentar toEntity(GrupoAlimentarDTO grupoAlimentarDTO) {
		return this.modelMapper.map(grupoAlimentarDTO, GrupoAlimentar.class);
	}

}
