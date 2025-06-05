package com.ishangoyalloopai.loopai.io;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IngestRequest {
    private List<Integer> ids;
    private Priority priority;

    public enum Priority {
        HIGH, MEDIUM, LOW
    }


}


