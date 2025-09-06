package com.myspringboot.journalApp.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.myspringboot.journalApp.Entity.JournalEntry;
import com.myspringboot.journalApp.Entity.User;
import com.myspringboot.journalApp.Service.JournalEntryService;
import com.myspringboot.journalApp.Service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntryOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> checkEntries = user.getJournalEntries();
        if (checkEntries != null && !checkEntries.isEmpty())
            return ResponseEntity.ok(checkEntries);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
                .collect(Collectors.toList());
        if (!collect.isEmpty()) {
            JournalEntry checkEntry = journalEntryService.getById(myId);
            if (checkEntry != null) {
                return ResponseEntity.ok(checkEntry);
            }
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<String> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(myEntry, userName);
            return ResponseEntity.status(HttpStatus.CREATED).body("Journal entry created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating journal entry: " + e.getMessage());
        }
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<String> deleteById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalEntryService.deleteById(myId, userName);
        return ResponseEntity.ok().body("Entry deleted succecfully.");
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
                .collect(Collectors.toList());
        if (!collect.isEmpty()) {
            JournalEntry checkEntry = journalEntryService.getById(myId);
            if (checkEntry != null) {
                JournalEntry entry = journalEntryService.updateEntry(myId, myEntry);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(entry);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
