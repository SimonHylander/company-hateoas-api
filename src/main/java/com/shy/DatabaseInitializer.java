package com.shy;

import com.shy.company.Company;
import com.shy.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Company> companies = Arrays.asList(
            new Company("12345", "Mitt företag", "Beskrivning"),
            new Company("678910", "Annat företag", "Beskrivning..")
        );

        companyRepository.saveAll(companies);
    }
}