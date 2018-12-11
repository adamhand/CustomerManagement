package controller;

import Utils.BaseServlet;
import Utils.WebUtils;
import domain.Customer;
import domain.PageBean;
import service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CustomerServlet extends BaseServlet{
    private CustomerService cs = new CustomerService();

    public String addCustomer(HttpServletRequest request, HttpServletResponse response){
        Customer customer = WebUtils.fillBean(request, Customer.class);
        customer.setId(UUID.randomUUID().toString());

        cs.addCustomer(customer);

        request.setAttribute("msg", "恭喜，成功添加客户！");

        return "";
    }

    public String preEdit(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        Customer customer = cs.findCustomerById(id);

        request.setAttribute("customer", customer);

        return "";
    }

    public String editCustomer(HttpServletRequest request, HttpServletResponse response){
        Customer customer = WebUtils.fillBean(request, Customer.class);
        cs.editCustomer(customer);

        request.setAttribute("msg", "恭喜，修改客户信息成功！");

        return "";
    }

    public String deleteCustomer(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        cs.deleteCustomerById(id);

        request.setAttribute("msg", "恭喜，删除客户信息成功！");

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

    public String findAll(HttpServletRequest request, HttpServletResponse response){
        int pc = getPc(request);
        int pr = 10;

        PageBean<Customer> pb = cs.findAll(pc, pr);
        pb.setUrl(getUrl(request));

        request.setAttribute("pb", pb);

        return "";
    }


}
