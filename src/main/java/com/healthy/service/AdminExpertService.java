package com.healthy.service;

import com.healthy.dto.ExpertDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminExpertService {
    List<ExpertDTO> getAll();
    Page<ExpertDTO> paginate(Pageable pageable);
    ExpertDTO findById(Integer id);
    ExpertDTO create(ExpertDTO Expert);
    ExpertDTO update(Integer id, ExpertDTO updateExpert);
    void delete(Integer id);
}
