package org.launchcode.javawebdevtechjobspersistent.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    @ManyToMany
    @JoinColumn(name = "user_id")
    private final List<Job> mySavedJobs = new ArrayList<>();

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public String getUsername() {
       return username;
    }

    public List<Job> getMySavedJobs() {
        return mySavedJobs;
    }

    public void setMySavedJobs(Job job) {
        mySavedJobs.add(job);
    }

    public void deleteJobFromSavedJobs(Job job) {
        mySavedJobs.remove(job);
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }
}
