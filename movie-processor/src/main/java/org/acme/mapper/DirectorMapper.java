package org.acme.mapper;

import org.acme.dto.DirectorDTO;
import org.acme.entity.DirectorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DirectorMapper {
    DirectorMapper INSANTCE = Mappers.getMapper(DirectorMapper.class);

    DirectorEntity directorDTOToDirectorEntity(DirectorDTO directorDTO);

    DirectorDTO directorEntityToDirectorDTO(DirectorEntity directorEntity);
}
