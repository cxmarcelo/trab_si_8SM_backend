package br.mcb.marketplaceSI.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.mcb.marketplaceSI.domain.Sector;
import br.mcb.marketplaceSI.repositories.SectorRepository;
import br.mcb.marketplaceSI.services.exception.DataIntegrityException;
import br.mcb.marketplaceSI.services.exception.ObjectNotFoundException;

@Service
public class SectorService {

	@Autowired
	private SectorRepository repo;
	
	//C
	public Sector insert(Sector obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	//R
	public List<Sector> findAll() {
		return repo.findAll();
	}

	//R
	public Page<Sector> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	//R
	public Sector find(Long id) {
		Optional<Sector> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Sector.class.getName()));
	}

	//U
	public Sector update(Sector obj) {
		Sector newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	//AUX U
	private void updateData(Sector newObj, Sector obj) {
		newObj.setName(obj.getName());
	}
	
	//D
	public void delete(Long id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Setor que possui Categorias.");
		}
	}
}
