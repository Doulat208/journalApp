package com.myspringboot.journalApp.Entity;

import lombok.Data;

@Data
public class JournalEntry {
    
    private long  id;
    private String title;
    private String  content;

}
