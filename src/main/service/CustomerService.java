package service;

import commons.Page;
import dao.CustomerDao;
import dao.CustomerDaoImp;
import domain.Customer;
import domain.PageBean;

public class CustomerService {
    CustomerDao customerDao = new CustomerDaoImp();
    Page pageDao = new Page();

    public void addCustomer(Customer customer){
        customerDao.addCustomer(customer);
    }

    public void editCustomer(Customer customer){
        customerDao.editCustomer(customer);
    }

    public Customer findCustomerById(String id){
        return customerDao.findCustomerById(id);
    }

    public void deleteCustomerById(String id){
        customerDao.deleteCustomerById(id);
    }

    public PageBean<Customer> findAll(int pc, int pr){
        return pageDao.findAll(pc, pr);
    }

    public PageBean<Customer> query(Customer customer, int pc, int pr){
        return pageDao.query(customer, pc, pr);
    }
}
