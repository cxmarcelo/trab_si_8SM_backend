package br.mcb.marketplaceSI.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.mcb.marketplaceSI.domain.Category;
import br.mcb.marketplaceSI.repositories.CategoryRepository;
import br.mcb.marketplaceSI.services.exception.DataIntegrityException;
import br.mcb.marketplaceSI.services.exception.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;
	
	//C
	public Category insert(Category obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	//R
	public List<Category> findAll() {
		return repo.findAll();
	}

	//R
	public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	//R
	public Category find(Long id) {
		Optional<Category> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
	}

	//U
	public Category update(Category obj) {
		Category newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	//AUX U
	private void updateData(Category newObj, Category obj) {
		newObj.setName(obj.getName());
	}
	
	//D
	public void delete(Long id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Category que possui produtos");
		}
	}
	
}
