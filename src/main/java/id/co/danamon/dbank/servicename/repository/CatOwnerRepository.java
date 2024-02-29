package id.co.danamon.dbank.servicename.repository;

import id.co.danamon.dbank.servicename.constant.Gender;
import id.co.danamon.dbank.servicename.domain.dao.CatOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatOwnerRepository extends JpaRepository<CatOwner, Long> {
    List<CatOwner> findByGender(Gender gender);
}
