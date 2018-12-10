package domain;

import lombok.Data;

@Data
public class Customer {
    private String id;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String description;
}
