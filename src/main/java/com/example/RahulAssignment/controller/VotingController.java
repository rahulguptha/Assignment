package com.example.RahulAssignment.controller;

import com.example.RahulAssignment.model.Candidate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
public class VotingController {

    private Map<String, Integer> candidates = new HashMap<>();

    @PostMapping("/entercandidate")
    public ResponseEntity<String> enterCandidate(@RequestParam String name) {
        candidates.put(name, 0); // Initialize vote count to 0
        return ResponseEntity.ok("Candidate entered: " + name);
    }

    @PostMapping("/castvote")
    public ResponseEntity<Integer> castVote(@RequestParam String name) {
        if (candidates.containsKey(name)) {
            candidates.put(name, candidates.get(name) + 1); // Increment vote count
            return ResponseEntity.ok(candidates.get(name));
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/countvote")
    public ResponseEntity<Integer> countVote(@RequestParam String name) {
        return ResponseEntity.ok(candidates.getOrDefault(name, 0));
    }

    @GetMapping("/listvote")
    public ResponseEntity<Map<String, Integer>> listVotes() {
        return ResponseEntity.ok(candidates);
    }

    @GetMapping("/getwinner")
    public ResponseEntity<String> getWinner() {
        return ResponseEntity.ok(candidates.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("No candidates found"));
    }
}
