package com.myspringboot.journalApp.Controller;


import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

import com.myspringboot.journalApp.Entity.JournalEntry;
import com.myspringboot.journalApp.Service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {    

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){
        List<JournalEntry> checkEntries = journalEntryService.getAll();
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

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        JournalEntry checkEntry = journalEntryService.saveEntry(myEntry);
        if( checkEntry != null ){
            return ResponseEntity.status(HttpStatus.OK).body(checkEntry);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<String> deleteById(@PathVariable ObjectId myId){
        JournalEntry checkEntry = journalEntryService.deleteById(myId);
        if(checkEntry != null){
            return ResponseEntity.ok().body("Entry deleted succecfully.");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry not found");
        }
    }


    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable String myId, @RequestBody JournalEntry myEntry ){
        JournalEntry checkEntry = journalEntryService.updateEntry(new ObjectId(myId), myEntry);
        if(checkEntry != null){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(checkEntry);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
