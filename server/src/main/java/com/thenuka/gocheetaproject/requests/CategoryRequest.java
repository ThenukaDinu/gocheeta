package com.thenuka.gocheetaproject.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class CategoryRequest {
    @Nullable
    private String description;
    private String name;
}
