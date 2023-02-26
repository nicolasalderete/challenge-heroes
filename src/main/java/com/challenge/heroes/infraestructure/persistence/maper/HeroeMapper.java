package com.challenge.heroes.infraestructure.persistence.maper;

import com.challenge.heroes.domain.Heroe;
import com.challenge.heroes.infraestructure.persistence.entity.HeroeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HeroeMapper {

    HeroeMapper INSTANCE = Mappers.getMapper(HeroeMapper.class);

    Heroe entityToDomain(HeroeEntity entity);

    HeroeEntity domainToEntity(Heroe domain);

    List<Heroe> entityListToDomainList(List<HeroeEntity> entityList);

    List<HeroeEntity> domainListToEntityList(List<Heroe> domainList);
}