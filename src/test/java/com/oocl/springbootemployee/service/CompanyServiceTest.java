package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyServiceTest {
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        companyService = new CompanyService(companyService.companyRepository.get());
    }

    @Test
    void should_return_all_companies_when_findAll() {
        // when
        List<Company> allCompanies = companyService.findAll();

        // then
        assertEquals(2, allCompanies.size());
        assertEquals("spring", allCompanies.get(0).getName());
        assertEquals("boot", allCompanies.get(1).getName());
    }

    @Test
    void should_return_specific_company_when_findById_given_existing_id() {
        // when
        Company company = companyService.findById(1);

        // then
        assertNotNull(company);
        assertEquals("spring", company.getName());
        assertEquals(3, company.getEmployees().size());
    }

    @Test
    void should_return_null_when_findById_given_non_existing_id() {
        // when
        Company company = companyService.findById(99);

        // then
        assertNull(company);
    }

    @Test
    void should_return_employees_when_getEmployeesByCompanyId_given_existing_company_id() {
        // when
        List<Employee> employees = companyService.getEmployeesByCompanyId(1);

        // then
        assertEquals(3, employees.size());
        assertTrue(employees.stream().anyMatch(emp -> emp.getName().equals("alice")));
    }

    @Test
    void should_return_empty_list_when_getEmployeesByCompanyId_given_non_existing_id() {
        // when
        List<Employee> employees = companyService.getEmployeesByCompanyId(99);

        // then
        assertTrue(employees.isEmpty());
    }

    @Test
    void should_return_paginated_companies_when_findAll_given_page_and_pageSize() {
        // when
        List<Company> paginatedCompanies = companyService.findAll(1, 1);

        // then
        assertEquals(1, paginatedCompanies.size());
        assertEquals("spring", paginatedCompanies.get(0).getName());
    }

    @Test
    void should_create_company_when_create_given_valid_company() {
        // given
        Company newCompany = new Company(null, "Apple",
                List.of(new Employee(null, "Steve", 55, Gender.MALE, 100000.0)));

        // when
        Company createdCompany = companyService.create(newCompany);

        // then
        assertNotNull(createdCompany);
        assertEquals("Apple", createdCompany.getName());
        assertEquals(3, companyService.findAll().size());
    }

    @Test
    void should_update_company_when_update_given_existing_id_and_valid_company() {
        // given
        Company updateInfo = new Company(null, "Updated Company", null);

        // when
        Company updatedCompany = companyService.update(1, updateInfo);

        // then
        assertNotNull(updatedCompany);
        assertEquals("Updated Company", updatedCompany.getName());
        assertEquals(3, updatedCompany.getEmployees().size()); // Employees remain unchanged
    }

    @Test
    void should_delete_company_when_delete_given_existing_id() {
        // when
        companyService.delete(1);

        // then
        assertEquals(1, companyService.findAll().size());
        assertNull(companyService.findById(1));
    }
}