package com.softserve.edu.service;

import com.softserve.edu.dto.MarathonRequest;
import com.softserve.edu.model.Marathon;
import javassist.NotFoundException;

import java.util.List;

public interface MarathonService {
    List<Marathon> getAll();

    Marathon getMarathonById(Long id);

    Marathon create(MarathonRequest marathonRequest);

    Marathon update(MarathonRequest marathonRequest, Long id) throws NotFoundException;

    void deleteMarathonById(Long id);
}
