package br.mcb.marketplaceSI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mcb.marketplaceSI.domain.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long>{

}