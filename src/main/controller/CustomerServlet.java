package controller;

import Utils.BaseServlet;
import Utils.WebUtils;
import domain.Customer;
import domain.PageBean;
import service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class CustomerServlet extends BaseServlet{
    private CustomerService cs = new CustomerService();

    public String addCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = WebUtils.fillBean(request, Customer.class);
        customer.setId(UUID.randomUUID().toString().substring(0, 4));

        cs.addCustomer(customer);

        request.setAttribute("msg", "恭喜，成功添加客户！");

        request.getRequestDispatcher("/msg.jsp").forward(request, response);

        return "";
    }

    public String preEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Customer customer = cs.findCustomerById(id);

        request.setAttribute("customer", customer);

        request.getRequestDispatcher("/edit.jsp").forward(request, response);

        return "";
    }

    public String editCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = WebUtils.fillBean(request, Customer.class);
        cs.editCustomer(customer);

        request.setAttribute("msg", "恭喜，修改客户信息成功！");

        request.getRequestDispatcher("/msg.jsp").forward(request, response);

        return "";
    }

    public String deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        cs.deleteCustomerById(id);

        request.setAttribute("msg", "恭喜，删除客户信息成功！");

        request.getRequestDispatcher("/msg.jsp").forward(request, response);

        return "";
    }

    private int getPc(HttpServletRequest request){
        String value = request.getParameter("pc");
        if(value == null || value.trim().isEmpty())
            return 1;

        return Integer.parseInt(value);
    }

    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String queryString = request.getQueryString();

        if (queryString.contains("&pc=")) {
            int index = queryString.lastIndexOf("&pc=");
            queryString = queryString.substring(0, index);
        }

        return contextPath + servletPath + "?" + queryString;
    }

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pc = getPc(request);
        int pr = 10;

        PageBean<Customer> pb = cs.findAll(pc, pr);
        pb.setUrl(getUrl(request));

        request.setAttribute("pb", pb);

        request.getRequestDispatcher("/list.jsp").forward(request, response);

        return "";
    }

    public String query(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Customer customer = WebUtils.fillBean(request, Customer.class);

        customer = encoding(customer);

        int pc = getPc(request);
        int pr = 10;

        PageBean<Customer> pb = cs.query(customer, pc, pr);
        pb.setUrl(getUrl(request));

        request.setAttribute("pb", pb);

        request.getRequestDispatcher("/list.jsp").forward(request, response);

        return "";
    }

    private Customer encoding(Customer customer) throws UnsupportedEncodingException {
        String name = customer.getName();
        String gender = customer.getGender();
        String phone = customer.getPhone();
        String email = customer.getEmail();

        if(name != null && !name.trim().isEmpty()){
            name = new String(name.getBytes("ISO-8859-1"), "utf-8");
            customer.setName(name);
        }
        if(gender != null && !gender.trim().isEmpty()){
            gender = new String(gender.getBytes("ISO-8859-1"), "utf-8");
            customer.setGender(gender);
        }
        if(phone != null && !phone.trim().isEmpty()){
            phone = new String(phone.getBytes("ISO-8859-1"), "utf-8");
            customer.setPhone(phone);
        }
        if(email != null && !email.trim().isEmpty()){
            email = new String(email.getBytes("ISO-8859-1"), "utf-8");
            customer.setEmail(email);
        }

        return customer;
    }
}
