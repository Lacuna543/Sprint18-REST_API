package com.softserve.edu.service.impl;

import com.softserve.edu.dto.MarathonRequest;
import com.softserve.edu.exception.EntityNotFoundException;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.service.MarathonService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MarathonServiceImpl implements MarathonService {

    private final MarathonRepository marathonRepository;

    public MarathonServiceImpl(MarathonRepository marathonRepository) {
        this.marathonRepository = marathonRepository;
    }

    @Transactional
    public List<Marathon> getAll() {
        List<Marathon> marathons = marathonRepository.findAll();
        return marathons.isEmpty() ? new ArrayList<>() : marathons;
    }

    public Marathon getMarathonById(Long id) {
        return marathonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(("No marathon /w id " + id)));// add to Logger later
    }

    @Override
    public Marathon createMarathon(MarathonRequest marathonRequest) {
        Marathon marathon = new Marathon();
        marathon.setTitle(marathonRequest.getTitle());
        return marathonRepository.save(marathon);
    }

    @Override
    public Marathon update(MarathonRequest marathonRequest, Long id) throws NotFoundException {
        Marathon marathon = marathonRepository.getOne(id);
        if (marathon.getId() != null) {
            marathon.setTitle(marathonRequest.getTitle());
            return marathonRepository.save(marathon);
        }

        throw new NotFoundException("This marathon doesn't exist");
    }

    @Override
    public void deleteMarathonById(Long id) {
        // Optional<Marathon> marathon = marathonRepository.findById(id);
        Marathon marathon1 = marathonRepository.getOne(id);
        if (marathon1 != null) {
            marathonRepository.deleteById(id);
        } else
            throw new EntityNotFoundException("No marathon exist for given id");

    }
}
