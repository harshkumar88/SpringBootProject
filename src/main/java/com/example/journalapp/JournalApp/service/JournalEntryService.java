package com.example.journalapp.JournalApp.service;
import com.example.journalapp.JournalApp.DTO.JournalEntry;
import com.example.journalapp.JournalApp.repository.JournalEntryRepository;
import com.example.journalapp.UserApp.DTO.UserEntry;
import com.example.journalapp.UserApp.service.UserEntryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;
import java.util.List;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private FormatTimeService timeService;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public void saveEntry(JournalEntry journalEntry,String userName) {
        journalEntryRepository.save(journalEntry);
        UserEntry userEntry = userEntryService.getUserEntry(userName);
        userEntry.getJournalEntries().add(journalEntry);
        userEntryService.save(userEntry);
    }

    public List<JournalEntry> getJournalEntries(String userName) {
      UserEntry userEntry =  userEntryService.getUserEntry(userName);
        List<JournalEntry> journals = userEntry.getJournalEntries();
        for (JournalEntry journalEntry : journals) {
            String updated_date = timeService.formatDate(journalEntry.getDate(), "yyyy-MM-dd");
            journalEntry.setFormattedDate(updated_date);
        }
        return journals;
    }

    public JournalEntry getJournalEntry(String id,String userName) {
        UserEntry userEntry = userEntryService.getUserEntry(userName);
        List<JournalEntry> journalEntries = userEntry.getJournalEntries();
        JournalEntry journalEntry= journalEntries
                .stream().
                filter(entry -> id.equals(entry.getId())).findFirst().orElse(null);

        if(journalEntry != null) {
            String updated_date = timeService.formatDate(journalEntry.getDate(), "yyyy-MM-dd");
            journalEntry.setFormattedDate(updated_date);
        }

        return journalEntry;
    }

    @Transactional
    public boolean deleteJournalEntry(String id,String userName) {
       boolean is_exist=journalEntryRepository.existsById(id);
       if(is_exist) {

           UserEntry userEntry = userEntryService.getUserEntry(userName);
           List<JournalEntry> journalEntries = userEntry.getJournalEntries();
           journalEntries.removeIf(entry -> id.equals(entry.getId()));
           userEntryService.save(userEntry);
           journalEntryRepository.deleteById(id);
           return true;
       }
       return false;
    }


    public boolean updateJournalEntry(String id, JournalEntry updatedJournalEntry,String userName) {

        JournalEntry journalEntry = journalEntryRepository.findById(id).orElse(null);

        if (journalEntry !=null) {
            // Update fields dynamically using reflection
            updateFieldsDynamically(journalEntry, updatedJournalEntry);
            // Save the updated entry
            journalEntryRepository.save(journalEntry);
            return true;
        }

        return false;
    }



    private void updateFieldsDynamically(Object target, Object source) {
        if (target == null || source == null) return;

        Field[] fields = source.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true); // Allow access to private fields

                Object newValue = field.get(source);
                Object currentValue = field.get(target);

                // Update only if new value is not null and different
                if (newValue != null && !newValue.equals(currentValue)) {
                    field.set(target, newValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to update field: " + field.getName(), e);
            }
        }
    }

}
