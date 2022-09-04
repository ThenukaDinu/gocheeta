package com.thenuka.gocheetaproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class BranchDTO {
    private int id;
    private String name;
    private String contactNo;
    private String address;

    private ArrayList<Integer> vehicles;
    private ArrayList<Integer> drivers;
}
