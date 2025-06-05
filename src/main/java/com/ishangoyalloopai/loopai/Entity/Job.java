


package com.ishangoyalloopai.loopai.Entity;

import com.ishangoyalloopai.loopai.io.IngestRequest;

import java.util.List;

public class Job implements Comparable<Job> {

    private final BatchStatus batch;
    private final IngestRequest.Priority priority;
    private final long createdTime;

    public Job(BatchStatus batch, IngestRequest.Priority priority, long createdTime) {
        this.batch = batch;
        this.priority = priority;
        this.createdTime = createdTime;
    }

    public BatchStatus getBatch() {
        return batch;
    }

    public List<Integer> getIds() {
        return batch.getIds();
    }

    public IngestRequest.Priority getPriority() {
        return priority;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    @Override
    public int compareTo(Job other) {

        int priorityCompare = other.priority.ordinal() - this.priority.ordinal();
        if (priorityCompare != 0) {
            return priorityCompare;
        }

        return Long.compare(this.createdTime, other.createdTime);
    }
}
