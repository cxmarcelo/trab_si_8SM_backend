package br.mcb.marketplaceSI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mcb.marketplaceSI.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
