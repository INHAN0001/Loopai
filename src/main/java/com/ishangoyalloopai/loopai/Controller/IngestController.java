package com.ishangoyalloopai.loopai.Controller;

import com.ishangoyalloopai.loopai.Entity.IngestionStatus;
import com.ishangoyalloopai.loopai.Service.IngestionService;
import com.ishangoyalloopai.loopai.io.IngestRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;



@RestController
public class IngestController {

    private final IngestionService ingestionService;

    public IngestController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/ingest")
    public Map<String, String> ingest(@RequestBody IngestRequest request) {
        String ingestionId = ingestionService.enqueue(request);
        return Collections.singletonMap("ingestion_id", ingestionId);
    }

    @GetMapping("/status/{ingestionId}")
    public ResponseEntity<Map<String, Object>> getStatus(@PathVariable String ingestionId) {
        IngestionStatus status = ingestionService.getStatus(ingestionId);
        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Invalid ingestion_id"));
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("ingestion_id", ingestionId);
        response.put("status", status.getOverallStatus());
        response.put("batches", status.getBatches());
        return ResponseEntity.ok(response);
    }
}
