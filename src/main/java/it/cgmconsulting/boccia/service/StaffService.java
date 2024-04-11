package it.cgmconsulting.boccia.service;

import it.cgmconsulting.boccia.repository.StaffRepository;
import org.springframework.stereotype.Service;



@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }




}
