package com.myspringboot.journalApp.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public void createEntry(){
        
    }

}
