package com.myspringboot.journalApp.Entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Journal_Entries")
public class JournalEntry {
    
    @Id
    private ObjectId  id;
    private String title;
    private String  content;
    private LocalDateTime date;

}
