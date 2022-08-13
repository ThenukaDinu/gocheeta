package com.thenuka.gocheetaproject.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class BranchRequest {
    private String name;
    private String contactNo;
    private String address;

    private ArrayList<Integer> drivers;
    private ArrayList<Integer> vehicles;
}
