package com.futsal;

import com.futsal.controller.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.event.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import javax.transaction.*;

@Transactional
public class ResetTimeStatus {
    @Autowired
    private TimeTableController timeController;

    @Autowired
    private BookingController bookingController;

    @PostConstruct
    public void resetTime() {
        System.out.println("RESETING............");
        timeController.reset();
        bookingController.expireBooking();
    }
}
