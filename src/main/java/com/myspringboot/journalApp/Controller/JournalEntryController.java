package com.myspringboot.journalApp.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntryOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> checkEntries = user.getJournalEntries();
        if(checkEntries != null)
            return ResponseEntity.ok(checkEntries);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId){
        JournalEntry checkEntry = journalEntryService.getById(myId);
        if(checkEntry != null){
            return ResponseEntity.ok(checkEntry);
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/{userName}")
    public ResponseEntity<String> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try {          
            journalEntryService.saveEntry(myEntry, userName);
            return ResponseEntity.status(HttpStatus.CREATED).body("Journal entry created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating journal entry: " + e.getMessage());
        }
    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<String> deleteById(@PathVariable ObjectId myId, @PathVariable String userName){
        journalEntryService.deleteById(myId, userName);
        return ResponseEntity.ok().body("Entry deleted succecfully.");
    }


    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntry(
        @PathVariable String myId, 
        @PathVariable String userName,
        @RequestBody JournalEntry myEntry
         )
        {
        JournalEntry checkEntry = journalEntryService.updateEntry(new ObjectId(myId), myEntry);
        if(checkEntry != null){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(checkEntry);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
