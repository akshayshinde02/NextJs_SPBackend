package com.example.nextjsbackend;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/schools")
public class TestController {

    private  SchoolRepository schoolRepository;

    @Autowired
    public TestController(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    // Endpoint to add a school
    @PostMapping("/add")
    public ResponseEntity<?> addSchool(
    		@RequestParam("name") String name,
                                       @RequestParam("address") String address,
                                       @RequestParam("city") String city,
                                       @RequestParam("state") String state,
                                       @RequestParam("contact") String contact,
                                       @RequestParam("email_id") String emailId,
                                       @RequestParam("file") MultipartFile file
                                      ) {
        try {
            School school = new School();
            school.setName(name);
            school.setAddress(address);
            school.setCity(city);
            school.setState(state);
            school.setContact(contact);
            school.setEmailId(emailId);
            school.setImage(file.getBytes());

            schoolRepository.save(school);
            return ResponseEntity.ok("School added successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add school");
        }
    }

    // Endpoint to fetch all schools
    @GetMapping("/all")
    public ResponseEntity<List<School>> getAllSchools() {
        List<School> schools = schoolRepository.findAll();
        return ResponseEntity.ok(schools);
    }

    // Endpoint to get a specific school by id
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getSchoolById(@PathVariable Long id) {
//        Optional<School> schoolOptional = schoolRepository.findById(id);
//        return schoolOptional.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found"));
//    }

    // Endpoint to update a school
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchool(@PathVariable Long id, @RequestBody School updatedSchool) {
        Optional<School> schoolOptional = schoolRepository.findById(id);
        if (schoolOptional.isPresent()) {
            School school = schoolOptional.get();
            school.setName(updatedSchool.getName());
            school.setAddress(updatedSchool.getAddress());
            school.setCity(updatedSchool.getCity());
            school.setState(updatedSchool.getState());
            school.setContact(updatedSchool.getContact());
            school.setEmailId(updatedSchool.getEmailId());
            school.setImage(updatedSchool.getImage());

            schoolRepository.save(school);
            return ResponseEntity.ok("School updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found");
        }
    }

    // Endpoint to delete a school
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchool(@PathVariable Long id) {
    	
//    	Long ids = (long) id;
        Optional<School> schoolOptional = schoolRepository.findById(id);
        if (schoolOptional.isPresent()) {
            schoolRepository.delete(schoolOptional.get());
            return ResponseEntity.ok("School deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found");
        }
    }
}

