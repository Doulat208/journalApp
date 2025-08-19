package com.myspringboot.journalApp.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myspringboot.journalApp.Entity.JournalEntry;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    

    private Map<Long, JournalEntry> journaleEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journaleEntries.values());
    }

    @GetMapping("id/{myId}")
    public JournalEntry getById(@PathVariable Long myId){
        return journaleEntries.get(myId);
    }

    @PostMapping
    public void createEntry(@RequestBody JournalEntry myEntry){
        journaleEntries.put(myEntry.getId(), myEntry);
    }

    @DeleteMapping("id/{myId}")
    public JournalEntry deleteById(@PathVariable Long myId){
        return journaleEntries.remove(myId);
    }


    @PutMapping("id/{myId}")
    public JournalEntry updateJournalEntry(@PathVariable Long myId, @RequestBody JournalEntry myEntry ){
        return journaleEntries.put(myId, myEntry);
    }
}
