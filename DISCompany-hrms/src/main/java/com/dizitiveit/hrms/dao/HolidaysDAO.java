package com.dizitiveit.hrms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dizitiveit.hrms.model.Holidays;

public interface HolidaysDAO extends JpaRepository<Holidays,Integer> {

	public 	Holidays findByHolidaysId(Integer empHolidaysId);
	public Boolean existsByHolidaysId(Integer empHolidaysId);
	
	@Query(value = "SELECT DISTINCT holiday_date FROM holidays where MONTH(holiday_date) =?1  and YEAR(holiday_date) = ?2", nativeQuery = true)
	List<Date> getbyHolidaysMonth(int month, int year);
	
	@Query(value = "SELECT * from com.holidays where MONTH(holiday_date) =?1  and YEAR(holiday_date) =?2 ", nativeQuery = true)
	List<Holidays> getbyholidaysMonth(int month, int year);
}
