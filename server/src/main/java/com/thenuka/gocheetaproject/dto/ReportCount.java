package com.thenuka.gocheetaproject.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ReportCount {
    private String label;
    private double count;
}
