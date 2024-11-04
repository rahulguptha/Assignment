package com.example.RahulAssignment.controller;

import com.example.RahulAssignment.model.Candidate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class VotingController {

    // Using ConcurrentHashMap for thread safety
    private final Map<String, Candidate> candidates = new ConcurrentHashMap<>();

    @PostMapping("/entercandidate")
    public String enterCandidate(@RequestParam String name) {
        if (candidates.containsKey(name)) {
            return "Candidate already exists.";
        }
        candidates.put(name, new Candidate(name));
        return "Candidate " + name + " entered with vote count initialized to 0.";
    }

    @PostMapping("/castvote")
    public String castVote(@RequestParam String name) {
        Candidate candidate = candidates.get(name);
        if (candidate == null) {
            return "Invalid candidate name.";
        }
        candidate.incrementVote();
        return "Vote casted for " + name + ". Current vote count: " + candidate.getVoteCount();
    }

    @GetMapping("/countvote")
    public String countVote(@RequestParam String name) {
        Candidate candidate = candidates.get(name);
        if (candidate == null) {
            return "Invalid candidate name.";
        }
        return "Vote count for " + name + ": " + candidate.getVoteCount();
    }

    @GetMapping("/listvote")
    public List<Map<String, Object>> listVotes() {
        List<Map<String, Object>> voteList = new ArrayList<>();
        for (Candidate candidate : candidates.values()) {
            Map<String, Object> voteDetails = new HashMap<>();
            voteDetails.put("name", candidate.getName());
            voteDetails.put("voteCount", candidate.getVoteCount());
            voteList.add(voteDetails);
        }
        return voteList;
    }

    @GetMapping("/getwinner")
    public String getWinner() {
        return candidates.values().stream()
                .max(Comparator.comparingInt(Candidate::getVoteCount))
                .map(winner -> "Winner: " + winner.getName() + " with " + winner.getVoteCount() + " votes.")
                .orElse("No votes have been cast.");
    }
}
