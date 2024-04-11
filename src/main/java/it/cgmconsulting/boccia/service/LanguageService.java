package it.cgmconsulting.boccia.service;

import it.cgmconsulting.boccia.repository.LanguageRepository;
import org.springframework.stereotype.Service;


@Service
public class LanguageService {


    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

}
