package id.co.danamon.dbank.servicename.service;

import id.co.danamon.dbank.servicename.domain.dao.CatOwner;
import id.co.danamon.dbank.servicename.domain.dto.internal.GreetingRequest;
import id.co.danamon.dbank.servicename.repository.CatOwnerRepository;
import id.co.danamon.dbank.servicename.util.GreetingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class GreetingService {

    @Autowired
    private CatOwnerRepository catOwnerRepository;

    public List<String> greetAllCatOwner(GreetingRequest request) {
        if (Objects.isNull(request.getGender())) {
            return catOwnerRepository.findAll().stream()
                    .map(owner -> GreetingUtil.constructGreeting(owner.getGender(), owner.getFirstName()))
                    .collect(Collectors.toList());
        } else {
            return catOwnerRepository.findByGender(request.getGender()).stream()
                    .map(owner -> GreetingUtil.constructGreeting(owner.getGender(), owner.getFirstName()))
                    .collect(Collectors.toList());
        }
    }

    public Optional<String> greetCatOwner(Long id) {
        CatOwner catOwner = catOwnerRepository.findById(id).orElse(null);
        if (Objects.isNull(catOwner)) {
            return Optional.empty();
        } else {
            return Optional.of(GreetingUtil.constructGreeting(catOwner.getGender(), catOwner.getFirstName()));
        }
    }

}
