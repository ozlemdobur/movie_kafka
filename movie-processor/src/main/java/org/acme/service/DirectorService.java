package org.acme.service;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import org.acme.dto.DirectorDTO;
import org.acme.entity.DirectorEntity;
import org.acme.mapper.DirectorMapper;
import org.acme.repository.DirectorRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DirectorService implements IDirectorService{
    private DirectorRepository directorRepository;
    @Inject
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    DirectorMapper mapper = DirectorMapper.INSANTCE;

    @Override
    @ReactiveTransactional
    public Uni<DirectorDTO> save(DirectorDTO directorDTO) {
        DirectorEntity directorEntity = mapper.directorDTOToDirectorEntity(directorDTO);
        return directorRepository.persist(directorEntity)
                .map(entity-> mapper.directorEntityToDirectorDTO(entity));
    }

    @Override
    @ReactiveTransactional
    public Uni<DirectorDTO> update(Long id, DirectorDTO directorDTO) {
        return directorRepository.findById(id)
                .onItem()
                .invoke(directorEntity -> DirectorEntity.builder()
                        .firstName(directorDTO.getFirstName())
                        .lastName(directorDTO.getLastName())
                        .country(directorDTO.getCountry()))
                        .map(directorEntity -> mapper.directorEntityToDirectorDTO(directorEntity));



    }

    @Override
    public Uni<DirectorDTO> findById(Long id) {
        return directorRepository.findById(id).map(p-> mapper.directorEntityToDirectorDTO(p));
    }
}
