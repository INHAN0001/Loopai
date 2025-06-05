package com.ishangoyalloopai.loopai.Entity;

import lombok.Data;
import java.util.List;

@Data
public class IngestionStatus {
    private String ingestionId;
    private List<BatchStatus> batches;

    public IngestionStatus() {
        // no-args constructor needed for JSON deserialization
    }

    public IngestionStatus(String ingestionId, List<BatchStatus> batches) {
        this.ingestionId = ingestionId;
        this.batches = batches;
    }

    public String getOverallStatus() {
        boolean allCompleted = true;
        boolean hasTriggered = false;

        for (BatchStatus batch : batches) {
            String status = batch.getStatus();
            if (!Status.COMPLETED.equals(status)) {
                allCompleted = false;
            }
            if (Status.TRIGGERED.equals(status)) {
                hasTriggered = true;
            }
        }

        if (allCompleted) {
            return Status.COMPLETED;
        } else if (hasTriggered) {
            return Status.TRIGGERED;
        } else {
            return Status.YET_TO_START;
        }
    }

    // You can define the batch status strings as constants or an enum elsewhere, e.g.:
    public static class Status {
        public static final String YET_TO_START = "yet_to_start";
        public static final String TRIGGERED = "triggered";
        public static final String COMPLETED = "completed";
    }
}
