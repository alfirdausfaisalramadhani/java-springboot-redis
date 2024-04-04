package id.co.ist.mobile.servicename.repository;

import id.co.ist.mobile.servicename.constant.Gender;
import id.co.ist.mobile.servicename.domain.dao.CatOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatOwnerRepository extends JpaRepository<CatOwner, Long> {
    List<CatOwner> findByGender(Gender gender);
}
