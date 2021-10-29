package br.mcb.marketplaceSI.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.mcb.marketplaceSI.domain.Product;
import br.mcb.marketplaceSI.repositories.ProductRepository;
import br.mcb.marketplaceSI.services.exception.DataIntegrityException;
import br.mcb.marketplaceSI.services.exception.ObjectNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;
	
	
	//C
	public Product insert(Product obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	//R
	public List<Product> findAll() {
		return repo.findAll();
	}

	//R
	public Page<Product> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	//R
	public Product find(Long id) {
		Optional<Product> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName()));
	}

	//U
	public Product update(Product obj) {
		Product newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	//AUX U
	private void updateData(Product newObj, Product obj) {
		newObj.setName(obj.getName());
		newObj.setPrice(obj.getPrice());
		newObj.setDescription(obj.getDescription());
	}
	
	//D
	public void delete(Long id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível excluir o produto.");
		}
	}
}
