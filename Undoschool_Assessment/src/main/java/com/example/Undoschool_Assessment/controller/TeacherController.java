package com.example.Undoschool_Assessment.controller;

import com.example.Undoschool_Assessment.entity.Course;
import com.example.Undoschool_Assessment.entity.Offering;
import com.example.Undoschool_Assessment.entity.Session;
import com.example.Undoschool_Assessment.entity.Teacher;
import com.example.Undoschool_Assessment.repository.CourseRepository;
import com.example.Undoschool_Assessment.repository.OfferingRepository;
import com.example.Undoschool_Assessment.repository.SessionRepository;
import com.example.Undoschool_Assessment.repository.TeacherRepository;
import com.example.Undoschool_Assessment.util.TimezoneHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired private TeacherRepository teacherRepo;
    @Autowired private CourseRepository courseRepo;
    @Autowired private OfferingRepository offeringRepo;
    @Autowired private SessionRepository sessionRepo;

    @PostMapping("/{teacherId}/offerings")
    public ResponseEntity<?> createOffering(@PathVariable Long teacherId,
                                            @RequestBody CreateOfferingRequest request) {
        Optional<Teacher> teacherOpt = teacherRepo.findById(teacherId);
        if (teacherOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");
        }
        Optional<Course> courseOpt = courseRepo.findById(request.getCourseId());
        if (courseOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        Offering offering = new Offering();
        offering.setName(request.getName());
        offering.setTeacher(teacherOpt.get());
        offering.setCourse(courseOpt.get());
        offering.setTeacherTimezone(request.getTeacherTimezone());
        Offering savedOffering = offeringRepo.save(offering);

        for (SessionRequest sessReq : request.getSessions()) {
            Session session = new Session();
            session.setOffering(savedOffering);
            LocalDateTime startUtc = TimezoneHelper.toUtc(sessReq.getStartTime(), request.getTeacherTimezone());
            LocalDateTime endUtc = TimezoneHelper.toUtc(sessReq.getEndTime(), request.getTeacherTimezone());
            session.setStartTimeUtc(startUtc);
            session.setEndTimeUtc(endUtc);
            sessionRepo.save(session);
        }

        // Return simple response to avoid circular references
        Map<String, Object> response = new HashMap<>();
        response.put("id", savedOffering.getId());
        response.put("name", savedOffering.getName());
        response.put("message", "Offering created with sessions");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/offerings/{offeringId}/sessions")
    public ResponseEntity<?> addSessions(@PathVariable Long offeringId,
                                         @RequestBody AddSessionsRequest request) {
        Optional<Offering> offeringOpt = offeringRepo.findById(offeringId);
        if (offeringOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offering not found");
        }
        Offering offering = offeringOpt.get();
        String teacherZone = offering.getTeacherTimezone();

        for (SessionRequest sessReq : request.getSessions()) {
            Session session = new Session();
            session.setOffering(offering);
            LocalDateTime startUtc = TimezoneHelper.toUtc(sessReq.getStartTime(), teacherZone);
            LocalDateTime endUtc = TimezoneHelper.toUtc(sessReq.getEndTime(), teacherZone);
            session.setStartTimeUtc(startUtc);
            session.setEndTimeUtc(endUtc);
            sessionRepo.save(session);
        }
        return ResponseEntity.ok("Sessions added successfully");
    }

    @GetMapping("/{teacherId}/offerings")
    public ResponseEntity<?> getTeacherOfferings(@PathVariable Long teacherId) {
        List<Offering> allOfferings = offeringRepo.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Offering off : allOfferings) {
            if (off.getTeacher().getId().equals(teacherId)) {
                List<Session> sessions = sessionRepo.findByOfferingId(off.getId());
                Map<String, Object> map = new HashMap<>();
                map.put("id", off.getId());
                map.put("name", off.getName());
                // Convert sessions to simple list to avoid recursion
                List<Map<String, String>> sessionList = new ArrayList<>();
                for (Session s : sessions) {
                    Map<String, String> sessMap = new HashMap<>();
                    sessMap.put("startUtc", s.getStartTimeUtc().toString());
                    sessMap.put("endUtc", s.getEndTimeUtc().toString());
                    sessionList.add(sessMap);
                }
                map.put("sessions", sessionList);
                result.add(map);
            }
        }
        return ResponseEntity.ok(result);
    }

    // ========== DTOs with proper getters/setters ==========
    public static class CreateOfferingRequest {
        private Long courseId;
        private String name;
        private String teacherTimezone;
        private List<SessionRequest> sessions;

        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getTeacherTimezone() { return teacherTimezone; }
        public void setTeacherTimezone(String teacherTimezone) { this.teacherTimezone = teacherTimezone; }
        public List<SessionRequest> getSessions() { return sessions; }
        public void setSessions(List<SessionRequest> sessions) { this.sessions = sessions; }
    }

    public static class AddSessionsRequest {
        private List<SessionRequest> sessions;
        public List<SessionRequest> getSessions() { return sessions; }
        public void setSessions(List<SessionRequest> sessions) { this.sessions = sessions; }
    }

    public static class SessionRequest {
        private String startTime;
        private String endTime;
        public String getStartTime() { return startTime; }
        public void setStartTime(String startTime) { this.startTime = startTime; }
        public String getEndTime() { return endTime; }
        public void setEndTime(String endTime) { this.endTime = endTime; }
    }
}