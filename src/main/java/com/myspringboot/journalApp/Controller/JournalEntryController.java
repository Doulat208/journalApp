package com.myspringboot.journalApp.Controller;


import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();
    }

    @GetMapping("id/{myId}")
    public JournalEntry getById(@PathVariable ObjectId myId){
        return journalEntryService.getById(myId);
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
        
    }

    @DeleteMapping("id/{myId}")
    public JournalEntry deleteById(@PathVariable ObjectId myId){
        return journalEntryService.deleteById(myId);
    }


    @PutMapping("id/{myId}")
    public JournalEntry updateJournalEntry(@PathVariable String myId, @RequestBody JournalEntry myEntry ){
        return journalEntryService.updateEntry(new ObjectId(myId), myEntry);
    }
}
