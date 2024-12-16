package com.healthy.service.impl;

import com.healthy.dto.HabitCreateUpdateDTO;
import com.healthy.dto.HabitDetailsDTO;
import com.healthy.exception.ResourceNotFoundException;
import com.healthy.mapper.HabitMapper;
import com.healthy.model.entity.Habit;
import com.healthy.model.entity.HabitType;
import com.healthy.repository.HabitRepository;
import com.healthy.repository.HabitTypeRepository;
import com.healthy.service.AdminHabitService;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminHabitServiceImpl implements AdminHabitService {

    private final HabitRepository habitRepository;
    private final HabitTypeRepository habitTypeRepository;
    private final HabitMapper habitMapper;


    @Transactional(readOnly = true)
    @Override
    public List<HabitDetailsDTO> getAll() {
        List<Habit> habits = habitRepository.findAll();
        return habits.stream()
                .map(habitMapper::toDetailsDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<HabitDetailsDTO> paginate(Pageable pageable) {
        return habitRepository.findAll(pageable)
                .map(habitMapper::toDetailsDTO);
    }

    @Override
    public HabitDetailsDTO findById(Integer id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habit "+id+" not found"));
        return habitMapper.toDetailsDTO(habit);
    }

    @Transactional
    @Override
    public HabitDetailsDTO create(HabitCreateUpdateDTO habitCreateUpdateDTO) {
        HabitType habitType = habitTypeRepository.findById(habitCreateUpdateDTO.getHabitTypeId())
                .orElseThrow(()-> new ResourceNotFoundException("Habit type not found with id: "+habitCreateUpdateDTO.getHabitTypeId()));

        Habit habit = habitMapper.toEntity(habitCreateUpdateDTO);
        habit.setHabitType(habitType);
        return habitMapper.toDetailsDTO(habitRepository.save(habit));
    }

    @Transactional
    @Override
    public HabitDetailsDTO update(Integer id, HabitCreateUpdateDTO habitCreateUpdateDTO) {
        Habit habitFromDb = habitRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Habit not found with id: "+id));

        HabitType habitType = habitTypeRepository.findById(habitCreateUpdateDTO.getHabitTypeId())
                .orElseThrow(()-> new ResourceNotFoundException("HabitType not found with id: "+habitCreateUpdateDTO.getHabitTypeId()));

        habitFromDb.setHabitType(habitType);
        habitFromDb.setId(habitCreateUpdateDTO.getId());
        habitFromDb.setFrequency(habitCreateUpdateDTO.getFrequency());
        habitFromDb.setName(habitCreateUpdateDTO.getName());
        habitFromDb.setDescription(habitCreateUpdateDTO.getDescription());

        return habitMapper.toDetailsDTO(habitRepository.save(habitFromDb));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habit "+id+" not found"));
        habitRepository.delete(habit);
    }
    /*Para poder eliminar un ID de Hábito, primero tengo que elimnar desde pgAdmin, en la tabla Goals*/
    /* DELETE FROM goals where habit_id = 207 */
    /*207 = id de hábito como ejemplo*/
}
