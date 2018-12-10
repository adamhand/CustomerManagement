package commons;

import Utils.C3P0Utils;
import domain.Customer;
import domain.PageBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Page {
    QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

    public PageBean<Customer> findAdd(int pageCode, int pageRecords){
        try {
            PageBean<Customer> pb = new PageBean<>();
            pb.setPageCode(pageCode);
            pb.setPageRecords(pageRecords);

            String sql = "select count(*) from t_customer";

            int totalNum = (int)qr.query(sql, new ScalarHandler<>());
            pb.setTotalRecords(totalNum);

            sql = "select * from t_customer order by name limit ?,?";
            Object[] params = {(pageCode - 1)*pageRecords, pageRecords};
            List<Customer> listBean = qr.query(sql,  new BeanListHandler<>(Customer.class), params);

            pb.setBeanList(listBean);

            return pb;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PageBean<Customer> query (Customer customer, int pageCode, int pageRecords){
        try {

            PageBean<Customer> pb = new PageBean<>();
            pb.setPageCode(pageCode);
            pb.setPageRecords(pageRecords);

            StringBuilder cntSql = new StringBuilder("select count(*) from t_customer");
            StringBuilder whereSql = new StringBuilder(" where 1=1 ");
            List<Object> params = new ArrayList<>();

            String name = customer.getName();
            if(name != null && !name.trim().isEmpty()){
                whereSql.append("and name like ? ");
                params.add("%"+name+"%");
            }

            String gender = customer.getGender();
            if(gender != null && !gender.trim().isEmpty()){
                whereSql.append("and gender like ? ");
                params.add(gender);
            }

            String phone = customer.getPhone();
            if(phone != null && !phone.trim().isEmpty()){
                whereSql.append("and phone like ? ");
                params.add("%"+phone+"%");
            }

            String email = customer.getEmail();
            if(email != null && !email.trim().isEmpty()){
                whereSql.append("and email like ? ");
                params.add("%"+email+"%");
            }

            int totalNum = (int)qr.query(cntSql.append(whereSql).toString(), new ScalarHandler<>(), params.toArray());
            pb.setTotalRecords(totalNum);

            StringBuilder sql = new StringBuilder("select * from t_customer ");
            StringBuilder limitSql = new StringBuilder(" limit ?,?");

            params.add((pageCode - 1)*pageRecords);
            params.add(pageRecords);

            List<Customer> beanList = qr.query(sql.append(whereSql).append(limitSql).toString(), new ScalarHandler<>(Customer.class), params.toArray());
            pb.setBeanList(beanList);

            return pb;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
