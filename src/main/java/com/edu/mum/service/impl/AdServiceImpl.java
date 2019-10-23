package com.edu.mum.service.impl;

import com.edu.mum.domain.Ad;
import com.edu.mum.repository.AdRepository;
import com.edu.mum.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdServiceImpl implements AdService{

    @Autowired
    private AdRepository adRepository;

//    Ad create(Ad ad);
//    void delete(Ad ad);
//    List<Ad> findAllAds();
//
//    Ad save(Ad ad);

    @Override
    public Ad create(Ad ad) {
        return adRepository.save(ad);
    }

    @Override
    public void delete(Ad ad) {
        adRepository.delete(ad);
    }

    @Override
    public List<Ad> findAllAds() {
        return adRepository.findAll();
    }

    @Override
    public Ad save(Ad ad) {
        return adRepository.save(ad);
    }

}
