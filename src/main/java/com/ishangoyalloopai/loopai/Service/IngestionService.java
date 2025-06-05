package com.ishangoyalloopai.loopai.Service;

import com.ishangoyalloopai.loopai.Entity.BatchStatus;
import com.ishangoyalloopai.loopai.Entity.IngestionStatus;
import com.ishangoyalloopai.loopai.Entity.Job;
import com.ishangoyalloopai.loopai.io.IngestRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class IngestionService {

    private final PriorityBlockingQueue<Job> jobQueue = new PriorityBlockingQueue<>();
    private final Map<String, IngestionStatus> ingestionStatusMap = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ExecutorService processingExecutor = Executors.newFixedThreadPool(3);

    public IngestionService() {
        // Schedule processing of 1 batch every 5 seconds
        scheduler.scheduleAtFixedRate(this::processNextJob, 0, 5, TimeUnit.SECONDS);
    }

    public String enqueue(IngestRequest request) {
        String ingestionId = UUID.randomUUID().toString();
        long createdTime = System.currentTimeMillis();

        List<BatchStatus> batchList = new ArrayList<>();
        List<Integer> ids = request.getIds();

        // Create batches of size 3
        for (int i = 0; i < ids.size(); i += 3) {
            List<Integer> batchIds = new ArrayList<>(ids.subList(i, Math.min(i + 3, ids.size())));
            String batchId = UUID.randomUUID().toString();

            BatchStatus batch = new BatchStatus();
            batch.setBatchId(batchId);
            batch.setIds(batchIds);
            batch.setStatus("yet_to_start");  // Initial status

            batchList.add(batch);

            // Add a job to the queue with this batch and the priority
            jobQueue.add(new Job(batch, request.getPriority(), createdTime));
        }

        // Save the ingestion status with batches
        ingestionStatusMap.put(ingestionId, new IngestionStatus(ingestionId, batchList));

        return ingestionId;
    }

    private void processNextJob() {
        if (jobQueue.isEmpty()) return;

        Job job = jobQueue.poll();
        if (job == null) return;

        BatchStatus batch = job.getBatch();
        batch.setStatus("triggered");  // Mark batch as triggered

        // Process the batch asynchronously (simulate API call)
        processingExecutor.submit(() -> {
            try {
                for (Integer id : batch.getIds()) {
                    Thread.sleep(1000);  // Simulate delay per ID
                    System.out.println("Processed ID " + id + ": {\"data\": \"processed\"}");
                }
                batch.setStatus("completed");  // Mark batch complete after processing
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                batch.setStatus("yet_to_start"); // revert status if interrupted
            }
        });
    }

    public IngestionStatus getStatus(String ingestionId) {
        return ingestionStatusMap.get(ingestionId);
    }
}
