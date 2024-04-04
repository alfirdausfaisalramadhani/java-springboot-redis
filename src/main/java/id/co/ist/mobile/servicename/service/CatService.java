package id.co.ist.mobile.servicename.service;

import id.co.ist.mobile.common.util.MapperUtil;
import id.co.ist.mobile.servicename.domain.dao.CatOwner;
import id.co.ist.mobile.servicename.domain.dto.external.CatFactDto;
import id.co.ist.mobile.servicename.domain.dto.internal.CatOwnerDto;
import id.co.ist.mobile.servicename.repository.CatOwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Slf4j
@Service
public class CatService {

    @Autowired
    private CatOwnerRepository catOwnerRepository;

    @Autowired
    private RestTemplate restTemplate;


    public CatFactDto getCatFact() {
        return restTemplate.getForEntity("https://catfact.ninja/fact", CatFactDto.class)
                .getBody();
    }

    public CatOwnerDto createCatOwner(CatOwnerDto catOwnerDto) {
        CatOwner owner = catOwnerRepository.save((CatOwner) MapperUtil.transform(catOwnerDto, new CatOwner(), true));
        catOwnerDto.setId(owner.getId());
        return catOwnerDto;
    }

    public Optional<CatOwnerDto> updateCatOwner(CatOwnerDto catOwnerDto) {
        Optional<CatOwner> ownerInDb = catOwnerRepository.findById(catOwnerDto.getId());

        if (ownerInDb.isEmpty()) {
            return Optional.empty();
        }
        CatOwner ownerToUpdate = catOwnerRepository.save((CatOwner) MapperUtil.transform(catOwnerDto, new CatOwner(), true));
        CatOwnerDto updatedCatOwnerData = (CatOwnerDto) MapperUtil.transform(ownerToUpdate, new CatOwnerDto(), true);

        return Optional.of(updatedCatOwnerData);
    }

    public Optional<CatOwnerDto> deleteCatOwner(Long id) {
        Optional<CatOwner> catOwner = catOwnerRepository.findById(id);
        if (catOwner.isEmpty()) {
            return Optional.empty();
        }
        catOwnerRepository.deleteById(id);
        return Optional.of((CatOwnerDto) MapperUtil.transform(catOwner, new CatOwnerDto(), true));
    }
}
