package com.healthy.service;

import com.healthy.dto.ProfileResourceCreateUpdateDTO;
import com.healthy.dto.ProfileResourceDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfileResourceService {
    List<ProfileResourceDetailsDTO> getAll();
    Page<ProfileResourceDetailsDTO> paginate(Pageable pageable);
    ProfileResourceDetailsDTO findById(Integer id);
    ProfileResourceDetailsDTO create(ProfileResourceCreateUpdateDTO profileResourceCreateUpdateDTO);
    ProfileResourceDetailsDTO update(Integer id, ProfileResourceCreateUpdateDTO profileResourceCreateUpdateDTO);
    void delete(Integer id);
}
