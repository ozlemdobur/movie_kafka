package org.acme.service;

import io.smallrye.mutiny.Uni;
import org.acme.dto.DirectorDTO;

public interface IDirectorService {
    Uni<DirectorDTO> save(DirectorDTO directorDTO);
    Uni<DirectorDTO> update(Long id, DirectorDTO directorDTO);

    Uni<DirectorDTO> findById(Long id);
}
