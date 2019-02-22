package dao;

import utils.C3P0Utils;
import domain.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class CustomerDaoImp implements CustomerDao{
    private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public void addCustomer(Customer customer) {
        String sql = "insert into t_customer values(?,?,?,?,?,?)";
        Object[] params =  {customer.getId(), customer.getName(), customer.getGender(),
                            customer.getPhone(), customer.getEmail(), customer.getDescription()};
        try {
            qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editCustomer(Customer customer) {
        String sql = "update t_customer set name=?,gender=?,phone=?,email=?,description=? where id=?";
        Object[] params = {customer.getName(), customer.getGender(), customer.getPhone(),
                           customer.getEmail(), customer.getDescription(), customer.getId()};
        try {
            qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer findCustomerById(String id) {
        String sql = "select * from t_customer where id=?";
        Customer customer = new Customer();
        try {
            customer = qr.query(sql, new BeanHandler<Customer>(Customer.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void deleteCustomerById(String id) {
        String sql = "delete from t_customer where id=?";

        try {
            qr.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
