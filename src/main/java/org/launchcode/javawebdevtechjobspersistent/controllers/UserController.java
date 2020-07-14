package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Job;
import org.launchcode.javawebdevtechjobspersistent.models.User;
import org.launchcode.javawebdevtechjobspersistent.models.data.JobRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public String displayUserPage(Model model, HttpServletRequest request) {
        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute("title", user.getUsername());

        model.addAttribute("saved",user.getMySavedJobs());

        return "user";
    }

    @PostMapping("save")
    public String saveJob(Model model, @RequestParam int jobId, HttpServletRequest request) {
        User user = authenticationController.getUserFromSession(request.getSession());


        Optional<Job> result =jobRepository.findById(jobId);

        if ( result.isPresent()) {
            Job job = result.get();
            if (!user.getMySavedJobs().contains(job))
                user.setMySavedJobs(job);
                userRepository.save(user);
            return "redirect:/";
        }
        return "user/save";
    }
}