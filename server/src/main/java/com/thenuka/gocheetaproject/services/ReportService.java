package com.thenuka.gocheetaproject.services;

import com.thenuka.gocheetaproject.dto.ReportCount;
import com.thenuka.gocheetaproject.interfaces.IReportService;
import com.thenuka.gocheetaproject.modals.Booking;
import com.thenuka.gocheetaproject.repositories.IBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "reportService")
@Transactional
public class ReportService implements IReportService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Override
    public ArrayList<?> bookingsCount(Date fromDate, Date toDate) {
        ArrayList<ReportCount> counts = new ArrayList<>();
//        List<Booking> list = bookingRepository.findAll().stream()
//                .filter(b -> fromDate.after(java.sql.Timestamp.valueOf(b.getTripScheduledTime())) && toDate.before(java.sql.Timestamp.valueOf(b.getTripScheduledTime())))
//                .collect(Collectors.toList());
//        Map<Month, Double> bookingsByMonth = list.stream()
//                .collect(Collectors.groupingBy(m -> Month.from(m.getTripScheduledTime().getMonth())))
        return counts;
    }

    @Override
    public ArrayList<?> usersCount(Date fromDate, Date toDate) {
        ArrayList<ReportCount> counts = new ArrayList<>();
        return  counts;
    }
}
