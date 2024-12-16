package com.healthy.service;

import java.util.List;

import com.healthy.dto.ResourceCreateUpdateDTO;
import com.healthy.dto.ResourceDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminResourceService {
    List<ResourceDetailsDTO> getAll();
    Page<ResourceDetailsDTO> paginate(Pageable pageable);
    ResourceDetailsDTO findById(Integer id);
    ResourceDetailsDTO create(ResourceCreateUpdateDTO resourceCreateUpdateDTO);
    ResourceDetailsDTO update(Integer id, ResourceCreateUpdateDTO resourceCreateUpdateDTO);
    void delete(Integer id);
}