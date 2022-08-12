package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.BranchDTO;
import com.thenuka.gocheetaproject.modals.Branch;

public interface IBranchService {
    BranchDTO convertEntityToDto(Branch branch);
}
