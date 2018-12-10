package Controller;

import Utils.BaseServlet;
import Utils.WebUtils;
import domain.Customer;
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


}
