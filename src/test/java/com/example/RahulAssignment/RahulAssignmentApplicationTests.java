package com.example.RahulAssignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.example.RahulAssignment.controller.VotingController;

@SpringBootTest
class RahulAssignmentApplicationTests {

	@Test
	public void testEnterCandidate() {
		VotingController controller = new VotingController();
		ResponseEntity<String> response = controller.enterCandidate("Ajay");
        assertEquals("Candidate Ajay entered with vote count initialized to 0.", response);
        
        response = controller.enterCandidate("Ajay");
        assertEquals("Candidate already exists.", response);
    }

    @Test
    public void testCastVote() {
        VotingController controller = new VotingController();
        controller.enterCandidate("Ajay");
        ResponseEntity<Integer> response = controller.castVote("Ajay");
        assertEquals("Vote casted for Ajay. Current vote count: 1", response);

        response = controller.castVote("InvalidName");
        assertEquals("Invalid candidate name.", response);
    }

    @Test
    public void testCountVote() {
        VotingController controller = new VotingController();
        controller.enterCandidate("Ajay");
        controller.castVote("Ajay");
        ResponseEntity<Integer> response = controller.countVote("Ajay");
        assertEquals("Vote count for Ajay: 1", response);
    }

    @Test
    public void testGetWinner() {
        VotingController controller = new VotingController();
        controller.enterCandidate("Ajay");
        controller.enterCandidate("Vijay");
        controller.castVote("Ajay");
        controller.castVote("Ajay");
        controller.castVote("Vijay");

        ResponseEntity<String> winner = controller.getWinner();
        assertEquals("Ajay", winner);
	}

}
