package com.example.Undoschool_Assessment.controller;

import com.example.Undoschool_Assessment.entity.*;
import com.example.Undoschool_Assessment.repository.*;
import com.example.Undoschool_Assessment.util.TimezoneHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    @Autowired private ParentRepository parentRepo;
    @Autowired private OfferingRepository offeringRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private SessionRepository sessionRepo;

    @GetMapping("/{parentId}/offerings")
    public ResponseEntity<?> getAvailableOfferings(@PathVariable Long parentId) {
        Optional<Parent> parentOpt = parentRepo.findById(parentId);
        if (parentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parent not found");
        }
        Parent parent = parentOpt.get();
        String parentZone = parent.getTimezone();

        List<Offering> allOfferings = offeringRepo.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Offering off : allOfferings) {
            List<Session> sessions = sessionRepo.findByOfferingId(off.getId());
            List<Map<String, String>> sessionInfos = new ArrayList<>();
            for (Session s : sessions) {
                Map<String, String> map = new HashMap<>();
                String startLocal = TimezoneHelper.toParentLocal(s.getStartTimeUtc(), parentZone, "yyyy-MM-dd HH:mm");
                String endLocal = TimezoneHelper.toParentLocal(s.getEndTimeUtc(), parentZone, "yyyy-MM-dd HH:mm");
                map.put("start", startLocal);
                map.put("end", endLocal);
                sessionInfos.add(map);
            }
            Map<String, Object> offeringMap = new HashMap<>();
            offeringMap.put("id", off.getId());
            offeringMap.put("name", off.getName());
            offeringMap.put("sessions", sessionInfos);
            result.add(offeringMap);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{parentId}/bookings")
    @Transactional
    public ResponseEntity<?> bookOffering(@PathVariable Long parentId, @RequestBody BookingRequest request) {
        Parent parent = parentRepo.findByIdWithLock(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        Offering offering = offeringRepo.findById(request.getOfferingId())
                .orElseThrow(() -> new RuntimeException("Offering not found"));

        List<Booking> existingBookings = bookingRepo.findByParentId(parentId);
        List<Session> newSessions = sessionRepo.findByOfferingId(offering.getId());

        for (Session newSession : newSessions) {
            for (Booking booking : existingBookings) {
                List<Session> bookedSessions = sessionRepo.findByOfferingId(booking.getOffering().getId());
                for (Session booked : bookedSessions) {
                    if (isOverlapping(newSession, booked)) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("Cannot book: session conflicts with existing booking");
                    }
                }
            }
        }

        Booking booking = new Booking();
        booking.setParent(parent);
        booking.setOffering(offering);
        booking.setBookedAt(LocalDateTime.now(ZoneOffset.UTC));
        bookingRepo.save(booking);

        return ResponseEntity.ok("Booking successful");
    }

    @GetMapping("/{parentId}/bookings")
    public ResponseEntity<?> getParentBookings(@PathVariable Long parentId) {
        Optional<Parent> parentOpt = parentRepo.findById(parentId);
        if (parentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parent not found");
        }
        Parent parent = parentOpt.get();
        String parentZone = parent.getTimezone();

        List<Booking> bookings = bookingRepo.findByParentId(parentId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Booking booking : bookings) {
            Offering off = booking.getOffering();
            List<Session> sessions = sessionRepo.findByOfferingId(off.getId());
            List<Map<String, String>> sessionInfos = new ArrayList<>();
            for (Session s : sessions) {
                Map<String, String> map = new HashMap<>();
                String startLocal = TimezoneHelper.toParentLocal(s.getStartTimeUtc(), parentZone, "yyyy-MM-dd HH:mm");
                String endLocal = TimezoneHelper.toParentLocal(s.getEndTimeUtc(), parentZone, "yyyy-MM-dd HH:mm");
                map.put("start", startLocal);
                map.put("end", endLocal);
                sessionInfos.add(map);
            }
            Map<String, Object> bookingMap = new HashMap<>();
            bookingMap.put("offeringId", off.getId());
            bookingMap.put("offeringName", off.getName());
            bookingMap.put("sessions", sessionInfos);
            bookingMap.put("bookedAt", booking.getBookedAt().toString());
            result.add(bookingMap);
        }
        return ResponseEntity.ok(result);
    }

    private boolean isOverlapping(Session s1, Session s2) {
        return !s1.getEndTimeUtc().isBefore(s2.getStartTimeUtc()) &&
                !s1.getStartTimeUtc().isAfter(s2.getEndTimeUtc());
    }

    // DTO with getters/setters
    public static class BookingRequest {
        private Long offeringId;
        public Long getOfferingId() { return offeringId; }
        public void setOfferingId(Long offeringId) { this.offeringId = offeringId; }
    }
}
