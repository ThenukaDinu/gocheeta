package com.thenuka.gocheetaproject.interfaces;

import java.util.ArrayList;
import java.util.Date;

public interface IReportService {
    public ArrayList<?> bookingsCount(Date fromDate, Date toDate);
    public ArrayList<?> usersCount(Date fromDate, Date toDate);
}
