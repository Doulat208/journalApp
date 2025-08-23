package com.myspringboot.journalApp.Service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myspringboot.journalApp.Entity.JournalEntry;
import com.myspringboot.journalApp.Repository.JournalEntryRepository;

@Service
public class JournalEntryService {
    

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry saveEntry(JournalEntry myEntry){
        // myEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(myEntry);
        return myEntry;
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    
    public JournalEntry getById(ObjectId myId){
        return journalEntryRepository.findById(myId).orElse(null);
    }

    public JournalEntry updateEntry(ObjectId myId, JournalEntry myEntry){
        JournalEntry old = journalEntryRepository.findById(myId).orElse(null);
        if(old != null){
            old.setTitle(myEntry.getTitle() != null && myEntry.getTitle() != "" ? myEntry.getTitle() : old.getTitle()) ;
            old.setContent(myEntry.getContent() != null && myEntry.getContent() != "" ? myEntry.getContent() :old.getContent());
            old = journalEntryRepository.save(old);
        }
        return old;
    }

    public JournalEntry deleteById(ObjectId myId){
        JournalEntry temp = journalEntryRepository.findById(myId).orElse(null);
        if(temp != null)
            journalEntryRepository.deleteById(myId);
        return temp;
    }
}
