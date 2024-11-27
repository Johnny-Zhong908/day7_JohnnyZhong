package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.CompanyJPARepository;
import com.oocl.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public List<Company> findAll(int pageIndex, int pageSize) {
        return companyRepository.getCompaniesByPagination(pageIndex, pageSize);
    }

    public Company findById(Integer id) {
        return companyRepository.findById(id);
    }

    public List<Employee> getEmployeesByCompanyId(Integer id) {
        return companyRepository.getEmployeesByCompanyId(id);
    }

    public Company create(Company company) {
        return companyRepository.addCompany(company);
    }

    public Company update(Integer id, Company company) {
        return companyRepository.updateCompany(id, company);
    }

    public void delete(Integer id) {
        companyRepository.deleteById(id);
    }
}
