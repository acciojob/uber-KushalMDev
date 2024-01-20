package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Cab;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		// Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Optional<Customer> optionalCustomer = customerRepository2.findById(customerId);
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			customerRepository2.delete(customer);
		}

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm)
			throws Exception {
		// Book the driver with lowest driverId who is free (cab available variable is
		// Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		// Avoid using SQL query
		List<Driver> dList = driverRepository2.findAll();
		int min = Integer.MAX_VALUE;
		for (Driver driver : dList) {
			Cab cab = driver.getCab();
			if (cab.getAvailable()) {
				min = Math.min(min, driver.getDriverId());
			}
		}
		if (min == Integer.MAX_VALUE) {
			throw new Exception("No cab available!");
		} else {
			TripBooking tripBooking = new TripBooking();
			Optional<Driver> driver = driverRepository2.findById(min);
			if(driver.isEmpty())return null;
			driver.getCab().setAvailable(false);
			int kmperrate = driver.getCab().getPerKmRate();
			int totalCost = distanceInKm * kmperrate;
			tripBooking.setBill(totalCost);
			tripBooking.setDistanceInKm(distanceInKm);
			tripBooking.setDriver(driver);
			tripBooking.setFromLocation(fromLocation);
			tripBooking.setToLocation(toLocation);
			tripBooking.setStatus(TripStatus.CONFIRMED);
			Optional<Customer> customer = customerRepository2.findById(customerId);
			if (customer.isEmpty())
				return null;
			tripBooking.setCustomer(customerRepository2.findById(customerId).get());
			Customer cust = customer.get();
			cust.getTripBookingList().add(tripBooking);
			driver.getTripBookingList().add(tripBooking);
			driverRepository2.save(driver);
			customerRepository2.save(cust);
			TripBooking trp=tripBookingRepository2.save(tripBooking);
			return trp;
		}

	}

	@Override
	public void cancelTrip(Integer tripId) {
		// Cancel the trip having given trip Id and update TripBooking attributes
		// accordingly
		Optional<TripBooking> optionaltripBooking=tripBookingRepository2.findById(tripId);
		if(optionaltripBooking.isPresent()){
		
			TripBooking tripBooking=optionaltripBooking.get();
			tripBooking.setStatus(TripStatus.CANCELED);
			Driver driver=tripBooking.getDriver();
			Cab cab=driver.getCab();
			cab.setAvailable(true);
			tripBookingRepository2.save(tripBooking);

		}
	}

	@Override
	public void completeTrip(Integer tripId) {
		// Complete the trip having given trip Id and update TripBooking attributes
		// accordingly
		Optional<TripBooking> optionaltripBooking=tripBookingRepository2.findById(tripId);
		if(optionaltripBooking.isPresent()){
		
			TripBooking tripBooking=optionaltripBooking.get();
			tripBooking.setStatus(TripStatus.COMPLETED);
			Driver driver=tripBooking.getDriver();
			Cab cab=driver.getCab();
			cab.setAvailable(true);
			tripBookingRepository2.save(tripBooking);

		}

	}
}
