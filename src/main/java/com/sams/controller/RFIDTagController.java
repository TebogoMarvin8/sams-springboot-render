package com.sams.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sams.model.RFIDTag;
import com.sams.service.RFIDTagService;

@RestController
@RequestMapping("/api/rfid-tags")
public class RFIDTagController {

    @Autowired
    private RFIDTagService rfidTagService;

    // Create a new RFIDTag
    @PostMapping
    public RFIDTag createRFIDTag(@RequestBody RFIDTag rfidTag) {
        return rfidTagService.saveRFIDTag(rfidTag);
    }

    // Get an RFIDTag by ID
    @GetMapping("/{id}")
    public ResponseEntity<RFIDTag> getRFIDTagById(@PathVariable Long id) {
        Optional<RFIDTag> rfidTag = rfidTagService.getRFIDTagById(id);
        return rfidTag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to get RFIDTags that are not assigned to any Student
    @GetMapping("/unassigned")
    public List<RFIDTag> getUnassignedRFIDTags() {
        return rfidTagService.getUnassignedRFIDTags();
    }
    // Get all RFIDTags
    @GetMapping
    public List<RFIDTag> getAllRFIDTags() {
        return rfidTagService.getAllRFIDTags();
    }

    // Update an RFIDTag by ID
    @PutMapping("/{id}")
    public ResponseEntity<RFIDTag> updateRFIDTag(@PathVariable Long id, @RequestBody RFIDTag rfidTagDetails) {
        Optional<RFIDTag> existingRFIDTag = rfidTagService.getRFIDTagById(id);

        if (existingRFIDTag.isPresent()) {
            RFIDTag rfidTag = existingRFIDTag.get();
            rfidTag.setTagNumber(rfidTagDetails.getTagNumber());
            rfidTag.setStudent(rfidTagDetails.getStudent());

            RFIDTag updatedRFIDTag = rfidTagService.saveRFIDTag(rfidTag);
            return ResponseEntity.ok(updatedRFIDTag);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an RFIDTag by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRFIDTag(@PathVariable Long id) {
        if (rfidTagService.getRFIDTagById(id).isPresent()) {
            rfidTagService.deleteRFIDTag(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
