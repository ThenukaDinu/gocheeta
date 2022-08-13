package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.BranchDTO;
import com.thenuka.gocheetaproject.dto.CategoryDTO;
import com.thenuka.gocheetaproject.modals.Branch;
import com.thenuka.gocheetaproject.modals.Category;
import com.thenuka.gocheetaproject.requests.BranchRequest;
import com.thenuka.gocheetaproject.requests.CategoryRequest;

import java.util.List;

public interface IBranchService {
    BranchDTO convertEntityToDto(Branch branch);

    boolean existsById(int id);
    Branch findById(int id);
    BranchDTO findOne(int id);
    BranchDTO save(Branch branch);
    BranchDTO update(int id, BranchRequest branchRequest);
    void delete(int id);
    List<BranchDTO> findAll();
    Branch convertRequestToEntity(BranchRequest branchRequest, Branch branch);
}
