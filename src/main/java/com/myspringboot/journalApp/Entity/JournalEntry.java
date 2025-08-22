package com.myspringboot.journalApp.Entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Journal_Entries")
public class JournalEntry {
    
    @Id
    private ObjectId  id;
    private String title;
    private String  content;
    private LocalDateTime date;

}
