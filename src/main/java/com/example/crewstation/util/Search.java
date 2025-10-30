package com.example.crewstation.util;

import com.example.crewstation.common.enumeration.AccompanyStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Component
public class Search {
    private String category;
    private String keyword;
    private int page;
    private String orderType;
    private List<String> categories;
    private AccompanyStatus accompanyStatus;
}
