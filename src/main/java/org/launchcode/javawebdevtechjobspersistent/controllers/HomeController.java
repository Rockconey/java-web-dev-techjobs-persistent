package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.Job;
import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.User;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.JobRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private AuthenticationController authenticationController;


    @RequestMapping("")
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("jobs", jobRepository.findAll());

        User user = authenticationController.getUserFromSession(request.getSession());
        model.addAttribute("user", user);
        model.addAttribute("title", "My Jobs");
        model.addAttribute("welcome", "Welcome " + user.getUsername());

        model.addAttribute("myJobs", user.getMySavedJobs());

            return "index";
        }


    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());

        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model, @RequestParam int employerId, @RequestParam(required = false) List<Integer> skills) {

        if (errors.hasErrors() || skills == null) {
            model.addAttribute("title", "Add Job");
            model.addAttribute("employers", employerRepository.findAll());
            model.addAttribute("skills", skillRepository.findAll());
            model.addAttribute("errors", errors);
            return "add";
        }

       Optional result = employerRepository.findById(employerId);
        if (result.isPresent()) {

            Employer anEmployer = (Employer) result.get();
            newJob.setEmployer(anEmployer);
            List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
            newJob.setSkills(skillObjs);
            jobRepository.save(newJob);
        }
        return "redirect:";
    }

    @RequestMapping(value = "view/{jobId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String displayViewJob(Model model, @PathVariable int jobId) {
         Optional<Job> result = jobRepository.findById(jobId);

         if (result.isPresent()) {
             Job job = result.get();

             model.addAttribute("job", job);
         }
        return "view";
    }


}
