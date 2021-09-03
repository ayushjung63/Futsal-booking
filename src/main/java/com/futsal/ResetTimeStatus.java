package com.futsal;

import com.futsal.controller.*;
import org.springframework.beans.factory.annotation.*;

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
        timeController.reset();
        bookingController.expireBooking();
    }
}
