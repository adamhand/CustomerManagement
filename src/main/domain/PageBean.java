package domain;

import lombok.Data;

import java.util.List;

@Data
public class PageBean<Object> {
    private int pc;          //当前页码page code
    //private int tp;//总页数total pages
    private int tr;          //总纪录数tatal records
    private int pr;          //每页纪录数page records
    private List<Object> beanList;//当前页的纪录
    private String url;
}
