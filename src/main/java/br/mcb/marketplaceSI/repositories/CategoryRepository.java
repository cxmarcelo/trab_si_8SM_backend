package br.mcb.marketplaceSI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mcb.marketplaceSI.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
