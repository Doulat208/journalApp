package com.myspringboot.journalApp.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myspringboot.journalApp.Entity.JournalEntry;
import com.myspringboot.journalApp.Entity.User;
import com.myspringboot.journalApp.Repository.JournalEntryRepository;

@Service
public class JournalEntryService {
    

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry myEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);            
            LocalDateTime now = LocalDateTime.now();
            myEntry.setDate(now);
            JournalEntry saved = journalEntryRepository.save(myEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the journal entry: " + e.getMessage(), e);
        }
    }

    public void saveEntry(JournalEntry myEntry){
        journalEntryRepository.save(myEntry);
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

    @Transactional
    public void deleteById(ObjectId myId, String userName){
        try{
            User user = userService.findByUserName(userName);
            user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
            userService.saveUser(user);
            journalEntryRepository.deleteById(myId);
        }catch (Exception e){
            throw new RuntimeException("An error occur while deleting entries.");
        }
    }
}
