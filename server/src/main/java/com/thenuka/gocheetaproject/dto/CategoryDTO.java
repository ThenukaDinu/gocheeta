package com.thenuka.gocheetaproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;

@Getter
@Setter
public class CategoryDTO {
    private int id;
    @Nullable
    private String description;
    private String name;
}
