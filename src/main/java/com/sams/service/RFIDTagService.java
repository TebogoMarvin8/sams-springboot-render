package com.sams.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sams.model.RFIDTag;
import com.sams.repository.RFIDTagRepository;

@Service
public class RFIDTagService {

    @Autowired
    private RFIDTagRepository rfidTagRepository;

    // Create or update an RFIDTag
    public RFIDTag saveRFIDTag(RFIDTag rfidTag) {
        return rfidTagRepository.save(rfidTag);
    }

    // Retrieve an RFIDTag by its ID
    public Optional<RFIDTag> getRFIDTagById(Long id) {
        return rfidTagRepository.findById(id);
    }

    // Retrieve all RFIDTags
    public List<RFIDTag> getAllRFIDTags() {
        return rfidTagRepository.findAll();
    }

    // Method to retrieve RFIDTags that are not assigned to any Student
    public List<RFIDTag> getUnassignedRFIDTags() {
        return rfidTagRepository.findUnassignedRFIDTags();
    }

    // Delete an RFIDTag by its ID
    public void deleteRFIDTag(Long id) {
        rfidTagRepository.deleteById(id);
    }
}
