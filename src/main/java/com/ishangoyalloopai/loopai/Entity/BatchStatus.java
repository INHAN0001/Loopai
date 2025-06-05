package com.ishangoyalloopai.loopai.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
//@AllArgsConstructor
@Data
public class BatchStatus {
    private String batchId;
    private List<Integer> ids;
    private String status; // yet_to_start, triggered, completed
    public BatchStatus(String batchId, List<Integer> ids, String status) {
        this.batchId = batchId;
        this.ids = ids;
        this.status = status;
    }
    public BatchStatus(List<Integer> ids) {
        this.batchId = UUID.randomUUID().toString();
        this.ids = ids;
        this.status = "yet_to_start";
    }

    // getters and setters
}

