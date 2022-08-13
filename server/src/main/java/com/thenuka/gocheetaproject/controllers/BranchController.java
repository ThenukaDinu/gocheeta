package com.thenuka.gocheetaproject.controllers;

import com.thenuka.gocheetaproject.dto.BranchDTO;
import com.thenuka.gocheetaproject.dto.RatingsDTO;
import com.thenuka.gocheetaproject.interfaces.IBranchService;
import com.thenuka.gocheetaproject.interfaces.IRatingService;
import com.thenuka.gocheetaproject.modals.Branch;
import com.thenuka.gocheetaproject.modals.Rating;
import com.thenuka.gocheetaproject.requests.BranchRequest;
import com.thenuka.gocheetaproject.requests.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/branches")
public class BranchController {
    @Autowired
    private IBranchService branchService;

    @PreAuthorize("hasAnyRole('USER', 'DRIVER', 'ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAll () {
        try {
            List<BranchDTO> branchDTOS = branchService.findAll();
            return new ResponseEntity<>(branchDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'DRIVER', 'ADMIN')")
    @RequestMapping(value = "/{branchId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOne(@PathVariable(value = "branchId") int id) {
        try {
            BranchDTO branchDTO = branchService.findOne(id);
            if (branchDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(branchDTO, HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody BranchRequest branchRequest) {
        try {
            Branch branch = new Branch();
            BranchDTO branchDTO = branchService.save(branchService.convertRequestToEntity(branchRequest, branch));
            return new ResponseEntity<>(branchDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{branchId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "branchId") int id) {
        try {
            branchService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{branchId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody BranchRequest branchRequest, @PathVariable(value = "branchId") int id) {
        try {
            BranchDTO branchDTO = branchService.update(id, branchRequest);
            if (branchDTO == null) {
                return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(branchDTO, HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
