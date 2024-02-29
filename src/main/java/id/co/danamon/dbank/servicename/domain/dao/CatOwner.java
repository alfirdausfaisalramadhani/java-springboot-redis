package id.co.danamon.dbank.servicename.domain.dao;

import id.co.danamon.dbank.servicename.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CAT_OWNER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatOwner implements Serializable {

    private static final long serialVersionUID = 6559698417157876717L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;


}
